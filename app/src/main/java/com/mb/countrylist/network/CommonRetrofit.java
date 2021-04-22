package com.mb.countrylist.network;

import android.content.Context;

import androidx.annotation.NonNull;

import com.mb.countrylist.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/*Common class to get base url and client handler*/
public abstract class CommonRetrofit<T> extends BaseRetrofit<T> {

    private final Context context;

    public CommonRetrofit(Context context) {
        this.context = context;
    }

    @Override
    protected String getBaseUrl() {
        return BuildConfig.BASE_URL;
    }

    @Override
    protected OkHttpClient.Builder okHttpClientHandler(OkHttpClient.Builder builder) {
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.writeTimeout(60, TimeUnit.SECONDS);
        builder.addInterceptor(new NetworkAvailabilityInterceptor(context));
        builder.addInterceptor(new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });
        return super.okHttpClientHandler(builder);
    }
}