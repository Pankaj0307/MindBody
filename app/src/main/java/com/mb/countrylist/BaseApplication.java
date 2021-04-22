package com.mb.countrylist;


import android.app.Application;

//Application class used to get context
public class BaseApplication extends Application {

    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }


    public static BaseApplication getInstance() {
        return instance;
    }
}