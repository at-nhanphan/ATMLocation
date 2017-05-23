package com.example.admin.findatm.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.findatm.R;
import com.example.admin.findatm.activities.DetailActivity_;
import com.example.admin.findatm.activities.MainActivity;
import com.example.admin.findatm.activities.MapsActivity_;
import com.example.admin.findatm.adapters.ATMListAdapter;
import com.example.admin.findatm.databases.MyDatabase;
import com.example.admin.findatm.interfaces.CallBack;
import com.example.admin.findatm.interfaces.MyOnClickFavoriteListener;
import com.example.admin.findatm.interfaces.MyOnClickListener;
import com.example.admin.findatm.interfaces.OnQueryTextChange;
import com.example.admin.findatm.models.MyATM;
import com.example.admin.findatm.models.googleDirections.MyLocation;
import com.example.admin.findatm.services.ATMServiceImpl;
import com.example.admin.findatm.utils.MyCurrentLocation;
import com.example.admin.findatm.utils.NetworkConnection;
import com.google.android.gms.maps.model.LatLng;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dmax.dialog.SpotsDialog;

/**
 * HomeFragment class
 * Created by naunem on 24/03/2017.
 */
@EFragment(R.layout.fragment_home)
public class HomeFragment extends Fragment implements MyOnClickListener, MyOnClickFavoriteListener, OnQueryTextChange {

    private static final int REQUEST_CODE = 1;
    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @ViewById(R.id.tvReload)
    TextView mTvReload;
    private ATMListAdapter mAdapter;
    private List<MyATM> mAtms;
    private SpotsDialog mDialog;
    private MyDatabase mMyDatabase;
    private ATMServiceImpl mAtmServiceImpl;
    private double mLat;
    private double mLng;
    private boolean mCheck;
    private static final int ACCESS_FINE_LOCATION_AND_COARSE_LOCATION = 123;

    @AfterViews
    void init() {
        mTvReload.setVisibility(View.INVISIBLE);
        LinearLayoutManager ln = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(ln);
        mMyDatabase = new MyDatabase(getContext());
        mDialog = new SpotsDialog(getContext(), R.style.CustomDialog);
        ((MainActivity) getContext()).setOnQueryTextChange(this);
        mAtms = new ArrayList<>();
        mAdapter = new ATMListAdapter(getContext(), mAtms, this);
        mAtmServiceImpl = new ATMServiceImpl(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setMyOnClickFavoriteListener(this);
        askPermissionsAccessLocation();
        if (MyCurrentLocation.checkLocationEnabled(getContext())
                && NetworkConnection.isInternetConnected(getContext())) {
            new MyAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            mTvReload.setVisibility(View.VISIBLE);
        }
    }

    public void askPermissionsAccessLocation() {
        // Ask for permission with API >= 23.
        if (Build.VERSION.SDK_INT >= 23) {
            int accessCoarsePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);
            int accessFinePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

            if (accessCoarsePermission != PackageManager.PERMISSION_GRANTED
                    && accessFinePermission != PackageManager.PERMISSION_GRANTED) {

                // Permissions.
                String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION};

                // Dialog.
                ActivityCompat.requestPermissions(getActivity(), permissions, ACCESS_FINE_LOCATION_AND_COARSE_LOCATION);
                return;
            } else {
                getAtmAroundCurrentLocation();
                mTvReload.setVisibility(View.INVISIBLE);
            }
        } else {
            getAtmAroundCurrentLocation();
            mTvReload.setVisibility(View.INVISIBLE);
        }
    }

    public void getAtmAroundCurrentLocation() {
        Location currentLocation = MyCurrentLocation.getCurrentLocation(getContext());
        if (currentLocation != null) {
            mLat = currentLocation.getLatitude();
            mLng = currentLocation.getLongitude();
            MainActivity.setCurrentLocation(new LatLng(mLat, mLng));
            getDataResponse(mAtmServiceImpl, mLat, mLng, 2);
        } else {
            Toast.makeText(getContext(), "Finding your current location", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ACCESS_FINE_LOCATION_AND_COARSE_LOCATION:
                // If ignore: array null.
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Permission granted!", Toast.LENGTH_LONG).show();
                    // Display current location.
                }
                // Cancel or refuse.
                else {
                    Toast.makeText(getActivity(), "Permission denied!", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void getDataResponse(ATMServiceImpl atmServiceImpl, double lat, double lng, int radius) {
        atmServiceImpl.getATM(lat, lng, radius, new CallBack<ArrayList<MyATM>>() {
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
                    Collections.sort(mAtms, new Comparator<MyATM>() {
                        @Override
                        public int compare(MyATM o1, MyATM o2) {
                            return o1.getTenDiaDiem().compareTo(o2.getTenDiaDiem());
                        }
                    });
                    MainActivity.setListAtms(mAtms);
                    mAdapter.notifyDataSetChanged();
                    Log.d("dddd", "next: " + MainActivity.getListAtms().size());
                }
            }
        });
    }

    @Override
    public void onClick(int position) {
        MyLocation myLocation = new MyLocation(Double.parseDouble(mAdapter.getResultFilter().get(position).getLat()),
                Double.parseDouble(mAdapter.getResultFilter().get(position).getLng()));
        MapsActivity_.intent(this)
                .mAddressAtm(myLocation)
                .mAtm(mAdapter.getResultFilter().get(position))
                .start();
    }

    @Override
    public void onLongClick(int position) {
        MyLocation myLocation = new MyLocation(Double.parseDouble(mAdapter.getResultFilter().get(position).getLat()),
                Double.parseDouble(mAdapter.getResultFilter().get(position).getLng()));
        DetailActivity_.intent(this)
                .mAtm(mAdapter.getResultFilter().get(position))
                .mMyLocation(myLocation)
                .startForResult(REQUEST_CODE);
    }

    @OnActivityResult(REQUEST_CODE)
    void onResult(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            getDataResponse(mAtmServiceImpl, mLat, mLng, 2);
        }
    }

    @Click(R.id.tvReload)
    void clickReload() {
        askPermissionsAccessLocation();
        if (MyCurrentLocation.checkLocationEnabled(getContext())
                && NetworkConnection.isInternetConnected(getContext())) {
            getAtmAroundCurrentLocation();
            new MyAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            mTvReload.setVisibility(View.INVISIBLE);
        } else {
            mTvReload.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClickFavorite(int position) {
        MyATM myATM = mAdapter.getResultFilter().get(position);
        if (myATM.isFavorite()) {
            mMyDatabase.insertATM(myATM);
            Toast.makeText(getContext(), "You're favorited this item", Toast.LENGTH_SHORT).show();
        } else {
            mMyDatabase.deleteATM(Integer.parseInt(myATM.getMaDiaDiem()));
            Toast.makeText(getContext(), "You're unfavorited this item", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTextChange(String newText) {
        if (mAdapter != null) {
            mAdapter.getValueFilter().filter(newText);
        }
    }

    //=============================================================//
    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog.show();
            mTvReload.setVisibility(View.INVISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            int count = 0;
            while (mAtms.size() <= 0) {
                count++;
                try {
                    Thread.sleep(1000);
                    mCheck = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (count >= 3) {
                    mCheck = true;
                    break;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mDialog.dismiss();
            try {
                if (mCheck) {
                    mTvReload.setVisibility(View.VISIBLE);
                    MainActivity.setListAtms(new ArrayList<MyATM>());
                } else {
                    mTvReload.setVisibility(View.INVISIBLE);
                }
            } catch (NullPointerException ignored) {
                Log.e("ddd", "onPostExecute: ", ignored);
            }
        }
    }
    //=============================================================//
}
