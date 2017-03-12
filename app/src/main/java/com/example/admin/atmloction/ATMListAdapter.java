package com.example.admin.atmloction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 3/3/2017.
 */

public class ATMListAdapter extends RecyclerView.Adapter<ATMListAdapter.MyViewHolder> {
    ArrayList<ATM> mAtms;
    Context mContext;

    public ATMListAdapter(Context context, ArrayList<ATM> atms) {
        this.mAtms = atms;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ATM atm = mAtms.get(position);
        holder.mImgLogo.setImageResource(R.drawable.ic_logo);
        holder.mTvName.setText(atm.getName());
        Log.d("dsfdf", "onBindViewHolder: " + atm.getName());
        holder.mTvAddress.setText(atm.getAddress());
        if (atm.getRating() == null) {
            holder.mRatingBar.setRating(3.0f);
        }else {
            holder.mRatingBar.setRating(Float.parseFloat(atm.getRating()));
        }
    }

    @Override
    public int getItemCount() {
        return mAtms.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView mImgLogo;
        TextView mTvName;
        TextView mTvAddress;
        RatingBar mRatingBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImgLogo = (ImageView) itemView.findViewById(R.id.imgLogo);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            mRatingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, FavoriteActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("object", mAtms.get(getLayoutPosition()));
                    intent.putExtra("data", bundle);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
