package com.mb.countrylist.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.gson.Gson;
import com.mb.countrylist.R;
import com.mb.countrylist.model.CountryListModel;
import com.mb.countrylist.util.Constants;

import butterknife.BindView;
import butterknife.OnClick;

public class ActivityCountryList extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    NavHostFragment navHostFragment;

    private Bundle bundle;
    CountryListModel countryDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host);
        navHostFragment.getNavController().setGraph(R.navigation.nav_graph);
        setHeaderTitle(getResources().getString(R.string.country_list));
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_country_list;
    }

    public void setCountryDetails(CountryListModel countryDetails) {
        this.countryDetails = countryDetails;
    }

    public void setHeaderTitle(String title) {
        tv_title.setText(title);
    }

    public void navigate() {
        switch (navHostFragment.getNavController().getCurrentDestination().getId()) {
            case R.id.nav_country:
                bundle = new Bundle();
                bundle.putString(Constants.COUNTRY_DETAIL, new Gson().toJson(countryDetails));
                navHostFragment.getNavController().navigate(R.id.action_list_to_details, bundle);
                break;
        }
    }

    @OnClick({R.id.ivBack})
    void onClick(View view) {
        if (view.getId() == R.id.ivBack) {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        if (Navigation.findNavController(this, R.id.nav_host)
                .getCurrentDestination().getId() == R.id.nav_country) {
            finish();
        } else if (Navigation.findNavController(this, R.id.nav_host)
                .getCurrentDestination().getId() == R.id.nav_details) {
            Navigation.findNavController(this, R.id.nav_host)
                    .navigateUp();
        }
//        super.onBackPressed();
    }
}
