package com.mb.countrylist.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mb.countrylist.R;
import com.mb.countrylist.model.ProvinceListModel;

import java.util.List;
/*Adapter to province list items*/
public class ProvinceListAdapter extends RecyclerView.Adapter<ProvinceListAdapter.ViewHolder> {
    private Context context;
    private List<ProvinceListModel> provinceListModelList;

    public ProvinceListAdapter(Context context, List<ProvinceListModel> provinceListModelList) {
        this.context = context;
        this.provinceListModelList = provinceListModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_province, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProvinceListModel provinceListModel = provinceListModelList.get(position);
        if (provinceListModel != null) {
            holder.tv_province_name.setText(provinceListModel.getName());
        }

    }

    @Override
    public int getItemCount() {
        return provinceListModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_province_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_province_name = itemView.findViewById(R.id.tv_province_name);
        }
    }
}
