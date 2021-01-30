package com.jerryhong.kaohsiungparking.ui.search;

import androidx.lifecycle.MutableLiveData;

import com.jerryhong.kaohsiungparking.base.BaseViewModel;
import com.jerryhong.kaohsiungparking.data.DatabaseManager;
import com.jerryhong.kaohsiungparking.data.repository.model.ParkingEntity;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SearchViewModel extends BaseViewModel {

    private MutableLiveData<List<ParkingEntity>> parkingList = new MutableLiveData<>();

    public void searchParking(String keyword){
        addDisposable(DatabaseManager.getInstance().getParkingDAO().selectKeyword(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<ParkingEntity>>(){
                    @Override
                    public void onSuccess(@NonNull List<ParkingEntity> parkingEntities) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                }));
    }
}
