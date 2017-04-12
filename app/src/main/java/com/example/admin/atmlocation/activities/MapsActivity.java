package com.example.admin.atmlocation.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.atmlocation.R;
import com.example.admin.atmlocation.interfaces.ATMService;
import com.example.admin.atmlocation.models.ATM;
import com.example.admin.atmlocation.models.DirectionResult;
import com.example.admin.atmlocation.models.Distance;
import com.example.admin.atmlocation.models.Duration;
import com.example.admin.atmlocation.models.Locations;
import com.example.admin.atmlocation.models.RouteDecode;
import com.example.admin.atmlocation.models.Routes;
import com.example.admin.atmlocation.models.Steps;
import com.example.admin.atmlocation.services.ApiUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MapsActivity class
 * Created by naunem on 30/03/2017.
 */


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, View.OnClickListener {

    private static final String KEY_DIRECTIONS = "AIzaSyDjJotwDoyLtG6RLKbXhsi56-c9dbjByOg";
    private GoogleMap mMap;
    private PolylineOptions mPolylineOptions = new PolylineOptions();
    Toolbar mToolbar;
    SearchView mSearchView;
    EditText mEdtStart;
    EditText mEdtEnd;
    Button mBtnFind;
    TextView mTvDistance;
    TextView mTvDuration;
    private ATMService mService;
    SupportMapFragment mMapFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_maps);
        setSupportActionBar(mToolbar);
        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
        mService = ApiUtils.getService();
        mEdtStart = (EditText) findViewById(R.id.edtStart);
        mEdtEnd = (EditText) findViewById(R.id.edtEnd);
        mBtnFind = (Button) findViewById(R.id.btnFind);
        mBtnFind.setOnClickListener(this);
        mTvDistance = (TextView) findViewById(R.id.tvDistance);
        mTvDuration = (TextView) findViewById(R.id.tvDuration);

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        ATM atm = getIntent().getBundleExtra("data").getParcelable("object");
        Locations atmLocations = getIntent().getBundleExtra("data").getParcelable("location");
        double latitude = atmLocations.getLat();
        double longitude = atmLocations.getLng();
        LatLng location = new LatLng(latitude, longitude);

        mPolylineOptions.add(location);

        mMap.addMarker(new MarkerOptions().position(location).title(atm.getName()).snippet(latitude + ", " + longitude)).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
        mMap.addPolyline(new PolylineOptions().clickable(true));
        mMap.setTrafficEnabled(true);
        // Check permission location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions marker = new MarkerOptions()
                .position(myLocation)
                .title("My Locations")
                .snippet("ahihihi")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mMap.addMarker(marker).showInfoWindow();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 16));
        mPolylineOptions.add(myLocation);
        mPolylineOptions.width(12);
        mPolylineOptions.color(Color.RED);
        mPolylineOptions.geodesic(true);

        mMap.addPolyline(mPolylineOptions);
        return true;
    }

    void onClickFind() {
        mService.getData(mEdtStart.getText().toString(), mEdtEnd.getText().toString(), KEY_DIRECTIONS).enqueue(new Callback<DirectionResult>() {
            @Override
            public void onResponse(Call<DirectionResult> call, Response<DirectionResult> response) {
                Locations toPosition = null;
                ArrayList<LatLng> routelist = new ArrayList<>();
                if (response.body().getRoutes().size() > 0) {
                    ArrayList<LatLng> decodelist;
                    Routes routeA = response.body().getRoutes().get(0);

                    if (routeA.getLegs().size() > 0) {
                        Distance distance = routeA.getLegs().get(0).getDistance();
                        Duration duration = routeA.getLegs().get(0).getDuration();
                        mTvDistance.setText(distance.getText());
                        mTvDuration.setText(duration.getText());
                        List<Steps> steps = routeA.getLegs().get(0).getSteps();
                        Steps step;
                        Locations location = null;
                        String polyline;
                        for (int i = 0; i < steps.size(); i++) {
                            step = steps.get(i);
                            location = step.getStartLocation();
                            routelist.add(new LatLng(location.getLat(), location.getLng()));
                            polyline = step.getPolyline().getPoints();
                            decodelist = RouteDecode.decodePoly(polyline);
                            routelist.addAll(decodelist);
                            location = step.getEnd_location();
                            routelist.add(new LatLng(location.getLat(), location.getLng()));
                        }
                        toPosition = location;
                    }
                }
                if (routelist.size() > 0) {
                    PolylineOptions rectLine = new PolylineOptions().width(10).color(Color.RED);

                    for (int i = 0; i < routelist.size(); i++) {
                        rectLine.add(routelist.get(i));
                    }
                    // Adding route on the map
                    mMap.addPolyline(rectLine);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(toPosition.getLat(), toPosition.getLng()));
                    markerOptions.draggable(true);
                    mMap.addMarker(markerOptions);
                }
            }

            @Override
            public void onFailure(Call<DirectionResult> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        onClickFind();
    }
}
