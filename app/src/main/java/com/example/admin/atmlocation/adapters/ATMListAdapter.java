package com.example.admin.atmlocation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.atmlocation.R;
import com.example.admin.atmlocation.databases.MyDatabase;
import com.example.admin.atmlocation.interfaces.MyOnClickFavoriteListener;
import com.example.admin.atmlocation.interfaces.MyOnClickListener;
import com.example.admin.atmlocation.models.MyATM;

import java.util.ArrayList;

/**
 * ATMListAdapter class
 * Created by Admin on 3/3/2017.
 */

public class ATMListAdapter extends RecyclerView.Adapter<ATMListAdapter.MyViewHolder> implements Filterable {
    private ArrayList<MyATM> mAtms;
    private final Context mContext;
    private MyOnClickListener mMyOnClickListener;
    private ValueFilter mValueFilter;
    private ArrayList<MyATM> mAtmsFilter;
    private MyDatabase mMyDatabase;
    private MyOnClickFavoriteListener mMyOnClickFavoriteListener;

    public ATMListAdapter(Context context, ArrayList<MyATM> atms, MyOnClickListener myOnClickListener) {
        this.mAtms = atms;
        this.mContext = context;
        this.mMyOnClickListener = myOnClickListener;
        this.mAtmsFilter = atms;
        mMyDatabase = new MyDatabase(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyATM atm = mAtms.get(position);
        holder.mImgLogo.setImageResource(R.mipmap.ic_logo_atm);
        holder.mTvName.setText(atm.getTenDiaDiem());
        holder.mTvAddress.setText(atm.getDiaChi());
        holder.mImgFavorite.setSelected(atm.isFavorite());
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
//        private RatingBar mRatingBar;

        MyViewHolder(View itemView) {
            super(itemView);
            mImgLogo = (ImageView) itemView.findViewById(R.id.imgLogo);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            mImgFavorite = (ImageView) itemView.findViewById(R.id.imgFavorite);
//            mRatingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMyOnClickListener.onClick(getLayoutPosition());
                }
            });
            mImgFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyATM myATM = mAtms.get(getLayoutPosition());
                    mImgFavorite.setSelected(!myATM.isFavorite());
                    mAtms.get(getLayoutPosition()).setFavorite(!myATM.isFavorite());

//                    if (myATM.isFavorite()) {
//                        ArrayList<MyATM> lists = mMyDatabase.getAll();
//                        int count = 0;
//                        if (lists.size() > 0) {
//                            for (int i = 0; i < lists.size(); i++) {
//                                if (myATM.getMaDiaDiem().equals(lists.get(i).getMaDiaDiem())) {
//                                    Toast.makeText(mContext, "Item is favorited", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    count++;
//                                }
//                            }
//                        }
//                        if (count == lists.size()) {
//                            mMyDatabase.insertATM(myATM);
//                        }
//                    }

                    mMyOnClickFavoriteListener.onClickFavorite(getLayoutPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }

    public void setMyOnClickFavoriteListener(MyOnClickFavoriteListener mMyOnClickFavoriteListener) {
        this.mMyOnClickFavoriteListener = mMyOnClickFavoriteListener;
    }

    @Override
    public Filter getFilter() {
        if (mValueFilter == null) {
            mValueFilter = new ValueFilter();
        }
        return mValueFilter;
    }

    private class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<MyATM> filterList = new ArrayList();
                for (int i = 0; i < mAtmsFilter.size(); i++) {
                    if ((mAtmsFilter.get(i).getTenDiaDiem().toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(mAtmsFilter.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mAtmsFilter.size();
                results.values = mAtmsFilter;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mAtms = (ArrayList<MyATM>) results.values;
            notifyDataSetChanged();
        }
    }

    public ArrayList<MyATM> getResultFilter() {
        return mAtms;
    }
}
