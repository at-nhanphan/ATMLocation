package com.example.admin.atmloction;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Admin on 3/11/2017.
 */

public class FavoriteActivity extends AppCompatActivity {

    ImageView mImgLogo;
    TextView mTvName;
    TextView mTvAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
    }
}
