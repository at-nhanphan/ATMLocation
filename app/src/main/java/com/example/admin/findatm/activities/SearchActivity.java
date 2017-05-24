package com.example.admin.findatm.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.findatm.R;
import com.example.admin.findatm.adapters.ATMListAdapter;
import com.example.admin.findatm.databases.MyDatabase;
import com.example.admin.findatm.interfaces.CallBack;
import com.example.admin.findatm.interfaces.MyOnClickFavoriteListener;
import com.example.admin.findatm.interfaces.MyOnClickListener;
import com.example.admin.findatm.models.MyATM;
import com.example.admin.findatm.models.googleDirections.MyLocation;
import com.example.admin.findatm.services.ATMServiceImpl;
import com.example.admin.findatm.utils.NetworkConnection;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * SearchActivity class
 * Created by naunem on 05/04/2017.
 */
@EActivity(R.layout.activity_search)
public class SearchActivity extends AppCompatActivity implements MyOnClickListener, MyOnClickFavoriteListener {
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
    @ViewById(R.id.imgWifi)
    ImageView mImgWifi;
    @Extra
    int mCode;
    @Extra
    String mResultBank;
    @Extra
    String mResultDistrict;
    @Extra
    int mPositionBank;
    @Extra
    int mPositionDistrict;
    private static final int REQUEST_CODE_BANK = 1;
    private static final int REQUEST_CODE_AREA = 2;
    private ATMListAdapter mAdapter;
    private ArrayList<MyATM> mAtms;
    private MyDatabase mMyDatabase;
    private boolean mCheck;
    private Animation mAnimation;

    @AfterViews
    void init() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTvMessage.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mImgWifi.setVisibility(View.GONE);
        mMyDatabase = new MyDatabase(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mPositionBank = -1;
        mPositionDistrict = -1;
        if (null != mResultBank) {
            mTvBank.setText(mResultBank);
        }
        if (null != mResultDistrict) {
            mTvArea.setText(mResultDistrict);
        }

        mAnimation = AnimationUtils.loadAnimation(this, R.anim.blink);
    }

    @Click({R.id.tvBank, R.id.tvArea, R.id.imgBack})
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
            case R.id.imgBack:
                finish();
        }
    }

    @OnActivityResult(REQUEST_CODE_BANK)
    void onResultBank(int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
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
        if (resultCode == RESULT_OK && data != null) {
            String resultDistrict = data.getStringExtra("resultDistrict");
            int position = data.getIntExtra("positionDistrict", -1);
            if (position != -1) {
                mPositionDistrict = position;
                mTvArea.setText(resultDistrict);
            }
        }
    }

    @Click(R.id.tvSearch)
    void clickSearch() {
        if (mTvBank.getText().equals("Bank") || mTvArea.getText().equals("District")) {
            Toast.makeText(this, "Please choose bank and district correctly", Toast.LENGTH_SHORT).show();
        } else if (NetworkConnection.isInternetConnected(this)) {
            mImgWifi.setVisibility(View.GONE);
            mTvMessage.setVisibility(View.INVISIBLE);
            mAtms = new ArrayList<>();
            loadData();
            new MyAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            mAdapter = new ATMListAdapter(mAtms, this);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setMyOnClickFavoriteListener(this);
        } else {
            mImgWifi.setVisibility(View.VISIBLE);
            mImgWifi.startAnimation(mAnimation);
        }
    }

    @Click(R.id.imgWifi)
    void clickImgWifi() {
        mImgWifi.startAnimation(mAnimation);
    }

    @Override
    public void onClick(int position) {
        MyLocation myLocation = new MyLocation(Double.parseDouble(mAdapter.getResultFilter().get(position).getLat()),
                Double.parseDouble(mAdapter.getResultFilter().get(position).getLng()));
        MapsActivity_.intent(this)
                .mAtm(mAdapter.getResultFilter().get(position))
                .mAddressAtm(myLocation)
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
        ATMServiceImpl atmServiceImpl = new ATMServiceImpl(this);
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
            Toast.makeText(this, "You're favorited this item", Toast.LENGTH_SHORT).show();
        } else {
            mMyDatabase.deleteATM(Integer.parseInt(myATM.getMaDiaDiem()));
            Toast.makeText(this, "You're unfavorited this item", Toast.LENGTH_SHORT).show();
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
                    Thread.sleep(1000);
                    mCheck = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (count >= 5) {
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
