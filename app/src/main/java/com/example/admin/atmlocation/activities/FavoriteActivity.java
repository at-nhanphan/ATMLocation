package com.example.admin.atmlocation.activities;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.atmlocation.R;
import com.example.admin.atmlocation.adapters.ATMListAdapter;
import com.example.admin.atmlocation.databases.MyDatabase;
import com.example.admin.atmlocation.interfaces.MyOnClickListener;
import com.example.admin.atmlocation.models.MyATM;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * This is class used to show item is favorited
 * Created by Admin on 3/11/2017.
 */

@EActivity(R.layout.activity_favorite)
public class FavoriteActivity extends AppCompatActivity implements MyOnClickListener {

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private MyDatabase mMyDatabase;
    private ArrayList<MyATM> mMyATMs = new ArrayList<>();
    private ATMListAdapter mAdapter;

    @AfterViews
    void init() {
        mMyDatabase = new MyDatabase(this);
        mMyATMs.addAll(mMyDatabase.getAll());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        if(mMyATMs.size() > 0) {
            mAdapter = new ATMListAdapter(this, mMyATMs, this);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onClick(int position) {

    }
}
