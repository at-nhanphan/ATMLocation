package com.example.admin.atmlocation.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.admin.atmlocation.R;
import com.example.admin.atmlocation.activities.DetailActivity_;
import com.example.admin.atmlocation.activities.MainActivity_;
import com.example.admin.atmlocation.adapters.ATMListAdapter;
import com.example.admin.atmlocation.interfaces.CallBack;
import com.example.admin.atmlocation.interfaces.MyOnClickListener;
import com.example.admin.atmlocation.interfaces.OnQueryTextChange;
import com.example.admin.atmlocation.models.ATM;
import com.example.admin.atmlocation.services.ATMServiceImpl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;

/**
 * HomeFragment
 * Created by naunem on 24/03/2017.
 */
@EFragment(R.layout.fragment_home)
public class HomeFragment extends Fragment implements MyOnClickListener, OnQueryTextChange {

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private ATMListAdapter mAdapter;
    private ArrayList<ATM> mAtms;

    @AfterViews
    void init() {
        LinearLayoutManager ln = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(ln);
        mAtms = new ArrayList<>();

        mAdapter = new ATMListAdapter(getContext(), mAtms, this);
        ATMServiceImpl mAtmService = new ATMServiceImpl(getContext());

        ((MainActivity_) getContext()).setOnQueryTextChange(this);
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

        LocationManager locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5000, locationListener);
        Location locations = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (locations != null) {
            float latitude = (float) locations.getLatitude();
            float longitude = (float) locations.getLongitude();
            String location = latitude + "," + longitude;
            String radius = "5000";
            String types = "ATM";
            mAtmService.getNearATM(location, radius, types, new CallBack<ArrayList<ATM>>() {
                @Override
                public void next(ArrayList<ATM> atm) {
                    mAtms.addAll(atm);
                    mAdapter.notifyDataSetChanged();
                }
            });
//            mAtmService.getATM("ATM+hoa+khanh", new CallBack<ArrayList<ATM>>() {
//                @Override
//                public void next(ArrayList<ATM> atms) {
//                    mAtms.addAll(atms);
//                    mAdapter.notifyDataSetChanged();
//                }
//            });
        } else {
            Log.e("location null", "onCreateView: ");
        }
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(int position) {
        DetailActivity_.intent(this)
                .mAtm(mAtms.get(position))
                .mMyLocation(mAtms.get(position).getGeometry().getLocation())
                .start();
    }
    @Override
    public void onTextChange(String newText) {
        mAdapter.getFilter().filter(newText);
    }
}
