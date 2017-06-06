package com.example.admin.findatm.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.admin.findatm.R;
import com.example.admin.findatm.fragments.AboutFragment_;
import com.example.admin.findatm.fragments.FavoriteFragment_;
import com.example.admin.findatm.fragments.HomeFragment;
import com.example.admin.findatm.fragments.HomeFragment_;
import com.example.admin.findatm.fragments.MapsFragment_;
import com.example.admin.findatm.fragments.SearchFragment_;
import com.example.admin.findatm.interfaces.OnQueryTextChange;
import com.example.admin.findatm.models.MyATM;
import com.example.admin.findatm.models.ObjectData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity class
 * Created by naunem on 24/03/2017.
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @ViewById(R.id.imgSearch)
    ImageView mImgSearch;
    @ViewById(R.id.imgSwipe)
    ImageView mImgSwipe;
    @ViewById(R.id.rlSearch)
    RelativeLayout mRlSearch;
    @ViewById(R.id.edtSearch)
    EditText mEdtSearch;

    private OnQueryTextChange mOnQueryTextChange;
    private FragmentManager mManager;
    private boolean mIsCheck;
    private boolean mIsClick;

    @AfterViews
    void init() {
        mRlSearch.setVisibility(View.GONE);
        mManager = getSupportFragmentManager();
        mManager.beginTransaction().replace(R.id.flContainer, HomeFragment_.builder().build()).commit();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        addSearchListener();
    }

    @Click(R.id.imgSearch)
    void clickSearch() {
        mEdtSearch.setText("");
        if (!mIsClick) {
            mRlSearch.setVisibility(View.VISIBLE);
            mIsClick = true;
        } else {
            mRlSearch.setVisibility(View.GONE);
            mIsClick = false;
        }
    }

    @Click(R.id.imgSwipe)
    void clickSwipe() {
        Fragment fragment;
        ArrayList<MyATM> lists = new ArrayList<>();
        Fragment frag = mManager.findFragmentById(R.id.flContainer);
        if (frag != null && frag instanceof HomeFragment) {
            lists = ((HomeFragment) frag).getListATMs();
        }
        if (!mIsCheck) {
            fragment = MapsFragment_.builder().mMyATMs(lists).build();
            mImgSwipe.setBackgroundResource(R.drawable.ic_view_list_white_24dp);
            mImgSearch.setVisibility(View.GONE);
            mRlSearch.setVisibility(View.GONE);
            mIsCheck = true;
        } else {
            fragment = HomeFragment_.builder().build();
            mImgSwipe.setBackgroundResource(R.drawable.ic_google_maps);
            mImgSearch.setVisibility(View.VISIBLE);
            mIsCheck = false;
        }
        mManager.beginTransaction().replace(R.id.flContainer, fragment).commit();

    }

    public void setOnQueryTextChange(OnQueryTextChange onQueryTextChange) {
        this.mOnQueryTextChange = onQueryTextChange;
    }

    private void addSearchListener() {
        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mOnQueryTextChange.onTextChange(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Click(R.id.imgDelete)
    void clickDelete() {
        if (mEdtSearch.getText().toString().isEmpty()) {
            mRlSearch.setVisibility(View.GONE);
            mIsClick = false;
        } else {
            mEdtSearch.setText("");
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.home:
                if (mIsCheck) {
                    fragment = MapsFragment_.builder().build();
                    mImgSearch.setVisibility(View.GONE);
                } else {
                    fragment = HomeFragment_.builder().build();
                    mImgSearch.setVisibility(View.VISIBLE);
                }
                mImgSwipe.setVisibility(View.VISIBLE);
                break;
            case R.id.favorite:
                fragment = FavoriteFragment_.builder().build();
                mImgSwipe.setVisibility(View.GONE);
                mImgSearch.setVisibility(View.VISIBLE);
                mEdtSearch.setText("");
                break;
            case R.id.search:
                fragment = SearchFragment_.builder().build();
                mImgSwipe.setVisibility(View.GONE);
                mImgSearch.setVisibility(View.GONE);
                mRlSearch.setVisibility(View.GONE);
                mIsClick = false;
                break;
            case R.id.about:
                fragment = AboutFragment_.builder().build();
                mImgSwipe.setVisibility(View.GONE);
                mImgSearch.setVisibility(View.GONE);
                mRlSearch.setVisibility(View.GONE);
                mIsClick = false;
                break;
        }
        mManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
        return true;
    }
}
