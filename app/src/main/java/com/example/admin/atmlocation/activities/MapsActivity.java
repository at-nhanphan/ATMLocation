package com.example.admin.atmlocation.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.admin.atmlocation.R;
import com.example.admin.atmlocation.adapters.StepAdapter;
import com.example.admin.atmlocation.interfaces.ATMService;
import com.example.admin.atmlocation.models.MyATM;
import com.example.admin.atmlocation.models.googleDirections.DirectionResult;
import com.example.admin.atmlocation.models.googleDirections.Distance;
import com.example.admin.atmlocation.models.googleDirections.Duration;
import com.example.admin.atmlocation.models.googleDirections.Leg;
import com.example.admin.atmlocation.models.googleDirections.MyLocation;
import com.example.admin.atmlocation.models.googleDirections.Route;
import com.example.admin.atmlocation.models.googleDirections.RouteDecode;
import com.example.admin.atmlocation.models.googleDirections.Step;
import com.example.admin.atmlocation.services.ApiUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.PageScrollStateChanged;
import org.androidannotations.annotations.PageSelected;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MapsActivity class
 * Created by naunem on 30/03/2017.
 */
@EActivity(R.layout.acitivity_maps)
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMarkerClickListener {

    @ViewById(R.id.toolbar)
    Toolbar mToolbar;
    @ViewById(R.id.searchView)
    SearchView mSearchView;
    @ViewById(R.id.tvDistance)
    TextView mTvDistance;
    @ViewById(R.id.tvDuration)
    TextView mTvDuration;
    @StringRes(R.string.direction_key)
    String KEY_DIRECTIONS;
    @ViewById(R.id.viewPager)
    ViewPager mViewPager;
    @Extra
    MyATM mAtm;
    @Extra
    MyLocation mAtmMyLocation;
    private GoogleMap mMap;
    private PolylineOptions mPolylineOptions;
    private ATMService mService;
    private ArrayList<Step> mSteps;
    private ArrayList<Leg> mLegs;
    private double mAtmLatitude;
    private double mAtmLongitude;
    private LatLng mLocation;
    private int mCurrentPage;
    private final ArrayList<Marker> mMarkers = new ArrayList<>();

    @AfterViews
    void init() {
        setSupportActionBar(mToolbar);
        mViewPager.setPageMargin(20);
        mService = ApiUtils.getService();
        mPolylineOptions = new PolylineOptions();
        SupportMapFragment mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
        mAtmLatitude = mAtmMyLocation.getLat();
        mAtmLongitude = mAtmMyLocation.getLng();
        mLocation = new LatLng(mAtmLatitude, mAtmLongitude);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        mPolylineOptions.add(mLocation);
        mMap.addMarker(new MarkerOptions()
                .position(mLocation)
                .title(mAtm.getTenDiaDiem())
                .snippet(mAtmLatitude + ", " + mAtmLongitude)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin)))
                .showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mLocation));
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
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        LatLng myLocation = new LatLng(getCurrentLocation().getLat(), getCurrentLocation().getLng());
        MarkerOptions marker = new MarkerOptions()
                .position(myLocation)
                .title("My MyLocation")
                .snippet("ahihihi")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin));
        mMap.addMarker(marker).showInfoWindow();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 16));
        return true;
    }

    private MyLocation getCurrentLocation() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        return new MyLocation(location.getLatitude(), location.getLongitude());
    }

    private void addMarker(LatLng latLng) {
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin)));
        mMarkers.add(marker);
    }

    private void drawRoute() {
        Call<DirectionResult> results = mService.getData(getCurrentLocation().getLat() + "," + getCurrentLocation().getLng(),
                mAtmMyLocation.getLat() + "," + mAtmMyLocation.getLng(), KEY_DIRECTIONS);
        Log.d("vvvv", "drawRoute: " + getCurrentLocation().getLat() + "," + getCurrentLocation().getLng() + "----------" + mAtmMyLocation.getLat() + "," + mAtmMyLocation.getLng());
        results.enqueue(new Callback<DirectionResult>() {
            @Override
            public void onResponse(Call<DirectionResult> call, Response<DirectionResult> response) {
                MyLocation toPosition = null;
                Log.d("aaa", "onResponse: ");
                ArrayList<LatLng> routeList = new ArrayList<>();
                mMap.clear();
                if (response.body().getRoutes().size() > 0) {
                    ArrayList<LatLng> decodeList;
                    Route routeA = response.body().getRoutes().get(0);
                    mLegs = routeA.getLegs();
                    if (mLegs.size() > 0) {
                        Distance distance = mLegs.get(0).getDistance();
                        Duration duration = mLegs.get(0).getDuration();
                        mTvDistance.setText(distance.getText());
                        mTvDuration.setText(duration.getText());

                        // Add start marker
                        addMarker(new LatLng(mLegs.get(0).getStartLocation().getLat(), mLegs.get(0).getStartLocation().getLng()));

                        mSteps = new ArrayList<>();
                        mSteps = routeA.getLegs().get(0).getSteps();
                        Step step;
                        MyLocation location = null;
                        String polyline;
                        for (int i = 0; i < mSteps.size(); i++) {
                            step = mSteps.get(i);
                            location = step.getStartLocation();
                            routeList.add(new LatLng(location.getLat(), location.getLng()));
                            polyline = step.getPolyline().getPoints();
                            decodeList = RouteDecode.decodePoly(polyline);
                            routeList.addAll(decodeList);
                            location = step.getEndLocation();
                            routeList.add(new LatLng(location.getLat(), location.getLng()));
                            // Add step maker
                            addMarker(new LatLng(step.getStartLocation().getLat(), step.getStartLocation().getLng()));

                        }
                        // Add end marker
                        addMarker(new LatLng(mLegs.get(0).getEndLocation().getLat(), mLegs.get(0).getEndLocation().getLng()));
                        toPosition = location;
                    }
                }
                if (routeList.size() > 0) {
                    PolylineOptions rectLine = new PolylineOptions().width(10).color(Color.RED);

                    for (int i = 0; i < routeList.size(); i++) {
                        rectLine.add(routeList.get(i));
                    }
                    // Adding route on the map
                    mMap.addPolyline(rectLine);
                    mMap.animateCamera(CameraUpdateFactory
                            .newLatLngZoom(new LatLng(toPosition.getLat(), toPosition.getLng()), 16));
                }
                if (mLegs == null) {
                    mViewPager.setVisibility(View.GONE);
                    Log.d("dddd", "init: bbbbbbbbbb");
                } else {
                    Log.d("dddd", "init: " + mSteps.size());
                    mViewPager.setVisibility(View.VISIBLE);
                    StepAdapter stepAdapter = new StepAdapter(getSupportFragmentManager(), mLegs);
                    mViewPager.setAdapter(stepAdapter);
                    mViewPager.setCurrentItem(mCurrentPage + 1);
                }
            }

            @Override
            public void onFailure(Call<DirectionResult> call, Throwable t) {
                Log.d("aaa", "onFailure: ");
            }
        });
    }

    @Click(R.id.btnFind)
    public void onClickFind() {
        Log.d("dddd", "onClickFind: ");
        drawRoute();
    }

    @PageSelected(R.id.viewPager)
    void onItemAtmSelected(int position) {
        mCurrentPage = position;
        double lat;
        double lng;
        if (position == 0) {
            return;
        } else if (position == 1) {
            lat = mLegs.get(0).getStartLocation().getLat();
            lng = mLegs.get(0).getStartLocation().getLng();
        } else if (position >= mSteps.size() + 1) {
            lat = mLegs.get(0).getEndLocation().getLat();
            lng = mLegs.get(0).getEndLocation().getLng();
        } else {
            lat = mSteps.get(position - 1).getStartLocation().getLat();
            lng = mSteps.get(position - 1).getStartLocation().getLng();
        }
        LatLng latLng = new LatLng(lat, lng);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

        if (position < mMarkers.size()) {
            mMarkers.get(position).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_choose));
        }
        for (int i = 0; i < mMarkers.size(); i++) {
            if (position == 1) {
                mMarkers.get(position).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_choose));
            }
            if (i != position) {
                mMarkers.get(i).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin));
            }
        }
    }

    @PageScrollStateChanged(R.id.viewPager)
    void onPageScrollChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            int pageCount = mSteps.size() + 3;
            if (mCurrentPage == 0) {
                mViewPager.setCurrentItem(pageCount - 2, false);
            } else if (mCurrentPage == pageCount - 1) {
                mViewPager.setCurrentItem(1, false);
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for (int i = 0; i < mMarkers.size(); i++) {
            if (marker.equals(mMarkers.get(i))) {
                mViewPager.setCurrentItem(i);
            }
        }
        return true;
    }
}
