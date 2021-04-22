package com.mb.countrylist.network;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.mb.countrylist.network.RxSchedulersOverrideRule;
import com.mb.countrylist.model.CountryListModel;
import com.mb.countrylist.model.ProvinceListModel;
import com.mb.countrylist.repository.CountryRepository;
import com.mb.countrylist.viewmodel.CountryViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class CountryViewModelTest {
    @Rule
    public RxSchedulersOverrideRule rxSchedulersOverrideRule = new RxSchedulersOverrideRule();
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    CountryRepository countryRepository;
    @Mock
    CountryListModel countryListModel;
    @Mock
    CountryViewModel countryViewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
        countryListModel = Mockito.mock(CountryListModel.class);
        countryRepository = Mockito.mock(CountryRepository.class);
        countryViewModel = Mockito.mock(CountryViewModel.class);
        /*countryViewModel.countryMutableListModel.observeForever(new androidx.lifecycle.Observer<List<CountryListModel>>() {
            @Override
            public void onChanged(List<CountryListModel> countryListModels) {

            }
        });*/
    }

    @Test
    public void testNull() {

    }

    @Test
    public void testGetCountryListSuccess() {
        // Mock API response
        CountryListModel countryListModel = new CountryListModel();
        countryListModel.setID(227);
        countryListModel.setCode("US");
        countryListModel.setName("UNITED STATES");
        countryListModel.setPhoneCode("1");
        List<CountryListModel> countryListModels = new ArrayList<>();
        countryListModels.add(countryListModel);
        when(countryRepository.getCountryList()).thenReturn(Single.just(countryListModels));
        countryViewModel.getCountryList();
//        doReturn(countryViewModel.getCountryList()).when(countryViewModel.getCountryList());
//        doReturn(countryViewModel.countryMutableListModel);
        System.out.print(countryViewModel.countryMutableListModel);
    }

    @Test
    public void testApiFetchDataError() {
        when(countryRepository.getCountryList()).thenReturn(Single.error(new Throwable("Api error")));
        countryViewModel.getCountryList();
    }

    @Test
    public void testGetProvinceListSuccess() {
        // Mock API response
        List<ProvinceListModel> provinceListModels = new ArrayList<>();
        when(countryRepository.getProvinceList(227)).thenReturn(Single.just(provinceListModels));
        countryViewModel.getProvinceList(227);
    }

    @Test
    public void testApiProvinceFetchDataError() {
        when(countryRepository.getProvinceList(227)).thenReturn(Single.error(new Throwable("Api error")));
        countryViewModel.getProvinceList(227);
    }

    @After
    public void tearDown() throws Exception {
        countryViewModel = null;
        countryRepository = null;
        RxAndroidPlugins.reset();
    }
}