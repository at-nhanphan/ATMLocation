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
    private static final String LEG_KEY = "leg";
    private static final String MYATM_KEY = "myATM";
    private static final String POSITION_KEY = "position";
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
        int position = getArguments().getInt(POSITION_KEY);
        Leg leg = getArguments().getParcelable(LEG_KEY);
        MyATM myATM = getArguments().getParcelable(MYATM_KEY);
        assert leg != null;
        if (position == 1 || position == (leg.getSteps().size() + 2)) {
            mTvName.setText(leg.getStartAddress());
            mImgLogo.setImageResource(R.drawable.ic_flag_start);
        } else {
            assert myATM != null;
            mTvName.setText(myATM.getAddress());
            mImgLogo.setImageResource(R.drawable.ic_flag_end);
        }
    }

    public ItemStartEndFragment_ newInstance(Leg leg, MyATM myATM, int position) {
        ItemStartEndFragment_ fragment = new ItemStartEndFragment_();
        Bundle bundle = new Bundle();
        bundle.putParcelable(LEG_KEY, leg);
        bundle.putParcelable(MYATM_KEY, myATM);
        bundle.putInt(POSITION_KEY, position);
        fragment.setArguments(bundle);
        return fragment;
    }
}
