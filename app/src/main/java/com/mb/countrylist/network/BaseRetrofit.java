package com.mb.countrylist.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mb.countrylist.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
/*Base Retrofit class to initialize network*/
public abstract class BaseRetrofit<T> {

    private T networkService;

    private Gson gson;

    private OkHttpClient okHttpClient;

    protected abstract String getBaseUrl();

    protected abstract Class<T> getRestClass();

    protected GsonBuilder gsonHandler(GsonBuilder builder) {
        return builder;
    }

    protected void initNetworkInterface() {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(getBaseUrl());
        Retrofit retrofit = retrofitHandler(retrofitBuilder)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .client(getOkHttpClient())
                .build();
        this.networkService = retrofit.create(getRestClass());
    }

    public T getNetworkService() {
        if (this.networkService == null) initNetworkInterface();

        return this.networkService;
    }

    protected Retrofit.Builder retrofitHandler(Retrofit.Builder builder) {
        return builder;
    }

    protected OkHttpClient.Builder okHttpClientHandler(OkHttpClient.Builder builder) {
        return builder;
    }

    public Gson getGson() {
        if (gson == null) {
            gson = gsonHandler(new GsonBuilder())
                    .create();
        }
        return gson;
    }

    public OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.level(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
            okHttpClient = okHttpClientHandler(new OkHttpClient.Builder()).addInterceptor(loggingInterceptor).build();
        }
        return okHttpClient;
    }
}