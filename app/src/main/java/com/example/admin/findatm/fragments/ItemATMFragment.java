package com.example.admin.findatm.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.findatm.R;
import com.example.admin.findatm.databases.MyDatabase;
import com.example.admin.findatm.models.MyATM;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;

/**
 * ItemATMFragment class
 * Created by naunem on 11/05/2017.
 */

@EFragment(R.layout.item_list)
public class ItemATMFragment extends Fragment implements View.OnClickListener {
    @ViewById(R.id.tvName)
    TextView mTvName;
    @ViewById(R.id.tvAddress)
    TextView mTvAddress;
    @ViewById(R.id.imgFavorite)
    ImageView mImgFavorite;
    @ViewById(R.id.cardView)
    CardView mCardView;
    @ColorRes(R.color.colorWhite)
    int mColorItem;
    private MyDatabase mMyDatabase;
    private MyATM mMyATM;


    @AfterViews
    void init() {
        mMyDatabase = new MyDatabase(getContext());
        mCardView.setCardBackgroundColor(mColorItem);
        mMyATM = getArguments().getParcelable("atm");
        if (mMyATM != null) {
            mTvName.setText(mMyATM.getTenDiaDiem());
            mTvAddress.setText(mMyATM.getDiaChi());
            mImgFavorite.setSelected(mMyATM.isFavorite());
        }
        mImgFavorite.setOnClickListener(this);
    }

    public ItemATMFragment_ newInstance(MyATM myATM) {
        ItemATMFragment_ fragment = new ItemATMFragment_();
        Bundle bundle = new Bundle();
        bundle.putParcelable("atm", myATM);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        mMyATM.setFavorite(!mMyATM.isFavorite());
        mImgFavorite.setSelected(mMyATM.isFavorite());
        if (mMyATM.isFavorite()) {
            mMyDatabase.insertATM(mMyATM);
            Toast.makeText(getContext(), R.string.favorite_item, Toast.LENGTH_SHORT).show();
        } else {
            mMyDatabase.deleteATM(Integer.parseInt(mMyATM.getMaDiaDiem()));
            Toast.makeText(getContext(), R.string.unfavorite_item, Toast.LENGTH_SHORT).show();
        }
    }
}
