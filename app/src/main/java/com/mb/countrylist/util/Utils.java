package com.mb.countrylist.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mb.countrylist.R;

public class Utils {

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

    public static SwipeRefreshLayout setUp(SwipeRefreshLayout swipeRefreshLayout, SwipeRefreshLayout.OnRefreshListener listener) {
        setColors(swipeRefreshLayout);
        setListener(listener, swipeRefreshLayout);
        return swipeRefreshLayout;
    }

    private static void setListener(final SwipeRefreshLayout.OnRefreshListener listener, final SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setOnRefreshListener(listener);
    }

    private static void setColors(final SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setColorSchemeResources(
                R.color.teal_200);
    }

}
