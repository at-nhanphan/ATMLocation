package com.example.admin.findatm.fragments;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.admin.findatm.R;
import com.example.admin.findatm.activities.DetailActivity_;
import com.example.admin.findatm.activities.MainActivity_;
import com.example.admin.findatm.activities.MapsActivity_;
import com.example.admin.findatm.adapters.ATMListAdapter;
import com.example.admin.findatm.databases.MyDatabase;
import com.example.admin.findatm.interfaces.MyOnClickFavoriteListener;
import com.example.admin.findatm.interfaces.MyOnClickListener;
import com.example.admin.findatm.interfaces.OnQueryTextChange;
import com.example.admin.findatm.models.MyATM;
import com.example.admin.findatm.models.googleDirections.MyLocation;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;

/**
 * FavoriteFragment class
 * Created by naunem on 24/03/2017.
 */

@EFragment(R.layout.fragment_favorite)
public class FavoriteFragment extends Fragment implements MyOnClickListener, MyOnClickFavoriteListener, OnQueryTextChange {

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @StringRes(R.string.OkButton)
    String mOkButton;
    @StringRes(R.string.cancelButton)
    String mCancelButton;
    @StringRes(R.string.unfavorite_item)
    String mMessageDelete;

    private MyDatabase mMyDatabase;
    private ATMListAdapter mAdapter;
    private ArrayList<MyATM> mMyATMs;

    @AfterViews
    void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mMyDatabase = new MyDatabase(getActivity());
        mMyATMs = mMyDatabase.getAll();
        if (mMyATMs != null) {
            for (MyATM myATM : mMyATMs) {
                myATM.setFavorite(true);
            }
            mAdapter = new ATMListAdapter(mMyATMs, this);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setMyOnClickFavoriteListener(this);
        }
        ((MainActivity_) getContext()).setOnQueryTextChange(this);
    }

    @Override
    public void onClick(int position) {
        MyLocation myLocation = new MyLocation(Double.parseDouble(mAdapter.getResultFilter().get(position).getLat()),
                Double.parseDouble(mAdapter.getResultFilter().get(position).getLng()));
        MapsActivity_.intent(this)
                .mAddressAtm(myLocation)
                .mAtm(mAdapter.getResultFilter().get(position))
                .start();
    }

    @Override
    public void onLongClick(int position) {
        MyLocation myLocation = new MyLocation(Double.parseDouble(mAdapter.getResultFilter().get(position).getLat()),
                Double.parseDouble(mAdapter.getResultFilter().get(position).getLng()));
        DetailActivity_.intent(this)
                .mAtm(mAdapter.getResultFilter().get(position))
                .mMyLocation(myLocation)
                .start();
    }

    @Override
    public void onClickFavorite(final int position) {
        final MyATM myATM = mAdapter.getResultFilter().get(position);
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setMessage(R.string.delete_message);
        dialog.setCancelable(false);
        dialog.setPositiveButton(mOkButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mMyDatabase.deleteATM(Integer.parseInt(myATM.getAddressId()));
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.collapse);
                mRecyclerView.getLayoutManager().findViewByPosition(position).startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mMyATMs.remove(position);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                Toast.makeText(getContext(), mMessageDelete, Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNegativeButton(mCancelButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mMyATMs.get(position).setFavorite(true);
                mAdapter.notifyDataSetChanged();
            }
        });
        dialog.show();
    }

    @Override
    public void onTextChange(String newText) {
        if (mAdapter != null) {
            mAdapter.getValueFilter().filter(newText);
        }
    }
}
