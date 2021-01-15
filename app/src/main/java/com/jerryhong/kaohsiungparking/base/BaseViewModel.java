package com.jerryhong.kaohsiungparking.base;

import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseViewModel extends ViewModel {
    CompositeDisposable disposable = new CompositeDisposable();

    protected void addDisposable(Disposable d) {
        disposable.add(d);
    }

    protected void deleteDisposable(Disposable d) {
        disposable.delete(d);
    }

    @Override
    protected void onCleared() {
        disposable.clear();
        super.onCleared();
    }
}
