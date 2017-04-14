package com.example.admin.atmlocation.activities;

import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.atmlocation.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * This is class used to show item is favorited
 * Created by Admin on 3/11/2017.
 */

@EActivity(R.layout.activity_favorite)
public class FavoriteActivity extends AppCompatActivity {

    @ViewById(R.id.imgLogo)
    ImageView mImgLogo;
    @ViewById(R.id.tvName)
    TextView mTvName;
    @ViewById(R.id.tvAddress)
    TextView mTvAddress;
}
