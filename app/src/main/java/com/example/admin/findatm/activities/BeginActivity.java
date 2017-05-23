package com.example.admin.findatm.activities;

import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.example.admin.findatm.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * BeginActivity class
 * Created by naunem on 23/05/2017.
 */

@EActivity(R.layout.activity_begin)
public class BeginActivity extends AppCompatActivity {

    @ViewById(R.id.progressBar)
    ProgressBar mProgressBar;

    @AfterViews
    void init() {
        mProgressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity_.intent(BeginActivity.this).start();
                finish();
            }
        }, 3000);
    }
}
