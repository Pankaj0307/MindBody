package com.mb.countrylist.repository;

import com.mb.countrylist.model.CountryListModel;
import com.mb.countrylist.model.ProvinceListModel;
import com.mb.countrylist.util.Constants;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/*interface to make api calls*/
public interface ApiInterface {

    @GET(Constants.get_country_list)
    Single<List<CountryListModel>> getCountryList();

    @GET(Constants.get_province_list)
    Single<List<ProvinceListModel>> getProvinceList(@Path("countryId") int countryId);
}
