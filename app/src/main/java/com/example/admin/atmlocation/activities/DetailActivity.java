package com.example.admin.atmlocation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.admin.atmlocation.R;
import com.example.admin.atmlocation.models.ATM;
import com.example.admin.atmlocation.models.Locations;

/**
 *
 * Created by naunem on 25/03/2017.
 */

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvName;
    private TextView mTvAddress;
    private RatingBar mRatingBar;
    private ImageView mImgFavorite;
    private Button mBtnShowMap;
    private ATM mAtm;
    private Locations mLocations;

    private void init() {
        mTvName = (TextView) findViewById(R.id.tvName);
        mTvAddress = (TextView) findViewById(R.id.tvAddress);
        mRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        mImgFavorite = (ImageView) findViewById(R.id.imgFavorite);
        mBtnShowMap = (Button) findViewById(R.id.btnShowMap);
        mBtnShowMap.setOnClickListener(this);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
        mAtm = getIntent().getBundleExtra("data").getParcelable("object");
        mLocations = getIntent().getBundleExtra("data").getParcelable("location");
        Log.d("llllll", "onCreate: " + mLocations.getLat());
        mTvName.setText(mAtm.getName());
        mTvAddress.setText(mAtm.getAddress());
        mRatingBar.setRating(Float.parseFloat(mAtm.getRating()));
        mImgFavorite.setSelected(mAtm.isFavorite());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(DetailActivity.this, MapsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("object", mAtm);
        bundle.putParcelable("location", mLocations);
        intent.putExtra("data", bundle);
        startActivity(intent);
        finish();
    }
}
