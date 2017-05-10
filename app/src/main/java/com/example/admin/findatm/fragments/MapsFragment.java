package com.example.admin.findatm.fragments;

import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.admin.findatm.R;

import org.androidannotations.annotations.EFragment;

/**
 * class MapsFragment
 * Created by naunem on 10/05/2017.
 */

@EFragment(R.layout.fragment_maps)
public class MapsFragment extends Fragment {

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.swipe).setIcon(R.drawable.ic_favorite_border_black_36dp);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
