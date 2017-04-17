package com.example.admin.atmlocation.activities;

import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.admin.atmlocation.R;
import com.example.admin.atmlocation.models.ATM;
import com.example.admin.atmlocation.models.MyLocation;

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

    @ViewById(R.id.tvName)
    TextView mTvName;
    @ViewById(R.id.tvAddress)
    TextView mTvAddress;
    @ViewById(R.id.ratingBar)
    RatingBar mRatingBar;
    @ViewById(R.id.imgFavorite)
    ImageView mImgFavorite;
    @Extra
    ATM mAtm;
    @Extra
    MyLocation mMyLocation;

    @AfterViews
    void init() {
        mTvName.setText(mAtm.getName());
        mTvAddress.setText(mAtm.getAddress());
        mRatingBar.setRating(Float.parseFloat(mAtm.getRating()));
        mImgFavorite.setSelected(mAtm.isFavorite());
    }

    @Click(R.id.btnShowMap)
    public void onClickShowMap() {
        MapsActivity_.intent(this).mAtm(mAtm).mAtmMyLocation(mMyLocation).start();
    }
}
