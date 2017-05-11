package com.example.admin.findatm.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.findatm.R;
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
public class ItemATMFragment extends Fragment {
    @ViewById(R.id.tvName)
    TextView mTvName;
    @ViewById(R.id.tvAddress)
    TextView mTvAddress;
    @ViewById(R.id.imgFavorite)
    ImageView mImgFavorite;
    @ViewById(R.id.cardView)
    CardView mCardView;
    @ColorRes(R.color.colorItem)
    int mColorItem;


    @AfterViews
    void init() {
        mCardView.setCardBackgroundColor(mColorItem);
        MyATM myATM = getArguments().getParcelable("atm");
        if (myATM != null) {
            mTvName.setText(myATM.getTenDiaDiem());
            mTvAddress.setText(myATM.getDiaChi());
            mImgFavorite.setSelected(myATM.isFavorite());
        }
    }

    public ItemATMFragment_ newInstance(MyATM myATM) {
        ItemATMFragment_ fragment = new ItemATMFragment_();
        Bundle bundle = new Bundle();
        bundle.putParcelable("atm", myATM);
        fragment.setArguments(bundle);
        return fragment;
    }
}
