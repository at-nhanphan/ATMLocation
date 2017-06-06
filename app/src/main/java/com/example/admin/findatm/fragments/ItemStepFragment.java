package com.example.admin.findatm.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.findatm.R;
import com.example.admin.findatm.models.googleDirections.Step;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * ItemStepFragment class
 * Created by naunem on 13/04/2017.
 */
@EFragment(R.layout.item_steps)
public class ItemStepFragment extends Fragment {

    private static final String STEP_KEY = "step";
    @ViewById(R.id.imgLogo)
    ImageView mImgLogo;
    @ViewById(R.id.tvName)
    TextView mTvName;
    @ViewById(R.id.tvAddress)
    TextView mTvAddress;
    @ViewById(R.id.tvDistance)
    TextView mTvDistance;

    @SuppressWarnings("deprecation")
    @AfterViews
    void init() {
        Step step = getArguments().getParcelable(STEP_KEY);
        assert step != null;
        mTvName.setText(Html.fromHtml(step.getHtmlInstructions()));
        mTvDistance.setText(step.getDistance().getText());

        if (null != step.getManeuver()) {
            if (step.getManeuver().equals("turn-left")) {
                mImgLogo.setImageResource(R.drawable.ic_turn_left);
            } else if (step.getManeuver().equals("turn-right")) {
                mImgLogo.setImageResource(R.drawable.ic_turn_right);
            } else if (Html.fromHtml(step.getHtmlInstructions()).toString().contains("At the roundabout, take the 2nd")) {
                mImgLogo.setImageResource(R.drawable.ic_turning_point_2nd);
            } else if (Html.fromHtml(step.getHtmlInstructions()).toString().contains("At the roundabout, take the 3rd")) {
                mImgLogo.setImageResource(R.drawable.ic_turning_point_3rd);
            } else if (Html.fromHtml(step.getHtmlInstructions()).toString().contains("At the roundabout, take the 4th")) {
                mImgLogo.setImageResource(R.drawable.ic_turning_point_4th);
            } else {
                mImgLogo.setImageResource(R.drawable.ic_turning_point_1st);
            }
        } else {
            mImgLogo.setImageResource(R.drawable.ic_turning_point_2nd);
        }
    }

    public ItemStepFragment_ newInstance(Step step) {
        ItemStepFragment_ fragment = new ItemStepFragment_();
        Bundle bundle = new Bundle();
        bundle.putParcelable(STEP_KEY, step);
        fragment.setArguments(bundle);
        return fragment;
    }
}
