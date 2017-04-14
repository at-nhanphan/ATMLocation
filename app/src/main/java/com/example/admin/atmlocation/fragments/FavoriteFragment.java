package com.example.admin.atmlocation.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import com.example.admin.atmlocation.R;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 *  FavoriteFragment class
 * Created by naunem on 24/03/2017.
 */

@EFragment(R.layout.fragment_favorite)
public class FavoriteFragment extends Fragment {
    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;
}
