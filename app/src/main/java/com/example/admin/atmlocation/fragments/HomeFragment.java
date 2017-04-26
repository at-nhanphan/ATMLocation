package com.example.admin.atmlocation.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.atmlocation.R;
import com.example.admin.atmlocation.activities.DetailActivity_;
import com.example.admin.atmlocation.activities.MainActivity_;
import com.example.admin.atmlocation.adapters.ATMListAdapter;
import com.example.admin.atmlocation.databases.MyDatabase;
import com.example.admin.atmlocation.interfaces.CallBack;
import com.example.admin.atmlocation.interfaces.MyOnClickFavoriteListener;
import com.example.admin.atmlocation.interfaces.MyOnClickListener;
import com.example.admin.atmlocation.interfaces.OnQueryTextChange;
import com.example.admin.atmlocation.models.MyATM;
import com.example.admin.atmlocation.models.googleDirections.MyLocation;
import com.example.admin.atmlocation.services.ATMServiceImpl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

import static android.content.Context.LOCATION_SERVICE;

/**
 * HomeFragment class
 * Created by naunem on 24/03/2017.
 */
@EFragment(R.layout.fragment_home)
public class HomeFragment extends Fragment implements MyOnClickListener, OnQueryTextChange, MyOnClickFavoriteListener {

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private ATMListAdapter mAdapter;
    private ArrayList<MyATM> mAtms;
    private SpotsDialog mDialog;
    private MyDatabase mMyDatabase;

    @AfterViews
    void init() {
        LinearLayoutManager ln = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(ln);
        mAtms = new ArrayList<>();
        mMyDatabase = new MyDatabase(getContext());

        for (int i = 0; i < mAtms.size(); i++) {
            for (int j = 0; j < mMyDatabase.getAll().size(); j++) {
                if (mAtms.get(i).getMaDiaDiem().equals(mMyDatabase.getAll().get(j))) {
                    mAtms.get(i).setFavorite(true);
                }
            }
        }

        mAdapter = new ATMListAdapter(getContext(), mAtms, this);

        mDialog = new SpotsDialog(getContext(), R.style.CustomDialog);
        new MyAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        ATMServiceImpl atmServiceImpl = new ATMServiceImpl(getContext());
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
            atmServiceImpl.getATM(new CallBack<ArrayList<MyATM>>() {
                @Override
                public void next(ArrayList<MyATM> myATMs) {
                    if (myATMs != null) {
                        mAtms.clear();
                        mAtms.addAll(myATMs);
                        for (int i = 0; i < mAtms.size(); i++) {
                            for (int j = 0; j < mMyDatabase.getAll().size(); j++) {
                                if (mAtms.get(i).getMaDiaDiem().equals(mMyDatabase.getAll().get(j).getMaDiaDiem())) {
                                    mAtms.get(i).setFavorite(true);
                                }
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
//            float latitude = (float) locations.getLatitude();
//            float longitude = (float) locations.getLongitude();
//            String location = latitude + "," + longitude;
//            String radius = "5000";
//            String types = "ATM";
//            atmServiceImpl.getNearATM(location, radius, types, new CallBack<ArrayList<ATM>>() {
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
        mAdapter.setMyOnClickFavoriteListener(this);
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
    public void onClick(int position) {
        MyLocation myLocation = new MyLocation(Double.parseDouble(mAdapter.getResultFilter().get(position).getLat()),
                Double.parseDouble(mAdapter.getResultFilter().get(position).getLng()));
        DetailActivity_.intent(this)
                .mAtm(mAdapter.getResultFilter().get(position))
                .mMyLocation(myLocation)
                .start();
    }

    @Override
    public void onTextChange(String newText) {
        mAdapter.getFilter().filter(newText);
    }

    @Override
    public void onClickFavorite(int position) {
        MyATM myATM = mAtms.get(position);
        ArrayList<MyATM> lists = mMyDatabase.getAll();
        if (myATM.isFavorite()) {
            int count = 0;
            if (lists.size() > 0) {
                for (int i = 0; i < lists.size(); i++) {
                    if (myATM.getMaDiaDiem().equals(lists.get(i).getMaDiaDiem())) {
                        Toast.makeText(getContext(), "Item is favorited", Toast.LENGTH_SHORT).show();
                    } else {
                        count++;
                    }
                }
            }
            if (count == lists.size()) {
                mMyDatabase.insertATM(myATM);
            }
        } else {
            if (lists.size() > 0) {
                for (int i = 0; i < lists.size(); i++) {
                    if (myATM.getMaDiaDiem().equals(lists.get(i).getMaDiaDiem())) {
                        mMyDatabase.deleteATM(Integer.parseInt(lists.get(i).getMaDiaDiem()));
                    }
                }
            }
        }
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            while (mAtms.size() <= 0) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mDialog.dismiss();
        }
    }
}
