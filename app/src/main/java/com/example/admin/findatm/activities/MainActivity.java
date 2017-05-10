package com.example.admin.findatm.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.admin.findatm.R;
import com.example.admin.findatm.fragments.AboutFragment_;
import com.example.admin.findatm.fragments.FavoriteFragment_;
import com.example.admin.findatm.fragments.HomeFragment_;
import com.example.admin.findatm.fragments.MapsFragment_;
import com.example.admin.findatm.fragments.SearchFragment_;
import com.example.admin.findatm.interfaces.OnQueryTextChange;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * MainActivity class
 * Created by naunem on 24/03/2017.
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.toolbar)
    Toolbar mToolbar;
    @ViewById(R.id.fabSearch)
    FloatingActionButton mFabSearch;

    private OnQueryTextChange mOnQueryTextChange;
    private OnQueryTextChange mOnQueryTextChangeHome;
    private static boolean mChange;
    private boolean mCheck = false;
    private FragmentManager mManager;

    @AfterViews
    void init() {
        setSupportActionBar(mToolbar);
        mManager = getSupportFragmentManager();
        mManager.beginTransaction().replace(R.id.flContainer, new HomeFragment_()).commit();
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment = null;
                        switch (item.getItemId()) {
                            case R.id.home:
                                if (mCheck) {
                                    fragment = new MapsFragment_();
                                } else {
                                    fragment = new HomeFragment_();
                                }
                                break;
                            case R.id.favorite:
                                fragment = new FavoriteFragment_();
                                break;
                            case R.id.search:
                                fragment = new SearchFragment_();
                                break;
                            case R.id.about:
                                fragment = new AboutFragment_();
                                break;
                        }
                        mManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                        return true;
                    }
                }
        );
    }

    public static boolean isChange() {
        return mChange;
    }

    public static void setChange(boolean isChange) {
        mChange = isChange;
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
                mOnQueryTextChangeHome.onTextChange(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.swipe:
                if (!mCheck) {
                    fragment = new MapsFragment_();
                    mCheck = true;
                } else {
                    fragment = new HomeFragment_();
                    mCheck = false;
                }
                break;
        }
        mManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
        return false;
    }

    public void setOnQueryTextChange(OnQueryTextChange onQueryTextChange) {
        this.mOnQueryTextChange = onQueryTextChange;
    }

    public void setOnQueryTextChangeHome(OnQueryTextChange onQueryTextChange) {
        this.mOnQueryTextChangeHome = onQueryTextChange;
    }
}
