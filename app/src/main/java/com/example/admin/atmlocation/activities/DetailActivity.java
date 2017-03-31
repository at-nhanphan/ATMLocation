package com.example.admin.atmlocation.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.admin.atmlocation.R;
import com.example.admin.atmlocation.models.ATM;

/**
 *
 * Created by naunem on 25/03/2017.
 */

public class DetailActivity extends AppCompatActivity {

    private TextView mTvName;
    private TextView mTvAddress;
    private RatingBar mRatingBar;
    private ImageView mImgFavorite;

    private void init() {
        mTvName = (TextView) findViewById(R.id.tvName);
        mTvAddress = (TextView) findViewById(R.id.tvAddress);
        mRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        mImgFavorite = (ImageView) findViewById(R.id.imgFavorite);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
        ATM mAtm = getIntent().getBundleExtra("data").getParcelable("object");
        assert mAtm != null;
        mTvName.setText(mAtm.getName());
        mTvAddress.setText(mAtm.getAddress());
        mRatingBar.setRating(Float.parseFloat(mAtm.getRating()));
        mImgFavorite.setSelected(mAtm.isFavorite());
    }
}
