package com.example.admin.atmlocation.activities;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.admin.atmlocation.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * SearchActivity class
 * Created by naunem on 05/04/2017.
 */
@EActivity(R.layout.activity_search)
public class SearchActivity extends AppCompatActivity {
    @ViewById(R.id.toolbar)
    Toolbar mToolbar;
    @ViewById(R.id.tvBank)
    TextView mTvBank;
    @ViewById(R.id.tvArea)
    TextView mTvArea;
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

    @AfterViews
    void init() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
                ListActivity_.intent(this)
                        .mCode(REQUEST_CODE_BANK)
                        .mPositionBank(mPositionBank)
                        .mResultDistrict(mResultDistrict)
                        .mPositionDistrict(mPositionDistrict)
                        .start();
                break;
            case R.id.tvArea:
                ListActivity_.intent(this)
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
}
