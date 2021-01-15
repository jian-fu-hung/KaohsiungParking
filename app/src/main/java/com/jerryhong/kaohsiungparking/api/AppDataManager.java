package com.jerryhong.kaohsiungparking.api;

import java.util.List;

import io.reactivex.Single;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface AppDataManager {


    @GET("/dataset/449e45d9-dead-4873-95a9-cc34dabbb3af/resource/fe3f93da-9673-4f7b-859c-9017d793f798/download/109.11.1.csv")
    Single<ResponseBody> downloadParkingData();

    @GET("/webapi/Data/ATM00679/")
    Single<ResponseBody> searchRepos(@Query("$skip") String skip, @Query("$top") String top, @Query("format") String format);

}
