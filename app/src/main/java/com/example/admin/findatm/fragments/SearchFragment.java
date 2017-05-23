package com.example.admin.findatm.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.findatm.R;
import com.example.admin.findatm.activities.DetailActivity_;
import com.example.admin.findatm.activities.ListBankDistrictActivity_;
import com.example.admin.findatm.activities.MapsActivity_;
import com.example.admin.findatm.adapters.ATMListAdapter;
import com.example.admin.findatm.databases.MyDatabase;
import com.example.admin.findatm.interfaces.CallBack;
import com.example.admin.findatm.interfaces.MyOnClickFavoriteListener;
import com.example.admin.findatm.interfaces.MyOnClickListener;
import com.example.admin.findatm.models.MyATM;
import com.example.admin.findatm.models.googleDirections.MyLocation;
import com.example.admin.findatm.services.ATMServiceImpl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * SearchActivity class
 * Created by naunem on 05/04/2017.
 */
@EFragment(R.layout.fragment_search)
public class SearchFragment extends Fragment implements MyOnClickListener, MyOnClickFavoriteListener {
    @ViewById(R.id.toolbar)
    Toolbar mToolbar;
    @ViewById(R.id.tvBank)
    TextView mTvBank;
    @ViewById(R.id.tvArea)
    TextView mTvArea;
    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @ViewById(R.id.tvMessage)
    TextView mTvMessage;
    @ViewById(R.id.progressBar)
    ProgressBar mProgressBar;

    private int mPositionBank = -1;
    private int mPositionDistrict = -1;

    private static final int REQUEST_CODE_BANK = 1;
    private static final int REQUEST_CODE_AREA = 2;
    private ATMListAdapter mAdapter;
    private ArrayList<MyATM> mAtms;
    private MyDatabase mMyDatabase;
    private boolean mCheck;

    @AfterViews
    void init() {
        mProgressBar.setVisibility(View.GONE);
        mTvMessage.setVisibility(View.INVISIBLE);
//        mDialog = new SpotsDialog(getContext(), R.style.CustomDialog);
        mMyDatabase = new MyDatabase(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Click({R.id.tvBank, R.id.tvArea})
    void clickChoose(View v) {
        switch (v.getId()) {
            case R.id.tvBank:
                ListBankDistrictActivity_.intent(this)
                        .mCode(REQUEST_CODE_BANK)
                        .mPositionBank(mPositionBank)
                        .startForResult(REQUEST_CODE_BANK);
                break;
            case R.id.tvArea:
                ListBankDistrictActivity_.intent(this)
                        .mCode(REQUEST_CODE_AREA)
                        .mPositionDistrict(mPositionDistrict)
                        .startForResult(REQUEST_CODE_AREA);
                break;
        }
    }

    @OnActivityResult(REQUEST_CODE_BANK)
    void onResultBank(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            String resultBank = data.getStringExtra("resultBank");
            int position = data.getIntExtra("positionBank", -1);
            if (position != -1) {
                mTvBank.setText(resultBank);
                mPositionBank = position;
            }
        }
    }

    @OnActivityResult(REQUEST_CODE_AREA)
    void onResultDistrict(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            String resultDistrict = data.getStringExtra("resultDistrict");
            int position = data.getIntExtra("positionDistrict", -1);
            if (position != -1) {
                mTvArea.setText(resultDistrict);
                mPositionDistrict = position;
            }
        }
    }

    @Click(R.id.tvSearch)
    void clickSearch() {
        if (mTvBank.getText().equals("Bank") || mTvArea.getText().equals("District")) {
            Toast.makeText(getContext(), "Please choose bank and district correctly", Toast.LENGTH_SHORT).show();
        } else {
            mTvMessage.setVisibility(View.INVISIBLE);
            mAtms = new ArrayList<>();
            loadData();
            new MyAsyncTask().execute();
            mAdapter = new ATMListAdapter(mAtms, this);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setMyOnClickFavoriteListener(this);
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
                .start();
    }


    public void loadData() {
        ATMServiceImpl atmServiceImpl = new ATMServiceImpl(getContext());
        atmServiceImpl.getAtmSearch(String.valueOf(mTvBank.getText()), String.valueOf(mTvArea.getText()), new CallBack<ArrayList<MyATM>>() {
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
    }

    @Override
    public void onClickFavorite(int position) {
        MyATM myATM = mAtms.get(position);
        if (myATM.isFavorite()) {
                mMyDatabase.insertATM(myATM);
                Toast.makeText(getContext(), "You're favorited this item", Toast.LENGTH_SHORT).show();
        } else {
            mMyDatabase.deleteATM(Integer.parseInt(myATM.getMaDiaDiem()));
            Toast.makeText(getContext(), "You're unfavorited this item", Toast.LENGTH_SHORT).show();
        }
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
            mTvMessage.setVisibility(View.INVISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            int count = 0;
            while (mAtms.size() <= 0) {
                count++;
                try {
                    Thread.sleep(500);
                    mCheck = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (count >= 10) {
                    mCheck = true;
                    break;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                mProgressBar.setVisibility(View.GONE);
                if (mCheck) {
                    mTvMessage.setVisibility(View.VISIBLE);
                } else {
                    mTvMessage.setVisibility(View.INVISIBLE);
                }
            } catch (NullPointerException ignored) {
                Log.e("dddd", "onPostExecute: ", ignored);
            }
        }
    }
}

