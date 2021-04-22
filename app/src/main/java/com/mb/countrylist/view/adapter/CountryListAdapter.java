package com.mb.countrylist.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mb.countrylist.R;
import com.mb.countrylist.model.CountryListModel;

import java.util.List;
/*Adapter to country list items*/
public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.ViewHolder> {
    private Context context;
    private List<CountryListModel> countryListModelList;
    private CountryClickListener countryClickListener;

    public CountryListAdapter(Context context, List<CountryListModel> countryListModelList, CountryClickListener countryClickListener) {
        this.context = context;
        this.countryListModelList = countryListModelList;
        this.countryClickListener = countryClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_country, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CountryListModel countryListModel = countryListModelList.get(position);
        if (countryListModel != null) {
            holder.tv_country_name.setText(countryListModel.getName());
            String imageName = countryListModel.getCode().toLowerCase();
            if (imageName.length() > 0) {
                if (imageName.equalsIgnoreCase("do")) {
                    imageName = "do_flag";
                    holder.iv_country.setImageResource(context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName()));
                } else {
                    holder.iv_country.setImageResource(context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName()));
                    // if we do not have country flag in our resources then showing default image
                    if (holder.iv_country.getDrawable() == null) {
                        holder.iv_country.setBackgroundResource(R.drawable.ic_flag);
                    }
                }
            }
        }

        holder.cv_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countryClickListener.onItemClicked(countryListModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return countryListModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv_country;
        private AppCompatImageView iv_country;
        private TextView tv_country_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cv_country = itemView.findViewById(R.id.cv_country);
            iv_country = itemView.findViewById(R.id.iv_country);
            tv_country_name = itemView.findViewById(R.id.tv_country_name);
        }
    }


    public interface CountryClickListener {
        void onItemClicked(CountryListModel countryListModel);
    }
}
