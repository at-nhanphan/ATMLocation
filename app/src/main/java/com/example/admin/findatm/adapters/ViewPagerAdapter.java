package com.example.admin.findatm.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.example.admin.findatm.fragments.AboutFragment_;
import com.example.admin.findatm.fragments.FavoriteFragment_;
import com.example.admin.findatm.fragments.HomeFragment_;

/**
 * ViewPagerAdapter class
 * Created by naunem on 24/03/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeFragment_.builder().build();
            case 1:
                return FavoriteFragment_.builder().build();
            case 2:
                return AboutFragment_.builder().build();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
