package com.example.admin.findatm.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.admin.findatm.fragments.ItemATMFragment_;
import com.example.admin.findatm.models.MyATM;

import java.util.List;

/**
 * class ATMListViewPagerAdapter
 * Created by naunem on 11/05/2017.
 */

public class ATMListViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<MyATM> mLists;

    public ATMListViewPagerAdapter(FragmentManager fm, List<MyATM> lists) {
        super(fm);
        this.mLists = lists;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ItemATMFragment_().newInstance(mLists.get(mLists.size() - 1));
        } else if (position >= mLists.size() + 1) {
            return new ItemATMFragment_().newInstance(mLists.get(0));
        } else {
            return new ItemATMFragment_().newInstance(mLists.get(position - 1));
        }
    }

    @Override
    public int getCount() {
        return mLists.size() + 2;
    }
}
