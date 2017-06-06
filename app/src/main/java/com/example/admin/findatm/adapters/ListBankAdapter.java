package com.example.admin.findatm.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.findatm.R;
import com.example.admin.findatm.interfaces.MyOnClickListener;
import com.example.admin.findatm.models.ItemListBank;

import java.util.ArrayList;
import java.util.List;

/**
 * ListBankAdapter class
 * Created by naunem on 20/04/2017.
 */

public class ListBankAdapter extends RecyclerView.Adapter<ListBankAdapter.ViewHolder> {

    private List<ItemListBank> mBanks = new ArrayList<>();
    private final MyOnClickListener mMyOnClickListener;
    private List<ItemListBank> mBankFilters = new ArrayList<>();
    private ValueFilter mValueFilter;

    public ListBankAdapter(List<ItemListBank> banks, MyOnClickListener myOnClickListener) {
        this.mBanks = banks;
        this.mMyOnClickListener = myOnClickListener;
        this.mBankFilters = banks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_bank, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTvName.setText(mBanks.get(position).getName());
        holder.mImgCheck.setSelected(mBanks.get(position).isCheck());
    }

    @Override
    public int getItemCount() {
        return mBanks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvName;
        private final ImageView mImgCheck;

        ViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mImgCheck = (ImageView) itemView.findViewById(R.id.imgCheck);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMyOnClickListener.onClick(getLayoutPosition());
                    mBanks.get(getLayoutPosition()).setCheck(true);
                    mImgCheck.setSelected(mBanks.get(getLayoutPosition()).isCheck());
                }
            });
        }
    }

    @SuppressWarnings("WeakerAccess")
    public class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                List<ItemListBank> listFilters = new ArrayList<>();
                for (int i = 0; i < mBankFilters.size(); i++) {
                    if (mBankFilters.get(i).getName().toUpperCase().contains(constraint.toString().toUpperCase())) {
                        listFilters.add(mBankFilters.get(i));
                    }
                }
                results.count = listFilters.size();
                results.values = listFilters;
            } else {
                results.count = mBankFilters.size();
                results.values = mBankFilters;
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mBanks = (List<ItemListBank>) results.values;
            notifyDataSetChanged();
        }
    }

    public ValueFilter getValueFilter() {
        if (mValueFilter == null) {
            mValueFilter = new ValueFilter();
        }
        return mValueFilter;
    }

    public List<ItemListBank> getResultFilter() {
        return mBanks;
    }
}
