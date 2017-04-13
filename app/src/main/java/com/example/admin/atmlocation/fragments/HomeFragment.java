package com.example.admin.atmlocation.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.atmlocation.R;
import com.example.admin.atmlocation.activities.DetailActivity;
import com.example.admin.atmlocation.activities.MainActivity;
import com.example.admin.atmlocation.adapters.ATMListAdapter;
import com.example.admin.atmlocation.interfaces.CallBack;
import com.example.admin.atmlocation.interfaces.MyOnClickListener;
import com.example.admin.atmlocation.interfaces.OnQueryTextChange;
import com.example.admin.atmlocation.models.ATM;
import com.example.admin.atmlocation.services.ATMServiceImpl;

import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;

/**
 * HomeFragment
 * Created by naunem on 24/03/2017.
 */

public class HomeFragment extends Fragment implements MyOnClickListener, OnQueryTextChange {

    private ATMListAdapter mAdapter;
    private ArrayList<ATM> mAtms;
    float latitude;
    float longitude;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager ln = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(ln);
        mAtms = new ArrayList<>();

        mAdapter = new ATMListAdapter(view.getContext(), mAtms, this);
        ATMServiceImpl mAtmService = new ATMServiceImpl(view.getContext());

        ((MainActivity) getContext()).setOnQueryTextChange(this);
        LocationListener mLocationListener = new LocationListener() {
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

        LocationManager mLocationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5000, mLocationListener);
        Location locations = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (locations != null) {
            latitude = (float) locations.getLatitude();
            longitude = (float) locations.getLongitude();
            String location = latitude + "," + longitude;
            String radius = "1000";
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
        return view;
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("object", mAtms.get(position));
        bundle.putParcelable("location", mAtms.get(position).getGeometry().getLocation());
        intent.putExtra("data", bundle);
        startActivity(intent);
    }
    @Override
    public void onTextChange(String newText) {
        mAdapter.getFilter().filter(newText);
    }
}
