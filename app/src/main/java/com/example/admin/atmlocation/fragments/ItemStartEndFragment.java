package com.example.admin.atmlocation.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.atmlocation.R;
import com.example.admin.atmlocation.models.Leg;

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
        if (position == 1 || position == (leg.getSteps().size() + 2)) {
            mTvName.setText(leg.getStartAddress());
            mImgLogo.setImageResource(R.drawable.ic_pin_on);
        } else {
            mTvName.setText(leg.getEndAddress());
            mImgLogo.setImageResource(R.drawable.ic_pin_off);
        }
    }

    public ItemStartEndFragment_ newInstance(Leg leg, int position) {
        ItemStartEndFragment_ fragment = new ItemStartEndFragment_();
        Bundle bundle = new Bundle();
        bundle.putParcelable("leg", leg);
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }
}
