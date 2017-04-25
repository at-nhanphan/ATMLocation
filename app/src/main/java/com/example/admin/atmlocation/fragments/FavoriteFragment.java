package com.example.admin.atmlocation.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.admin.atmlocation.R;
import com.example.admin.atmlocation.adapters.ATMListAdapter;
import com.example.admin.atmlocation.databases.MyDatabase;
import com.example.admin.atmlocation.interfaces.MyOnClickListener;
import com.example.admin.atmlocation.models.MyATM;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 *  FavoriteFragment class
 * Created by naunem on 24/03/2017.
 */

@EFragment(R.layout.fragment_favorite)
public class FavoriteFragment extends Fragment implements MyOnClickListener {
    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private MyDatabase mMyDatabase;
    private ArrayList<MyATM> mMyATMs;
    private ATMListAdapter mAdapter;

    @AfterViews
    void init() {
        mMyDatabase = new MyDatabase(getActivity());
        mMyATMs = mMyDatabase.getAll();
        Log.d("aaaa", "init: " + mMyDatabase.getAll().size());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        if(mMyATMs.size() > 0) {
            mAdapter = new ATMListAdapter(getActivity(), mMyATMs, this);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onClick(int position) {

    }
}
