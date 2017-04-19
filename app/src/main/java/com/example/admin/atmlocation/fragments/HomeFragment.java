package com.example.admin.atmlocation.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.example.admin.atmlocation.models.MyATM;
import com.example.admin.atmlocation.models.googleDirections.MyLocation;
import com.example.admin.atmlocation.services.ATMServiceImpl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;

/**
 * HomeFragment class
 * Created by naunem on 24/03/2017.
 */
@EFragment(R.layout.fragment_home)
public class HomeFragment extends Fragment implements MyOnClickListener, OnQueryTextChange {

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private ATMListAdapter mAdapter;
    private ArrayList<MyATM> mAtms;

    @AfterViews
    void init() {
        LinearLayoutManager ln = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(ln);
        mAtms = new ArrayList<>();

        mAdapter = new ATMListAdapter(getContext(), mAtms, this);

        ATMServiceImpl mAtmService = new ATMServiceImpl(getContext());
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
        checkLocationEnabled();
        LocationManager locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5000, locationListener);
        Location locations = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (locations != null) {
            mAtmService.getATM(new CallBack<ArrayList<MyATM>>() {
                @Override
                public void next(ArrayList<MyATM> myATMs) {
                    mAtms.addAll(myATMs);
                    Log.d("aaaaa", "next: " + mAtms.size());
                    mAdapter.notifyDataSetChanged();
                }
            });
//            float latitude = (float) locations.getLatitude();
//            float longitude = (float) locations.getLongitude();
//            String location = latitude + "," + longitude;
//            String radius = "5000";
//            String types = "ATM";
//            mAtmService.getNearATM(location, radius, types, new CallBack<ArrayList<ATM>>() {
//                @Override
//                public void next(ArrayList<ATM> atm) {
//                    mAtms.addAll(atm);
//                    mAdapter.notifyDataSetChanged();
//                }
//            });
        } else {
            Log.e("location null", "onCreateView: ");
        }
        mRecyclerView.setAdapter(mAdapter);
        ((MainActivity_) getContext()).setOnQueryTextChange(this);
    }

    public void checkLocationEnabled() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ignored) {

        }

        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ignored) {

        }

        if (!gps_enabled && !network_enabled) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setCancelable(false);
            dialog.setMessage(getContext().getResources().getString(R.string.gps_network_not_enabled));
            dialog.setPositiveButton(getResources().getString(R.string.positiveButton), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_SETTINGS);
                    getContext().startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton(getResources().getString(R.string.cancelButton), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(int position) {
        MyLocation myLocation = new MyLocation(Double.parseDouble(mAtms.get(position).getLat()),
                Double.parseDouble(mAtms.get(position).getLng()));
        DetailActivity_.intent(this)
                .mAtm(mAtms.get(position))
                .mMyLocation(myLocation)
                .start();
    }

    @Override
    public void onTextChange(String newText) {
        mAdapter.getFilter().filter(newText);
    }
}
