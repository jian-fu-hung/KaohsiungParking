package com.jerryhong.kaohsiungparking.base;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity extends AppCompatActivity {
    CompositeDisposable disposable = new CompositeDisposable();

    protected void addDisposable(Disposable d) {
        disposable.add(d);
    }

    protected void deleteDisposable(Disposable d) {
        disposable.delete(d);
    }

    @Override
    protected void onDestroy() {
        disposable.clear();
        super.onDestroy();
    }

}
