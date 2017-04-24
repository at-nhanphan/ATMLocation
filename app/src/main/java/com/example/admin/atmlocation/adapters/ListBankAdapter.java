package com.example.admin.atmlocation.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.admin.atmlocation.R;
import com.example.admin.atmlocation.interfaces.MyOnClickListener;
import com.example.admin.atmlocation.models.ItemListBank;

import java.util.ArrayList;

/**
 * ListBankAdapter class
 * Created by naunem on 20/04/2017.
 */

public class ListBankAdapter extends RecyclerView.Adapter<ListBankAdapter.ViewHolder> {
    private ArrayList<ItemListBank> mBanks = new ArrayList<>();
    private MyOnClickListener mMyOnClickListener;
    public ArrayList<ItemListBank> mBankFilters = new ArrayList<>();
    private ValueFilter mValueFilter;

    public ListBankAdapter(ArrayList<ItemListBank> banks, MyOnClickListener myOnClickListener) {
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
        holder.mRbChoose.setChecked(mBanks.get(position).isCheck());
    }

    @Override
    public int getItemCount() {
        return mBanks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvName;
        private RadioButton mRbChoose;

        ViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mRbChoose = (RadioButton) itemView.findViewById(R.id.rbOk);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMyOnClickListener.onClick(getLayoutPosition());
                    mBanks.get(getLayoutPosition()).setCheck(true);
                    mRbChoose.setChecked(mBanks.get(getLayoutPosition()).isCheck());
                }
            });
        }
    }

    public class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<ItemListBank> listFilters = new ArrayList<>();
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

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mBanks = (ArrayList<ItemListBank>) results.values;
            notifyDataSetChanged();
        }
    }

    public ValueFilter getValueFilter() {
        if (mValueFilter == null) {
            mValueFilter = new ValueFilter();
        }
        return mValueFilter;
    }

    public ArrayList<ItemListBank> getResultFilter() {
        return mBanks;
    }
}
