package com.example.admin.atmlocation.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import com.example.admin.atmlocation.R;
import com.example.admin.atmlocation.adapters.SearchAdapter;

import java.util.ArrayList;

/**
 * SearchActivity class
 * Created by naunem on 05/04/2017.
 */

public class SearchActivity extends AppCompatActivity {
    private SearchView mSearchView;
    private ArrayList<String> mMonths;
    private SearchAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mSearchView = (SearchView) findViewById(R.id.searchView);
        mSearchView.setIconifiedByDefault(false);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        setSupportActionBar(toolbar);

        mMonths = new ArrayList<>();
        mMonths.add("January");
        mMonths.add("February");
        mMonths.add("March");
        mMonths.add("April");
        mMonths.add("May");
        mMonths.add("June");
        mMonths.add("July");
        mMonths.add("August");
        mMonths.add("September");
        mMonths.add("October");
        mMonths.add("November");
        mMonths.add("December");

        mAdapter = new SearchAdapter(mMonths);
        recyclerView.setAdapter(mAdapter);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}
