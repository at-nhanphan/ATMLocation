package com.example.admin.findatm.adapters;

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
    private final MyOnClickListener mMyOnClickListener;
    private ValueFilterATM mValueFilter;
    private final List<MyATM> mAtmsFilter;
    private MyOnClickFavoriteListener mMyOnClickFavoriteListener;

    public ATMListAdapter(List<MyATM> atms, MyOnClickListener myOnClickListener) {
        this.mAtms = atms;
        this.mMyOnClickListener = myOnClickListener;
        this.mAtmsFilter = atms;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyATM atm = mAtms.get(position);
        holder.mTvName.setText(atm.getAddressName());
        holder.mTvAddress.setText(atm.getAddress());
        holder.mImgFavorite.setSelected(atm.isFavorite());
    }

    @Override
    public int getItemCount() {
        return mAtms.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvName;
        private final TextView mTvAddress;
        private final ImageView mImgFavorite;

        MyViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            mImgFavorite = (ImageView) itemView.findViewById(R.id.imgFavorite);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMyOnClickListener.onClick(getLayoutPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mMyOnClickListener.onLongClick(getLayoutPosition());
                    return true;
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

    @SuppressWarnings("WeakerAccess")
    public class ValueFilterATM extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                List<MyATM> filterLists = new ArrayList<>();
                for (int i = 0; i < mAtmsFilter.size(); i++) {
                    if ((mAtmsFilter.get(i).getAddressName().toUpperCase()).contains(constraint.toString().toUpperCase())) {
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

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mAtms = (List<MyATM>) results.values;
            notifyDataSetChanged();
        }
    }

    public List<MyATM> getResultFilter() {
        return mAtms;
    }

    public ValueFilterATM getValueFilter() {
        if (mValueFilter == null) {
            mValueFilter = new ValueFilterATM();
        }
        return mValueFilter;
    }
}
