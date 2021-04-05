package com.jerryhong.kaohsiungparking.base;

import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jerryhong.kaohsiungparking.R;

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

    protected void errorMessage(String message){
        if(!TextUtils.isEmpty(message)){
            Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    }

    protected void errorMessage(int messageId){
//        errorMessage();
    }

}
