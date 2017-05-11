package com.example.admin.findatm.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.findatm.R;
import com.example.admin.findatm.adapters.ListBankAdapter;
import com.example.admin.findatm.interfaces.MyOnClickListener;
import com.example.admin.findatm.models.ItemListBank;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * ListBankDistrictActivity class
 * Created by naunem on 20/04/2017.
 */

@EActivity(R.layout.activity_list)
@OptionsMenu(R.menu.main)
public class ListBankDistrictActivity extends AppCompatActivity implements MyOnClickListener {
    @ViewById(R.id.toolbar)
    Toolbar mToolbar;
    @ViewById(R.id.tvTitle)
    TextView mTvTitle;
    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @ViewById(R.id.llContainer)
    LinearLayout mLinearLayout;
    @ViewById(R.id.edtSearch)
    EditText mEdtSearch;
    @ViewById(R.id.rlSearch)
    RelativeLayout mRlSearch;
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
    private ArrayList<ItemListBank> mLists;
    private ListBankAdapter mAdapter;
    private boolean mClick;

    @AfterViews
    void init() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mRlSearch.setVisibility(View.GONE);
//        mLinearLayout.requestFocus();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mLists = new ArrayList<>();
        String[] arrays;

        if (mCode == 1) {
            mTvTitle.setText(R.string.toolbar_title_chooseBank);
            arrays = getResources().getStringArray(R.array.list_banks);
            Arrays.sort(arrays);
            for (String array : arrays) {
                mLists.add(new ItemListBank(array));
            }
            if (mPositionBank != -1) {
                mLists.get(mPositionBank).setCheck(true);
            }
        } else {
            mTvTitle.setText(R.string.toolbar_title_chooseDistrict);
            arrays = getResources().getStringArray(R.array.districts);
            Arrays.sort(arrays);
            for (String array : arrays) {
                mLists.add(new ItemListBank(array));
            }
            if (mPositionDistrict != -1) {
                mLists.get(mPositionDistrict).setCheck(true);
            }
        }


        mAdapter = new ListBankAdapter(mLists, this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        addSearchListener();
    }

    @Override
    public void onClick(int position) {
        int positionBank = 0;
        Intent intent = new Intent();
        for (int i = 0; i < mLists.size(); i++) {
            if (mLists.get(i).getName().equals(mAdapter.getResultFilter().get(position).getName())) {
                positionBank = i;
            }
        }
        if (mCode == 1) {
            intent.putExtra("positionBank", positionBank);
            intent.putExtra("resultBank", mAdapter.getResultFilter().get(position).getName());
        } else {
            intent.putExtra("positionDistrict", positionBank);
            intent.putExtra("resultDistrict", mAdapter.getResultFilter().get(position).getName());
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    @Click(R.id.imgBack)
    void clickBack() {
        finish();
    }

    public void addSearchListener() {
        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.getValueFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Click(R.id.imgDelete)
    void clickDelete() {
        mEdtSearch.setText("");
    }

    @OptionsItem(R.id.search)
    void onItemSearch() {
        if (!mClick) {
            mRlSearch.setVisibility(View.GONE);
            mClick = true;
        } else {
            mRlSearch.setVisibility(View.VISIBLE);
            mClick = false;
        }
    }
}