package com.mb.countrylist.repository;

import android.content.Context;

import com.mb.countrylist.network.CommonRetrofit;
import com.mb.countrylist.model.CountryListModel;
import com.mb.countrylist.model.ProvinceListModel;

import java.util.List;

import io.reactivex.Single;
/*repository*/
public class CountryRepository extends CommonRetrofit<ApiInterface> {

    public CountryRepository(Context context) {
        super(context);
    }

    @Override
    protected Class getRestClass() {
        return ApiInterface.class;
    }


    public Single<List<CountryListModel>> getCountryList() {
        return getNetworkService().getCountryList();
    }

    public Single<List<ProvinceListModel>> getProvinceList(int countryId) {
        return getNetworkService().getProvinceList(countryId);
    }
}
