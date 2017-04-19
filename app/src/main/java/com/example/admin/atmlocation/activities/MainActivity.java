package com.example.admin.atmlocation.activities;

import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.example.admin.atmlocation.R;
import com.example.admin.atmlocation.adapters.ViewPagerAdapter;
import com.example.admin.atmlocation.interfaces.OnQueryTextChange;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.PageSelected;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * MainActivity class
 * Created by naunem on 24/03/2017.
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    @ViewById(R.id.toolbar)
    Toolbar mToolbar;
    @ViewById(R.id.viewPager)
    ViewPager mViewPager;
    @ViewById(R.id.tabLayout)
    TabLayout mTabLayout;
    private OnQueryTextChange mOnQueryTextChange;
    private TabLayout.Tab mHome;
    private TabLayout.Tab mFavorite;
    private TabLayout.Tab mSetting;


    @AfterViews
    void init() {
        setSupportActionBar(mToolbar);
        mHome = mTabLayout.newTab();
        mFavorite = mTabLayout.newTab();
        mSetting = mTabLayout.newTab();
        mHome.setIcon(R.drawable.ic_home_36dp);
        mFavorite.setIcon(R.drawable.ic_favorite_brown_200_36dp);
        mSetting.setIcon(R.drawable.ic_settings_brown_200_36dp);

        mTabLayout.addTab(mHome, 0);
        mTabLayout.addTab(mFavorite, 1);
        mTabLayout.addTab(mSetting, 2);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

        mTabLayout.addOnTabSelectedListener(this);
        mViewPager.setOffscreenPageLimit(3);
    }

    @PageSelected(R.id.viewPager)
    void onPageFragmentSelected(int position) {
        switch (position) {
            case 0:
                mHome.setIcon(R.drawable.ic_home_36dp);
                mFavorite.setIcon(R.drawable.ic_favorite_brown_200_36dp);
                mSetting.setIcon(R.drawable.ic_settings_brown_200_36dp);
                mToolbar.setTitle("Home");
                break;
            case 1:
                mHome.setIcon(R.drawable.ic_home_brown_200_36dp);
                mFavorite.setIcon(R.drawable.ic_favorite_36dp);
                mSetting.setIcon(R.drawable.ic_settings_brown_200_36dp);
                mToolbar.setTitle("Favorite");
                break;
            case 2:
                mHome.setIcon(R.drawable.ic_home_brown_200_36dp);
                mFavorite.setIcon(R.drawable.ic_favorite_brown_200_36dp);
                mSetting.setIcon(R.drawable.ic_settings_36dp);
                mToolbar.setTitle("Setting");
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
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Type your keyword here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mOnQueryTextChange.onTextChange(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
//                Intent intent  = new Intent(this, SearchActivity.class);
//                startActivity(intent);
                break;
            case R.id.home:
                new AlertDialog.Builder(this)
                        .setTitle("Title")
                        .setMessage("Message")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                break;
            case R.id.favorite:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                ArrayList<String> items = new ArrayList<>();
                items.add("Red");
                items.add("Blue");
                items.add("Green");

                final ArrayAdapter<String> arrayAdapterItems = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, items);

                dialog.setTitle("aaaa")
                        .setSingleChoiceItems(arrayAdapterItems, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                dialog.create().show();
                break;
            case R.id.setting:
                break;
        }
        return false;
    }

    public void setOnQueryTextChange(OnQueryTextChange onQueryTextChange) {
        this.mOnQueryTextChange = onQueryTextChange;
    }
}
