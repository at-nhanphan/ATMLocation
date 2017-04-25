package com.example.admin.atmlocation.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.example.admin.atmlocation.fragments.FavoriteFragment_;
import com.example.admin.atmlocation.fragments.HomeFragment_;
import com.example.admin.atmlocation.fragments.SettingFragment_;

import java.util.HashMap;
import java.util.Map;

/**
 * ViewPagerAdapter class
 * Created by naunem on 24/03/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Map<Integer, String> mFragmentTag;
    private FragmentManager mFragmentManager;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
        mFragmentTag = new HashMap<>();
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

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object object = super.instantiateItem(container, position);
        if (object instanceof Fragment) {
            Fragment f = (Fragment) object;
            String tag = f.getTag();
            mFragmentTag.put(position, tag);
        }
        return object;
    }

    public Fragment getFragment(int position) {
        String tag = mFragmentTag.get(position);
        if (tag == null) {
            return null;
        }
        return mFragmentManager.findFragmentByTag(tag);
    }
}
