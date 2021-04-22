package com.mb.countrylist.view.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.mb.countrylist.R;
import com.mb.countrylist.model.CountryListModel;
import com.mb.countrylist.repository.CountryRepository;
import com.mb.countrylist.util.Utils;
import com.mb.countrylist.view.ActivityCountryList;
import com.mb.countrylist.view.BaseFragment;
import com.mb.countrylist.view.adapter.CountryListAdapter;
import com.mb.countrylist.viewmodel.CountryViewModel;

import java.util.List;

import butterknife.BindView;

public class FragmentCountryList extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.ll_main)
    LinearLayout ll_main;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.rv_country)
    RecyclerView rv_country;
    @BindView(R.id.prog_lazy_load)
    ProgressBar prog_lazy_load;
    NavHostFragment navHostFragment;

    private LinearLayoutManager mLayoutManager;
    private CountryViewModel countryViewModel;
    private CountryRepository countryRepository;
    ViewModelProvider.Factory viewModelFactory;
    CountryListAdapter countryListAdapter;

    @Override
    protected void initView() {
        initViews();
    }

    @Override
    protected void setupViewModel() {
        countryRepository = new CountryRepository(activity);
        countryViewModel = new CountryViewModel(countryRepository);

        getCountryList();
        countryViewModel.progressLiveData.observe(this, aBoolean -> {
            if (aBoolean) {
                showDialog();
            } else {
                dimisssDialog();
            }
        });

        countryViewModel.countryMutableListModel.observe(this, new Observer<List<CountryListModel>>() {
            @Override
            public void onChanged(List<CountryListModel> countryListModels) {
                prog_lazy_load.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
                if (countryListModels != null) {
                    if (countryListModels.size() > 0) {
                        setCountryList(countryListModels);
                    } else if (countryListModels.size() == 0) {
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

    //setting country list in adapter
    private void setCountryList(List<CountryListModel> countryListModels) {
        countryListAdapter = new CountryListAdapter(activity, countryListModels, new CountryListAdapter.CountryClickListener() {
            @Override
            public void onItemClicked(CountryListModel countryListModel) {
                ((ActivityCountryList) activity).setCountryDetails(countryListModel);
                ((ActivityCountryList) activity).navigate();
            }
        });
        rv_country.setAdapter(countryListAdapter);
    }

    // get country list by calling api
    private void getCountryList() {
        countryViewModel.getCountryList();
    }

    //if getting api error then user can retry else user can tap ok to destroy snackbar
    private void showError(String s, boolean doRetry) {
        Snackbar.make(ll_main, s, Snackbar.LENGTH_LONG)
                .setAction(doRetry ? getResources().getString(R.string.retry) : getResources().getString(R.string.ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (doRetry)
                            getCountryList();
                    }
                })
                .setActionTextColor(getResources().getColor(doRetry ? android.R.color.holo_red_dark : R.color.teal_200))
                .show();
    }

    //initializing views
    private void initViews() {
        Utils.setUp(swipeRefresh, this);
        mLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        rv_country.setNestedScrollingEnabled(false);
        rv_country.setLayoutManager(mLayoutManager);
        rv_country.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_country_list;
    }

    @Override
    public void onRefresh() {
        getCountryList();
    }
}
