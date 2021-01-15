package com.jerryhong.kaohsiungparking.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static RetrofitManager mInstance;

    private AppDataManager appDataManager;

    private static String URL = "https://data.kcg.gov.tw/";

    private static final int connect = 60;

    private static final int read = 60;

    private static final int write = 60;

    private RetrofitManager(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new OkHttpClient().newBuilder()
                        .connectTimeout(connect, TimeUnit.SECONDS)
                        .readTimeout(read, TimeUnit.SECONDS)
                        .writeTimeout(write, TimeUnit.SECONDS)
                        .build())
                .build();
        appDataManager = retrofit.create(AppDataManager.class);
    }

    public static RetrofitManager getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitManager.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitManager();
                }
            }
        }
        return mInstance;
    }

    public AppDataManager getAppDataManager() {
        return appDataManager;
    }
}
