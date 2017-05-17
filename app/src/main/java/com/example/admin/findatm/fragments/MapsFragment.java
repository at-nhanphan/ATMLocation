package com.example.admin.findatm.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.example.admin.findatm.R;
import com.example.admin.findatm.activities.MainActivity;
import com.example.admin.findatm.adapters.ATMListViewPagerAdapter;
import com.example.admin.findatm.databases.MyDatabase;
import com.example.admin.findatm.models.MyATM;
import com.example.admin.findatm.utils.MyCurrentLocation;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.PageScrollStateChanged;
import org.androidannotations.annotations.PageSelected;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;
import java.util.List;

/**
 * class MapsFragment
 * Created by naunem on 10/05/2017.
 */

@EFragment(R.layout.fragment_maps)
public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationButtonClickListener {
    @StringRes(R.string.myLocation)
    String mStMyLocation;
    @ViewById(R.id.viewPager)
    ViewPager mViewPager;
    private List<MyATM> mAtms;
    private GoogleMap mMap;
    private Location mCurrentLocation;
    private ArrayList<Marker> mMarkers = new ArrayList<>();
    private int mCurrentPage;

    @AfterViews
    void init() {
        mAtms = new ArrayList<>();
        mViewPager.setPageMargin(10);
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getChildFragmentManager().beginTransaction().replace(R.id.mapView, mapFragment).commit();
        mapFragment.getMapAsync(this);
        mCurrentLocation = MyCurrentLocation.getCurrentLocation(getContext());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMyLocationButtonClickListener(this);
        mAtms = MainActivity.getListAtms();
        MyDatabase myDatabase = new MyDatabase(getContext());
        if (mAtms.size() > 0) {
            for (int i = 0; i < mAtms.size(); i++) {
                addMarker(new LatLng(Double.parseDouble(mAtms.get(i).getLat()), Double.parseDouble(mAtms.get(i).getLng())));
                for (int j = 0; j < myDatabase.getAll().size(); j++) {
                    if (mAtms.get(i).getMaDiaDiem().equals(myDatabase.getAll().get(j).getMaDiaDiem())) {
                        mAtms.get(i).setFavorite(true);
                    } else {
                        mAtms.get(i).setFavorite(false);
                    }
                }
            }
            mViewPager.setVisibility(View.VISIBLE);
            ATMListViewPagerAdapter adapter = new ATMListViewPagerAdapter(getFragmentManager(), mAtms);
            mViewPager.setAdapter(adapter);
            mViewPager.setCurrentItem(mCurrentPage + 1);
        } else {
            mViewPager.setVisibility(View.GONE);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(16.054031, 108.203836), 10));
        }
        zoomMapFitMarkers(mMarkers);
    }

    public void addMarker(LatLng latLng) {
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_choose)));
        mMarkers.add(marker);
    }

    public void zoomMapFitMarkers(ArrayList<Marker> markers) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        if (markers.size() > 0) {
            for (Marker marker : markers) {
                builder.include(marker.getPosition());
            }
            LatLngBounds bounds = builder.build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 100);
            mMap.moveCamera(cameraUpdate);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (mMarkers != null) {
            for (int i = 0; i < mMarkers.size(); i++) {
                if (marker.equals(mMarkers.get(i))) {
                    mViewPager.setCurrentItem(i + 1);
                }
            }
        }
        return false;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        MyCurrentLocation.checkLocationEnabled(getContext());
        if (mCurrentLocation != null) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()))
                    .title(mStMyLocation)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_icon))).showInfoWindow();
        } else {
            Toast.makeText(getContext(), "Location not found", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @PageSelected(R.id.viewPager)
    void onItemAtmSelected(int position) {
        mCurrentPage = position;
        if (position <= mMarkers.size() && position > 0) {
            mMap.animateCamera(CameraUpdateFactory.newLatLng(mMarkers.get(position - 1).getPosition()));
            mMarkers.get(position - 1).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_choose));
        }
        for (int i = 0; i < mMarkers.size(); i++) {
            if (i != (position - 1)) {
                mMarkers.get(i).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin));
            }
        }
    }

    @PageScrollStateChanged(R.id.viewPager)
    void onPageScrollChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            int pageCount = mAtms.size() + 2;
            if (mCurrentPage == 0) {
                mViewPager.setCurrentItem(pageCount - 2, false);
            } else if (mCurrentPage == pageCount - 1) {
                mViewPager.setCurrentItem(1, false);
            }
        }
    }
}
