package com.example.admin.findatm.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.admin.findatm.R;
import com.example.admin.findatm.fragments.AboutFragment_;
import com.example.admin.findatm.fragments.FavoriteFragment_;
import com.example.admin.findatm.fragments.HomeFragment_;
import com.example.admin.findatm.fragments.MapsFragment_;
import com.example.admin.findatm.fragments.SearchFragment_;
import com.example.admin.findatm.interfaces.OnQueryTextChange;
import com.example.admin.findatm.models.MyATM;
import com.google.android.gms.maps.model.LatLng;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity class
 * Created by naunem on 24/03/2017.
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @ViewById(R.id.imgSearch)
    ImageView mImgSearch;
    @ViewById(R.id.imgSwipe)
    ImageView mImgSwipe;
    @ViewById(R.id.rlSearch)
    RelativeLayout mRlSearch;
    @ViewById(R.id.edtSearch)
    EditText mEdtSearch;

    private OnQueryTextChange mOnQueryTextChange;
    private static boolean mChange;
    private boolean mCheck = false;
    private FragmentManager mManager;
    private boolean mFlag;
    private boolean mClick;
    private static LatLng mCurrentLocation;
    private static List<MyATM> mListAtms = new ArrayList<>();

    @AfterViews
    void init() {
        mRlSearch.setVisibility(View.GONE);
        mManager = getSupportFragmentManager();
        mManager.beginTransaction().replace(R.id.flContainer, new HomeFragment_()).commit();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        addSearchListener();
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                //your code here
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5000, locationListener);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            mCurrentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        }
    }

    @Click(R.id.imgSearch)
    void clickSearch() {
        if (!mClick) {
            mRlSearch.setVisibility(View.VISIBLE);
            mClick = true;
        } else {
            mRlSearch.setVisibility(View.GONE);
            mClick = false;
        }
    }

    @Click(R.id.imgSwipe)
    void clickSwipe() {
        Fragment fragment;
        if (!mCheck) {
            fragment = new MapsFragment_();
            mImgSwipe.setBackgroundResource(R.drawable.ic_list_white_24dp);
            mCheck = true;
        } else {
            fragment = new HomeFragment_();
            mImgSwipe.setBackgroundResource(R.drawable.ic_google_maps);
            mCheck = false;
        }
        mManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
    }

    public static LatLng getCurrentLocation() {
        return mCurrentLocation;
    }

    public void setCurrentLocation(LatLng currentLocation) {
        mCurrentLocation = currentLocation;
    }

    public static boolean isChange() {
        return mChange;
    }

    public static void setChange(boolean isChange) {
        mChange = isChange;
    }

    public void setOnQueryTextChange(OnQueryTextChange onQueryTextChange) {
        this.mOnQueryTextChange = onQueryTextChange;
    }

    public void addSearchListener() {
        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mOnQueryTextChange.onTextChange(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Click(R.id.imgDelete)
    void clickDelete() {
        mEdtSearch.setText("");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.home:
                if (mCheck) {
                    fragment = new MapsFragment_();
                } else {
                    fragment = new HomeFragment_();
                }
                mImgSwipe.setVisibility(View.VISIBLE);
                mImgSearch.setVisibility(View.VISIBLE);
                break;
            case R.id.favorite:
                fragment = new FavoriteFragment_();
                mImgSwipe.setVisibility(View.VISIBLE);
                mImgSearch.setVisibility(View.VISIBLE);
                break;
            case R.id.search:
                fragment = new SearchFragment_();
                mImgSwipe.setVisibility(View.GONE);
                mImgSearch.setVisibility(View.GONE);
                break;
            case R.id.about:
                fragment = new AboutFragment_();
                mImgSwipe.setVisibility(View.GONE);
                mImgSearch.setVisibility(View.GONE);
                break;
        }
        mManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
        return true;
    }

    public static List<MyATM> getListAtms() {
        return mListAtms;
    }

    public static void setListAtms(List<MyATM> listAtms) {
        mListAtms = listAtms;
    }
}
