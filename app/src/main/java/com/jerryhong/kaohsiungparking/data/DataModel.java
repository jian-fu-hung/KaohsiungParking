package com.jerryhong.kaohsiungparking.data;

import com.jerryhong.kaohsiungparking.api.AppDataManager;
import com.jerryhong.kaohsiungparking.api.RetrofitManager;

import io.reactivex.Single;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class DataModel {

    private static DataModel instance;

    private AppDataManager appDataManager = RetrofitManager.getInstance().getAppDataManager();

    public static DataModel getInstance() {
        synchronized (DataModel.class) {
            if (instance == null) {
                instance = new DataModel();
            }
        }
        return instance;
    }

    public Single<ResponseBody> downloadParkingDataRepo() {
        return appDataManager.downloadParkingData();
    }

    public Single<ResponseBody> testApi() {
        return appDataManager.searchRepos("0", "76", "json");
    }

}
