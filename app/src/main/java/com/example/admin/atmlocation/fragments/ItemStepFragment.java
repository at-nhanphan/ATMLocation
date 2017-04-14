package com.example.admin.atmlocation.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.atmlocation.R;
import com.example.admin.atmlocation.models.Step;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * ItemStepFragment class
 * Created by naunem on 13/04/2017.
 */
@EFragment(R.layout.item_steps)
public class ItemStepFragment extends Fragment {
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
        Step step = getArguments().getParcelable("step");
        mTvName.setText(Html.fromHtml(step.getHtmlInstructions()));
        mTvDistance.setText(step.getDistance().getText());

        Log.d("aaaa", "init: " + step.getManeuver());
        if (null != step.getManeuver()) {
            switch (step.getManeuver()) {
                case "turn-left":
                    mImgLogo.setImageResource(R.drawable.ic_turn_left);
                    break;
                case "turn-right":
                    mImgLogo.setImageResource(R.drawable.ic_turn_right);
                    break;
                default:
                    mImgLogo.setImageResource(R.drawable.ic_turning_point_right);
                    break;
            }
        } else {
            mImgLogo.setImageResource(R.drawable.ic_ahead_point);
        }
    }

    public ItemStepFragment_ newInstance(Step step) {
        ItemStepFragment_ fragment = new ItemStepFragment_();
        Bundle bundle = new Bundle();
        bundle.putParcelable("step", step);
        fragment.setArguments(bundle);
        return fragment;
    }
}
