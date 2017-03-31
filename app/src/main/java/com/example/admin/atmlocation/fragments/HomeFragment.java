package com.example.admin.atmlocation.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.atmlocation.R;
import com.example.admin.atmlocation.activities.DetailActivity;
import com.example.admin.atmlocation.adapters.ATMListAdapter;
import com.example.admin.atmlocation.interfaces.CallBack;
import com.example.admin.atmlocation.interfaces.MyOnClickListener;
import com.example.admin.atmlocation.models.ATM;
import com.example.admin.atmlocation.services.ATMServiceImpl;

import java.util.ArrayList;

/**
 *
 * Created by naunem on 24/03/2017.
 */

public class HomeFragment extends Fragment implements MyOnClickListener {

    private ATMListAdapter mAdapter;
    private ArrayList<ATM> mAtms;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager ln = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(ln);
        mAtms = new ArrayList<>();
        mAdapter = new ATMListAdapter(view.getContext(), mAtms, this);
        ATMServiceImpl mAtmService = new ATMServiceImpl(view.getContext());
        mAtmService.getATM("ATM+hoa+khanh+da+nang", new CallBack<ArrayList<ATM>>() {
            @Override
            public void next(ArrayList<ATM> atm) {
                mAtms.addAll(atm);
                mAdapter.notifyDataSetChanged();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("object", mAtms.get(position));
        intent.putExtra("data", bundle);
        startActivity(intent);
    }
}
