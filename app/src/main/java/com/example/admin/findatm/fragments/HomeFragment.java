package com.example.admin.findatm.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * HomeFragment class
 * Created by naunem on 24/03/2017.
 */
@EFragment(R.layout.fragment_home)
public class HomeFragment extends Fragment implements MyOnClickListener, MyOnClickFavoriteListener, OnQueryTextChange {

    private static final int REQUEST_CODE_DETAIL = 1111;
    private static final int ACCESS_FINE_LOCATION_AND_COARSE_LOCATION = 123;
    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @ViewById(R.id.progressBar)
    ProgressBar mProgressBar;
    @ViewById(R.id.imgWifi)
    ImageView mImgWifi;

    private ATMListAdapter mAdapter;
    private List<MyATM> mAtms;
    private MyDatabase mMyDatabase;
    private ATMServiceImpl mAtmServiceImpl;
    private Animation mAnimation;
    private ArrayList<MyATM> mListATMs = new ArrayList<>();

    @AfterViews
    void init() {
        LinearLayoutManager ln = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(ln);
        initAfterView();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setMyOnClickFavoriteListener(this);
        askPermissionsAccessLocation();
        checkWifiAndLocation();
    }

    private void initAfterView() {
        mMyDatabase = new MyDatabase(getContext());
        mImgWifi.setVisibility(View.GONE);
        ((MainActivity) getContext()).setOnQueryTextChange(this);
        mAtms = new ArrayList<>();
        mAdapter = new ATMListAdapter(mAtms, this);
        mAtmServiceImpl = new ATMServiceImpl(getContext());
        mAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.blink);
    }

    private void checkWifiAndLocation() {
        if (MyCurrentLocation.checkLocationEnabled(getContext())) {
            if (NetworkConnection.isInternetConnected(getContext())) {
                mImgWifi.setVisibility(View.GONE);
                new MyAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                mImgWifi.setImageResource(R.drawable.ic_wifi_off_brown_200_48dp);
                mImgWifi.startAnimation(mAnimation);
            }
        } else {
            mImgWifi.setImageResource(R.drawable.ic_location_off_brown_200_48dp);
        }
    }

    private void askPermissionsAccessLocation() {
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
            } else {
                getAtmAroundCurrentLocation();
            }
        } else {
            getAtmAroundCurrentLocation();
        }
    }

    private void getAtmAroundCurrentLocation() {
        Location currentLocation = MyCurrentLocation.getCurrentLocation(getContext());
        if (currentLocation != null) {
            if (NetworkConnection.isInternetConnected(getContext())) {
                mImgWifi.setVisibility(View.GONE);
                getDataResponse(mAtmServiceImpl, currentLocation.getLatitude(), currentLocation.getLongitude(), 2);
            } else {
                mImgWifi.setVisibility(View.VISIBLE);
                mImgWifi.setImageResource(R.drawable.ic_wifi_off_brown_200_48dp);
                mProgressBar.setVisibility(View.GONE);
            }
        } else {
            mImgWifi.setVisibility(View.VISIBLE);
            mImgWifi.setImageResource(R.drawable.ic_location_off_brown_200_48dp);
            mProgressBar.setVisibility(View.GONE);
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
                    Toast.makeText(getActivity(), R.string.permission_granted, Toast.LENGTH_LONG).show();
                    // Display current location.
                } else { // Cancel or refuse.
                    Toast.makeText(getActivity(), R.string.permission_denied, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void getDataResponse(ATMServiceImpl atmServiceImpl, double lat, double lng, int radius) {
        atmServiceImpl.getATM(lat, lng, radius, new CallBack<ArrayList<MyATM>>() {
            @Override
            public void next(ArrayList<MyATM> myATMs) {
                if (myATMs != null) {
                    mAtms.clear();
                    mAtms.addAll(myATMs);
                    mListATMs.addAll(myATMs);
                    for (int i = 0; i < mAtms.size(); i++) {
                        for (int j = 0; j < mMyDatabase.getAll().size(); j++) {
                            if (mAtms.get(i).getAddressId().equals(mMyDatabase.getAll().get(j).getAddressId())) {
                                mAtms.get(i).setFavorite(true);
                            }
                        }
                    }
                    Collections.sort(mAtms, new Comparator<MyATM>() {
                        @Override
                        public int compare(MyATM o1, MyATM o2) {
                            return o1.getAddressName().compareTo(o2.getAddressName());
                        }
                    });
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Click(R.id.imgWifi)
    void clickImgWifi() {
        askPermissionsAccessLocation();
        if (MyCurrentLocation.checkLocationEnabled(getContext())) {
            if (NetworkConnection.isInternetConnected(getContext())) {
                mImgWifi.setVisibility(View.GONE);
                new MyAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                mImgWifi.setImageResource(R.drawable.ic_wifi_off_brown_200_48dp);
                mImgWifi.startAnimation(mAnimation);
            }
        } else {
            mImgWifi.setImageResource(R.drawable.ic_location_off_brown_200_48dp);
        }
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
                .startForResult(REQUEST_CODE_DETAIL);
    }

    @OnActivityResult(REQUEST_CODE_DETAIL)
    void onResult(int resultCode) {
        if (resultCode == Activity.RESULT_OK) {
            for (int i = 0; i < mAtms.size(); i++) {
                for (int j = 0; j < mMyDatabase.getAll().size(); j++) {
                    if (mAtms.get(i).getAddressId().equals(mMyDatabase.getAll().get(j).getAddressId())) {
                        mAtms.get(i).setFavorite(true);
                    }
                }
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClickFavorite(int position) {
        MyATM myATM = mAdapter.getResultFilter().get(position);
        if (myATM.isFavorite()) {
            mMyDatabase.insertATM(myATM);
            Toast.makeText(getContext(), R.string.favorite_item, Toast.LENGTH_SHORT).show();
        } else {
            mMyDatabase.deleteATM(Integer.parseInt(myATM.getAddressId()));
            Toast.makeText(getContext(), R.string.unfavorite_item, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTextChange(String newText) {
        if (mAdapter != null) {
            mAdapter.getValueFilter().filter(newText);
        }
    }

    public ArrayList<MyATM> getListATMs() {
        return mListATMs;
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            while (mAtms.size() <= 0) {
                SystemClock.sleep(1000);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                mProgressBar.setVisibility(View.GONE);
            } catch (NullPointerException ignored) {
            }
        }
    }
}
