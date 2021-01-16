package com.jerryhong.kaohsiungparking.ui.map;

import com.jerryhong.kaohsiungparking.base.BaseViewModel;
import com.jerryhong.kaohsiungparking.data.DatabaseManager;
import com.jerryhong.kaohsiungparking.data.repository.model.ParkingEntity;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends BaseViewModel {

    public MutableLiveData<List<ParkingEntity>> parkingList = new MutableLiveData<>();

    public void readDB(){
        addDisposable(DatabaseManager.getInstance().getParkingDAO().selectAll()
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableSingleObserver<List<ParkingEntity>>(){
                        @Override
                        public void onSuccess(List<ParkingEntity> parkingEntityList) {
                            parkingList.postValue(parkingEntityList);
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }
                    }));
    }

    public MutableLiveData<List<ParkingEntity>> getParkingList() {
        return parkingList;
    }
}
