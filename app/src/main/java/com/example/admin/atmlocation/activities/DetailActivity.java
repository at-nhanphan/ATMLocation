package com.example.admin.atmlocation.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.admin.atmlocation.R;
import com.example.admin.atmlocation.databases.MyDatabase;
import com.example.admin.atmlocation.models.MyATM;
import com.example.admin.atmlocation.models.googleDirections.MyLocation;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * DetailActivity class
 * Created by naunem on 25/03/2017.
 */

@EActivity(R.layout.activity_detail)
public class DetailActivity extends AppCompatActivity {
    @ViewById(R.id.tvId)
    TextView mTvId;
    @ViewById(R.id.tvName)
    TextView mTvName;
    @ViewById(R.id.tvAddress)
    TextView mTvAddress;
    @ViewById(R.id.ratingBar)
    RatingBar mRatingBar;
    @ViewById(R.id.tvDistrict)
    TextView mTvDistrict;
    @ViewById(R.id.tvBank)
    TextView mTvBank;
    @ViewById(R.id.tvLatLng)
    TextView mTvLatLng;
    @ViewById(R.id.imgFavorite)
    ImageView mImgFavorite;
    @Extra
    MyATM mAtm;
    @Extra
    MyLocation mMyLocation;
    private MyDatabase mMyDatabase;

    @AfterViews
    void init() {
        mMyDatabase = new MyDatabase(this);
        mTvId.setText(getString(R.string.textView_text_id_address) + " " + mAtm.getMaDiaDiem());
        mTvName.setText(mAtm.getTenDiaDiem());
        mTvAddress.setText(getString(R.string.textView_text_address) + " " + mAtm.getDiaChi());
        mTvDistrict.setText(getString(R.string.textView_text_id_district) + " " + mAtm.getMaQuan());
        mTvBank.setText(getString(R.string.textView_text_id_bank) + " " + mAtm.getMaNganHang());
        mTvLatLng.setText(getString(R.string.textView_text_location) + " " + mAtm.getLat() + " , " + mAtm.getLng());
//        mRatingBar.setRating(Float.parseFloat(mAtm.getRating()));
        mImgFavorite.setSelected(mAtm.isFavorite());
    }

    @Click(R.id.btnShowMap)
    public void onClickShowMap() {
        MapsActivity_.intent(this).mAtm(mAtm).mAtmMyLocation(mMyLocation).start();
    }

    @Click(R.id.imgBack)
    void clickBack() {
        Intent intent = new Intent();
        intent.putExtra("isFavorite", mAtm.isFavorite());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Click(R.id.imgFavorite)
    void clickFavorite() {
        mImgFavorite.setSelected(!mAtm.isFavorite());
        if (mImgFavorite.isSelected()) {
            mMyDatabase.insertATM(mAtm);
        } else {
            mMyDatabase.deleteATM(Integer.parseInt(mAtm.getMaDiaDiem()));
        }
    }
}
