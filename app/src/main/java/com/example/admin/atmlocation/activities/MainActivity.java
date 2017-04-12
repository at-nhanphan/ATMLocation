package com.example.admin.atmlocation.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import java.util.ArrayList;

/**
 * Main class
 * Created by naunem on 24/03/2017.
 */

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private ViewPager mViewPager;
    private OnQueryTextChange mOnQueryTextChange;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Declare component
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        toolbar.setTitle("Home");
//        Spinner spinner = (Spinner) findViewById(R.id.spinner);

//        String[] areas = new String[]{"hjhj", "jhjhjh", "hjhj", "jhjhjh", "hjhj", "jhjhjh"};
//        ArrayAdapter<String> mang = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, areas);
//        spinner.setAdapter(mang);
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        final TabLayout.Tab home = mTabLayout.newTab();
        final TabLayout.Tab favorite = mTabLayout.newTab();
        final TabLayout.Tab setting = mTabLayout.newTab();
        home.setIcon(R.drawable.ic_home);
        favorite.setIcon(R.drawable.ic_favorite_brown_200_36dp);
        setting.setIcon(R.drawable.ic_settings_brown_200_36dp);

        mTabLayout.addTab(home, 0);
        mTabLayout.addTab(favorite, 1);
        mTabLayout.addTab(setting, 2);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

        mTabLayout.addOnTabSelectedListener(this);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        home.setIcon(R.drawable.ic_home);
                        favorite.setIcon(R.drawable.ic_favorite_brown_200_36dp);
                        setting.setIcon(R.drawable.ic_settings_brown_200_36dp);
                        break;
                    case 1:
                        home.setIcon(R.drawable.ic_home_brown_200_36dp);
                        favorite.setIcon(R.drawable.ic_favorite);
                        setting.setIcon(R.drawable.ic_settings_brown_200_36dp);
                        break;
                    case 2:
                        home.setIcon(R.drawable.ic_home_brown_200_36dp);
                        favorite.setIcon(R.drawable.ic_favorite_brown_200_36dp);
                        setting.setIcon(R.drawable.ic_settings);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
                        .setSingleChoiceItems(arrayAdapterItems, -1,  new DialogInterface.OnClickListener() {
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
