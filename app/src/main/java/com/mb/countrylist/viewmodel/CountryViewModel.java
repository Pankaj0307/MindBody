package com.mb.countrylist.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mb.countrylist.network.NoNetworkException;
import com.mb.countrylist.model.CountryListModel;
import com.mb.countrylist.model.ProvinceListModel;
import com.mb.countrylist.repository.CountryRepository;
import com.mb.countrylist.util.Constants;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
/*ViewModel with MutableLiveData which are observed in view models*/
public class CountryViewModel extends ViewModel {
    public MutableLiveData<Boolean> progressLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public MutableLiveData<List<CountryListModel>> countryMutableListModel = new MutableLiveData<>();
    public MutableLiveData<List<ProvinceListModel>> provinceMutableListModel = new MutableLiveData<>();

    CountryRepository countryRepository;

    public CountryViewModel(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public void getCountryList() {
        progressLiveData.postValue(true);
        countryRepository.getCountryList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<CountryListModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<CountryListModel> countryListModel) {
                        progressLiveData.postValue(false);
                        countryMutableListModel.postValue(countryListModel);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        handleErrorResponse(e);
                    }
                });
    }

    public void getProvinceList(int countryId) {
        progressLiveData.postValue(true);
        countryRepository.getProvinceList(countryId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<ProvinceListModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<ProvinceListModel> provinceListModelList) {
                        progressLiveData.postValue(false);
                        provinceMutableListModel.postValue(provinceListModelList);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        handleErrorResponse(e);
                    }
                });
    }
    private void handleErrorResponse(Throwable e) {
        try {
            if (e instanceof NoNetworkException) {
                errorLiveData.postValue(Constants.check_network);
                progressLiveData.postValue(false);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            errorLiveData.postValue(Constants.please_try_again);
        }
        progressLiveData.postValue(false);
    }

}