package com.example.admin.findatm.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.admin.findatm.R;
import com.example.admin.findatm.fragments.SearchFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * SearchActivity class
 * Created by naunem on 05/04/2017.
 */
@EActivity(R.layout.activity_search)
public class SearchActivity extends AppCompatActivity {

    @ViewById(R.id.toolbar)
    Toolbar mToolbar;

    @AfterViews
    void init() {
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flContainer, SearchFragment_.builder().build()).commit();
    }
}
