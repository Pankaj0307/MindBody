package com.mb.countrylist.view.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.mb.countrylist.R;
import com.mb.countrylist.model.CountryListModel;
import com.mb.countrylist.model.ProvinceListModel;
import com.mb.countrylist.repository.CountryRepository;
import com.mb.countrylist.util.Constants;
import com.mb.countrylist.util.Utils;
import com.mb.countrylist.view.ActivityCountryList;
import com.mb.countrylist.view.BaseFragment;
import com.mb.countrylist.view.adapter.ProvinceListAdapter;
import com.mb.countrylist.viewmodel.CountryViewModel;

import java.util.List;

import butterknife.BindView;

public class FragmentCountryDetails extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.ll_main)
    LinearLayout ll_main;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.rv_province)
    RecyclerView rv_province;
    @BindView(R.id.prog_lazy_load)
    ProgressBar prog_lazy_load;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_province_counts)
    TextView tv_province_counts;

    private LinearLayoutManager mLayoutManager;
    private CountryViewModel countryViewModel;
    private CountryRepository countryRepository;
    ViewModelProvider.Factory viewModelFactory;
    ProvinceListAdapter provinceListAdapter;
    private CountryListModel countryListModel;
    private int countryId = 0;

    @Override
    protected void initView() {
        initViews();
    }

    @Override
    protected void setupViewModel() {
        countryRepository = new CountryRepository(activity);
        countryViewModel = new CountryViewModel(countryRepository);
        getArgumentValues();
        getProvinceList();

        countryViewModel.progressLiveData.observe(this, aBoolean -> {
            if (aBoolean) {
                showDialog();
            } else {
                dimisssDialog();
            }
        });

        countryViewModel.provinceMutableListModel.observe(this, new Observer<List<ProvinceListModel>>() {
            @Override
            public void onChanged(List<ProvinceListModel> provinceListModelList) {
                prog_lazy_load.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
                if (provinceListModelList != null) {
                    if (provinceListModelList.size() > 0) {
                        setProvinceList(provinceListModelList);
                    } else if (provinceListModelList.size() == 0) {
                        showError(getResources().getString(R.string.no_data_found), false);
                    }
                }
            }
        });

        countryViewModel.errorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                swipeRefresh.setRefreshing(false);
                if (s != null && s.length() > 0) {
                    showError(s, true);
                }
            }
        });

    }

    private void getArgumentValues() {
        if (getArguments().getString(Constants.COUNTRY_DETAIL) != null && getArguments().getString(Constants.COUNTRY_DETAIL).length() > 0) {
            countryListModel = new Gson().fromJson(getArguments().getString(Constants.COUNTRY_DETAIL), CountryListModel.class);
            countryId = countryListModel.getID();
            tv_name.setText(countryListModel.getName());
        }
    }

    //setting province list in adapter
    private void setProvinceList(List<ProvinceListModel> provinceList) {
        tv_province_counts.setText(getResources().getString(R.string.total_province).concat(String.valueOf(provinceList.size())));
        provinceListAdapter = new ProvinceListAdapter(activity, provinceList);
        rv_province.setAdapter(provinceListAdapter);
    }

    // get province list by calling api
    private void getProvinceList() {
        if (countryId > 0)
            countryViewModel.getProvinceList(countryId);
    }


    //if getting api error then user can retry else user can tap ok to destroy snackbar
    private void showError(String s, boolean doRetry) {
        Snackbar.make(ll_main, s, Snackbar.LENGTH_LONG)
                .setAction(doRetry ? getResources().getString(R.string.retry) : getResources().getString(R.string.ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (doRetry)
                            getProvinceList();
                    }
                })
                .setActionTextColor(getResources().getColor(doRetry ? android.R.color.holo_red_dark : R.color.teal_200))
                .show();
    }

    //initializing views
    private void initViews() {
        ((ActivityCountryList) activity).setHeaderTitle(getResources().getString(R.string.country_detail));
        Utils.setUp(swipeRefresh, this);
        mLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        rv_province.setNestedScrollingEnabled(false);
        rv_province.setLayoutManager(mLayoutManager);
        rv_province.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_country_details;
    }

    @Override
    public void onRefresh() {
        getProvinceList();
    }



}
