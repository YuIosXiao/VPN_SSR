package com.wwws.wwwsvpn.myapplication.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by admin on 2018/6/4.
 */

public class AppUpdateHelper {
    private static final String TAG = "AppUpdateHelper";

    private static AppUpdateHelper mInstance;
    private Retrofit mRetrofit;
    private OkHttpClient mHttpClient;

    private AppUpdateHelper() {
        this( 30, 30, 30);
    }

    public AppUpdateHelper( int connTimeout, int readTimeout, int writeTimeout) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(connTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS);

        mHttpClient = builder.build();
    }

    public static AppUpdateHelper getInstance() {
        if (mInstance == null) {
            mInstance = new AppUpdateHelper();
        }

        return mInstance;
    }

    public AppUpdateHelper buildRetrofit(String baseUrl) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mHttpClient)
                .build();
        return this;
    }

    public <T> T createService(Class<T> serviceClass) {
        return mRetrofit.create(serviceClass);
    }
}
