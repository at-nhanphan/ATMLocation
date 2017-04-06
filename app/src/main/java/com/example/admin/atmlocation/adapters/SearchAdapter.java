package com.example.admin.atmlocation.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.admin.atmlocation.R;

import java.util.ArrayList;

/**
 * SearchAdapter class
 * Created by naunem on 05/04/2017.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements Filterable {
    private ArrayList<String> mMonths;
    private ArrayList<String> mStringFilterList;
    private ValueFilter mValueFilter;

    public SearchAdapter(ArrayList<String> months) {
        this.mMonths = months;
        this.mStringFilterList = months;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_search, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvText.setText(mMonths.get(position));
    }

    @Override
    public int getItemCount() {
        return mMonths.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvText;

        ViewHolder(View itemView) {
            super(itemView);
            tvText = (TextView) itemView.findViewById(R.id.stringName);
        }
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
                ArrayList filterList = new ArrayList();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(mStringFilterList.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mMonths = (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }
    }
}
