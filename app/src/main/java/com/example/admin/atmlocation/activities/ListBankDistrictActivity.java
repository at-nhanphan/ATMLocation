package com.example.admin.atmlocation.activities;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.example.admin.atmlocation.R;
import com.example.admin.atmlocation.adapters.ListBankAdapter;
import com.example.admin.atmlocation.interfaces.MyOnClickListener;
import com.example.admin.atmlocation.models.ItemListBank;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * ListBankDistrictActivity class
 * Created by naunem on 20/04/2017.
 */

@EActivity(R.layout.activity_list)
public class ListBankDistrictActivity extends AppCompatActivity implements MyOnClickListener {
    @ViewById(R.id.toolbar)
    Toolbar mToolbar;
    @ViewById(R.id.tvTitle)
    TextView mTvTitle;
    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Extra
    int mCode;
    @Extra
    String mResultBank;
    @Extra
    String mResultDistrict;
    @Extra
    int mPositionBank;
    @Extra
    int mPositionDistrict;
    private ArrayList<ItemListBank> mBanks;
    private ListBankAdapter mAdapter;

    @AfterViews
    void init() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mBanks = new ArrayList<>();
        String[] arrays;

        if (mCode == 1) {
            mTvTitle.setText(R.string.toolbar_title_chooseBank);
            arrays = getResources().getStringArray(R.array.list_banks);
            for (String array : arrays) {
                mBanks.add(new ItemListBank(array));
            }
            if (mPositionBank != 0) {
                mBanks.get(mPositionBank).setCheck(true);
            }
        } else {
            mTvTitle.setText(R.string.toolbar_title_chooseDistrict);
            arrays = getResources().getStringArray(R.array.districts);
            for (String array : arrays) {
                mBanks.add(new ItemListBank(array));
            }
            if (mPositionDistrict != 0) {
                mBanks.get(mPositionDistrict).setCheck(true);
            }
        }
        mAdapter = new ListBankAdapter(mBanks, this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(int position) {
        int positionBank = 0;
        for (int i = 0; i < mBanks.size(); i++) {
            if (mBanks.get(i).getName().equals(mAdapter.getResultFilter().get(position).getName())) {
                positionBank = i;
            }
        }
        if (mCode == 1) {
            SearchActivity_.intent(this)
                    .mResultBank(mAdapter.getResultFilter().get(position).getName())
                    .mResultDistrict(mResultDistrict)
                    .mCode(mCode)
                    .mPositionBank(positionBank)
                    .mPositionDistrict(mPositionDistrict)
                    .start();
        } else {
            SearchActivity_.intent(this)
                    .mResultDistrict(mAdapter.getResultFilter().get(position).getName())
                    .mResultBank(mResultBank)
                    .mCode(mCode)
                    .mPositionDistrict(positionBank)
                    .mPositionBank(mPositionBank)
                    .start();
        }
    }

    @Click(R.id.imgBack)
    void clickBack() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Type your keyword here");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getValueFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}