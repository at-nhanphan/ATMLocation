package com.example.admin.atmlocation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.admin.atmlocation.R;
import com.example.admin.atmlocation.interfaces.MyOnClickListener;
import com.example.admin.atmlocation.models.ATM;

import java.util.ArrayList;

/**
 *
 * Created by Admin on 3/3/2017.
 */

public class ATMListAdapter extends RecyclerView.Adapter<ATMListAdapter.MyViewHolder> {
    private ArrayList<ATM> mAtms;
    private final Context mContext;
    private MyOnClickListener mMyOnClickListener;

    public ATMListAdapter(Context context, ArrayList<ATM> atms, MyOnClickListener myOnClickListener) {
        this.mAtms = atms;
        this.mContext = context;
        this.mMyOnClickListener = myOnClickListener;
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
        holder.mTvAddress.setText(atm.getAddress());
        if (atm.getRating() == null) {
            holder.mRatingBar.setRating(3.0f);
        } else {
            holder.mRatingBar.setRating(Float.parseFloat(atm.getRating()));
        }
    }

    @Override
    public int getItemCount() {
        return mAtms.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgLogo;
        private TextView mTvName;
        private TextView mTvAddress;
        private ImageView mImgFavorite;
        private RatingBar mRatingBar;

        MyViewHolder(View itemView) {
            super(itemView);
            mImgLogo = (ImageView) itemView.findViewById(R.id.imgLogo);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            mImgFavorite = (ImageView) itemView.findViewById(R.id.imgFavorite);
            mRatingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMyOnClickListener.onClick(getLayoutPosition());
                }
            });
            mImgFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public void setMyOnClickListener(MyOnClickListener myOnClickListener) {
        this.mMyOnClickListener = myOnClickListener;
    }
}
