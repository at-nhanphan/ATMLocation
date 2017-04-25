package com.example.admin.atmlocation.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.admin.atmlocation.fragments.FavoriteFragment_;
import com.example.admin.atmlocation.fragments.HomeFragment_;
import com.example.admin.atmlocation.fragments.SettingFragment_;

/**
 * ViewPagerAdapter class
 * Created by naunem on 24/03/2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment_();
            case 1:
                return new FavoriteFragment_();
            case 2:
                return new SettingFragment_();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
