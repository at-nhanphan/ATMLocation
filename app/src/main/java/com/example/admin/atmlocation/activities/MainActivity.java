package com.example.admin.atmlocation.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.example.admin.atmlocation.R;
import com.example.admin.atmlocation.adapters.ViewPagerAdapter;
import com.example.admin.atmlocation.interfaces.OnQueryTextChange;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.PageSelected;
import org.androidannotations.annotations.ViewById;

/**
 * MainActivity class
 * Created by naunem on 24/03/2017.
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    @ViewById(R.id.toolbar)
    Toolbar mToolbar;
    @ViewById(R.id.tabLayout)
    TabLayout mTabLayout;
    @ViewById(R.id.viewPager)
    ViewPager mViewPager;
    @ViewById(R.id.fabSearch)
    FloatingActionButton mFabSearch;
    private SearchView mSearchView;

    private OnQueryTextChange mOnQueryTextChange;
    private OnQueryTextChange mOnQueryTextChangeHome;
    private TabLayout.Tab mHome;
    private TabLayout.Tab mFavorite;
    private TabLayout.Tab mSetting;
    private boolean mIsChange;

    @AfterViews
    void init() {
        setSupportActionBar(mToolbar);
        initTabLayout();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.addOnTabSelectedListener(this);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mViewPager.setCurrentItem(0);
    }

    public void initTabLayout() {
        mHome = mTabLayout.newTab();
        mFavorite = mTabLayout.newTab();
        mSetting = mTabLayout.newTab();
        mHome.setIcon(R.drawable.ic_home_red_a400_36dp);
        mFavorite.setIcon(R.drawable.ic_favorite_brown_200_36dp);
        mSetting.setIcon(R.drawable.ic_info_brown_200_36dp);

        mTabLayout.addTab(mHome, 0);
        mTabLayout.addTab(mFavorite, 1);
        mTabLayout.addTab(mSetting, 2);
    }

    public boolean isIsChange() {
        return mIsChange;
    }

    public void setIsChange(boolean mIsChange) {
        this.mIsChange = mIsChange;
    }

    @PageSelected(R.id.viewPager)
    void onPageFragmentSelected(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                mHome.setIcon(R.drawable.ic_home_red_a400_36dp);
                mFavorite.setIcon(R.drawable.ic_favorite_brown_200_36dp);
                mSetting.setIcon(R.drawable.ic_info_brown_200_36dp);
                mToolbar.setTitle("Home");
                mSearchView.setVisibility(View.VISIBLE);
                mFabSearch.setVisibility(View.VISIBLE);
//                HomeFragment homeFragment = new HomeFragment();
//                Log.d("ddd", "onPageFragmentSelected: " + homeFragment.isCheck());
//
//                fragment = ((ViewPagerAdapter) mViewPager.getAdapter()).getFragment(0);
//                if (fragment != null) {
//                    fragment.onResume();
//                }

                break;
            case 1:
                mHome.setIcon(R.drawable.ic_home_brown_200_36dp);
                mFavorite.setIcon(R.drawable.ic_favorite_red_a400_36dp);
                mSetting.setIcon(R.drawable.ic_info_brown_200_36dp);
                mToolbar.setTitle("Favorite");
                mSearchView.setVisibility(View.VISIBLE);
                mFabSearch.setVisibility(View.VISIBLE);
                fragment = ((ViewPagerAdapter) mViewPager.getAdapter()).getFragment(1);
                if (fragment != null) {
                    fragment.onResume();
                }
                break;
            case 2:
                mHome.setIcon(R.drawable.ic_home_brown_200_36dp);
                mFavorite.setIcon(R.drawable.ic_favorite_brown_200_36dp);
                mSetting.setIcon(R.drawable.ic_info_red_a400_36dp);
                mToolbar.setTitle("Info");
                mSearchView.setVisibility(View.GONE);
                mFabSearch.setVisibility(View.GONE);
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        mSearchView = (SearchView) menu.findItem(R.id.search).getActionView();
        mSearchView.setMaxWidth(Integer.MAX_VALUE);
        mSearchView.setQueryHint("Type your keyword here");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mOnQueryTextChange.onTextChange(newText);
                mOnQueryTextChangeHome.onTextChange(newText);
                return false;
            }
        });

        return true;
    }

    public void setOnQueryTextChange(OnQueryTextChange onQueryTextChange) {
        this.mOnQueryTextChange = onQueryTextChange;
    }

    public void setOnQueryTextChangeHome(OnQueryTextChange onQueryTextChange) {
        this.mOnQueryTextChangeHome = onQueryTextChange;
    }

    @Click(R.id.fabSearch)
    void clickSearch() {
        SearchActivity_.intent(this).start();
    }
}
