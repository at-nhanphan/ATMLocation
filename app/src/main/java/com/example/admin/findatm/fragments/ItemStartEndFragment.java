package com.example.admin.findatm.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.findatm.R;
import com.example.admin.findatm.models.MyATM;
import com.example.admin.findatm.models.googleDirections.Leg;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * ItemStartEndFragment class
 * Created by naunem on 14/04/2017.
 */

@EFragment(R.layout.item_steps)
public class ItemStartEndFragment extends Fragment {
    @ViewById(R.id.imgLogo)
    ImageView mImgLogo;
    @ViewById(R.id.tvName)
    TextView mTvName;
    @ViewById(R.id.tvAddress)
    TextView mTvAddress;
    @ViewById(R.id.tvDistance)
    TextView mTvDistance;

    @AfterViews
    void init() {
        int position = getArguments().getInt("position");
        Leg leg = getArguments().getParcelable("leg");
        MyATM myATM = getArguments().getParcelable("myATM");
        if (position == 1 || position == (leg.getSteps().size() + 2)) {
            mTvName.setText(leg.getStartAddress());
            mImgLogo.setImageResource(R.drawable.ic_flag_start);
        } else {
            mTvName.setText(myATM.getDiaChi());
            mImgLogo.setImageResource(R.drawable.ic_flag_end);
        }
    }

    public ItemStartEndFragment_ newInstance(Leg leg, MyATM myATM, int position) {
        ItemStartEndFragment_ fragment = new ItemStartEndFragment_();
        Bundle bundle = new Bundle();
        bundle.putParcelable("leg", leg);
        bundle.putParcelable("myATM", myATM);
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }
}
