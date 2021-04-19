package com.jerryhong.kaohsiungparking.ui.info;

import com.jerryhong.kaohsiungparking.base.BaseViewModel;
import com.jerryhong.kaohsiungparking.data.DatabaseManager;
import com.jerryhong.kaohsiungparking.data.repository.model.ParkingEntity;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class InfoViewModel extends BaseViewModel {

    public MutableLiveData<ParkingEntity> parkingEntityMutableLiveData = new MutableLiveData<>();

    public void readDB(int ID){
        addDisposable(DatabaseManager.getInstance().getParkingDAO().selectByID(ID)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<ParkingEntity>(){

                        @Override
                        public void onSuccess(ParkingEntity parkingEntity) {
                            parkingEntityMutableLiveData.setValue(parkingEntity);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    }));
    }
}
