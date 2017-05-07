package com.example.admin.atmlocation.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.admin.atmlocation.R;
import com.example.admin.atmlocation.activities.DetailActivity_;
import com.example.admin.atmlocation.activities.MainActivity;
import com.example.admin.atmlocation.activities.MainActivity_;
import com.example.admin.atmlocation.adapters.ATMListAdapter;
import com.example.admin.atmlocation.databases.MyDatabase;
import com.example.admin.atmlocation.interfaces.MyOnClickFavoriteListener;
import com.example.admin.atmlocation.interfaces.MyOnClickListener;
import com.example.admin.atmlocation.interfaces.OnQueryTextChange;
import com.example.admin.atmlocation.models.MyATM;
import com.example.admin.atmlocation.models.googleDirections.MyLocation;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * FavoriteFragment class
 * Created by naunem on 24/03/2017.
 */

@EFragment(R.layout.fragment_favorite)
public class FavoriteFragment extends Fragment implements MyOnClickListener, MyOnClickFavoriteListener, OnQueryTextChange {
    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private MyDatabase mMyDatabase;
    private ArrayList<MyATM> mMyATMs;
    private ATMListAdapter mAdapter;

    @AfterViews
    void init() {
        mMyDatabase = new MyDatabase(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        ((MainActivity_) getContext()).setOnQueryTextChange(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadFragment();
    }

    @Override
    public void onClick(int position) {
        MyLocation myLocation = new MyLocation(Double.parseDouble(mAdapter.getResultFilter().get(position).getLat()),
                Double.parseDouble(mAdapter.getResultFilter().get(position).getLng()));
        DetailActivity_.intent(this)
                .mAtm(mAdapter.getResultFilter().get(position))
                .mMyLocation(myLocation)
                .start();
    }

    @Override
    public void onClickFavorite(int position) {
        MyATM myATM = mMyATMs.get(position);
        mMyDatabase.deleteATM(Integer.parseInt(myATM.getMaDiaDiem()));
        reloadFragment();
        MainActivity.setChange(true);
    }

    public void reloadFragment() {
        mMyATMs = mMyDatabase.getAll();
        for (MyATM myAtm : mMyATMs) {
            myAtm.setFavorite(true);
        }
        if (mMyATMs.size() > 0) {
            mAdapter = new ATMListAdapter(getActivity(), mMyATMs, this);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setMyOnClickFavoriteListener(this);
        }
    }

    @Override
    public void onTextChange(String newText) {
        if (mAdapter != null) {
            mAdapter.getFilter().filter(newText);
        }
    }
}
