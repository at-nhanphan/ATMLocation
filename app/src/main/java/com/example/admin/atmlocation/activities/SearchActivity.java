package com.example.admin.atmlocation.activities;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.admin.atmlocation.R;
import com.example.admin.atmlocation.adapters.ATMListAdapter;
import com.example.admin.atmlocation.interfaces.CallBack;
import com.example.admin.atmlocation.interfaces.MyOnClickListener;
import com.example.admin.atmlocation.models.MyATM;
import com.example.admin.atmlocation.models.googleDirections.MyLocation;
import com.example.admin.atmlocation.services.ATMServiceImpl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * SearchActivity class
 * Created by naunem on 05/04/2017.
 */
@EActivity(R.layout.activity_search)
public class SearchActivity extends AppCompatActivity implements MyOnClickListener {
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
    @Extra
    int mCode;
    @Extra
    String mResultBank;
    @Extra
    String mResultDistrict;
    @Extra
    int mPosition;
    @Extra
    int mPositionBank;
    @Extra
    int mPositionDistrict;
    private static final int REQUEST_CODE_BANK = 1;
    private static final int REQUEST_CODE_AREA = 2;
    private ATMListAdapter mAdapter;
    private ArrayList<MyATM> mAtms = new ArrayList<>();

    @AfterViews
    void init() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTvMessage.setVisibility(View.GONE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        if (null != mResultBank) {
            mTvBank.setText(mResultBank);
        }
        if (null != mResultDistrict) {
            mTvArea.setText(mResultDistrict);
        }
    }

    @Click({R.id.tvBank, R.id.tvArea, R.id.imgBack})
    void clickChoose(View v) {
        switch (v.getId()) {
            case R.id.tvBank:
                ListBankDistrictActivity_.intent(this)
                        .mCode(REQUEST_CODE_BANK)
                        .mPositionBank(mPositionBank)
                        .mResultDistrict(mResultDistrict)
                        .mPositionDistrict(mPositionDistrict)
                        .start();
                break;
            case R.id.tvArea:
                ListBankDistrictActivity_.intent(this)
                        .mCode(REQUEST_CODE_AREA)
                        .mPositionDistrict(mPositionDistrict)
                        .mPositionBank(mPositionBank)
                        .mResultBank(mResultBank)
                        .start();
                break;
            case R.id.imgBack:
                finish();
        }
    }

    @Click(R.id.btnSearch)
    void clickSearch() {
        loadData();
        if (mAtms != null) {
            mAdapter = new ATMListAdapter(this, mAtms, this);
            mRecyclerView.setAdapter(mAdapter);
            if (mAtms.size() <= 0) {
                mTvMessage.setVisibility(View.VISIBLE);
                mTvMessage.setText(R.string.message);
            }
        } else {
            Log.d("aaaa", "clickSearch: array is empty");
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


    public void loadData() {
        ATMServiceImpl atmServiceImpl = new ATMServiceImpl(this);
        atmServiceImpl.getAtmSearch(String.valueOf(mTvBank.getText()), String.valueOf(mTvArea.getText()), new CallBack<ArrayList<MyATM>>() {
            @Override
            public void next(ArrayList<MyATM> myATMs) {
                if (myATMs != null) {
                    mAtms.clear();
                    mAtms.addAll(myATMs);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
