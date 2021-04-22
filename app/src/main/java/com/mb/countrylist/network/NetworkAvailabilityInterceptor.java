package com.mb.countrylist.network;

import android.content.Context;

import androidx.annotation.NonNull;

import com.mb.countrylist.R;
import com.mb.countrylist.util.Utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
/*Interceptor used to check network availability*/
public class NetworkAvailabilityInterceptor implements Interceptor {

    private final Context context;

    public NetworkAvailabilityInterceptor(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        if (!Utils.isOnline(context)) {
            throw new NoNetworkException(context.getResources().getString(R.string.check_network));
        }

        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());

    }
}
