package com.example.admin.findatm.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.findatm.R;
import com.example.admin.findatm.interfaces.MyOnClickFavoriteListener;
import com.example.admin.findatm.interfaces.MyOnClickListener;
import com.example.admin.findatm.models.MyATM;

import java.util.ArrayList;
import java.util.List;

/**
 * ATMListAdapter class
 * Created by Admin on 3/3/2017.
 */

public class ATMListAdapter extends RecyclerView.Adapter<ATMListAdapter.MyViewHolder> {
    private List<MyATM> mAtms;
    private final Context mContext;
    private MyOnClickListener mMyOnClickListener;
    private ValueFilter mValueFilter;
    private List<MyATM> mAtmsFilter;
    private MyOnClickFavoriteListener mMyOnClickFavoriteListener;

    public ATMListAdapter(Context context, List<MyATM> atms, MyOnClickListener myOnClickListener) {
        this.mAtms = atms;
        this.mContext = context;
        this.mMyOnClickListener = myOnClickListener;
        this.mAtmsFilter = atms;
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

        MyViewHolder(View itemView) {
            super(itemView);
            mImgLogo = (ImageView) itemView.findViewById(R.id.imgLogo);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            mImgFavorite = (ImageView) itemView.findViewById(R.id.imgFavorite);
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

                    mMyOnClickFavoriteListener.onClickFavorite(getLayoutPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }

    public void setMyOnClickFavoriteListener(MyOnClickFavoriteListener mMyOnClickFavoriteListener) {
        this.mMyOnClickFavoriteListener = mMyOnClickFavoriteListener;
    }

    private class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<MyATM> filterLists = new ArrayList();
                for (int i = 0; i < mAtmsFilter.size(); i++) {
                    if ((mAtmsFilter.get(i).getTenDiaDiem().toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterLists.add(mAtmsFilter.get(i));
                    }
                }
                results.count = filterLists.size();
                results.values = filterLists;
            } else {
                results.count = mAtmsFilter.size();
                results.values = mAtmsFilter;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mAtms = (List<MyATM>) results.values;
            notifyDataSetChanged();
        }
    }

    public List<MyATM> getResultFilter() {
        return mAtms;
    }

    public ValueFilter getValueFilter() {
        if (mValueFilter == null) {
            mValueFilter = new ValueFilter();
        }
        return mValueFilter;
    }
}
