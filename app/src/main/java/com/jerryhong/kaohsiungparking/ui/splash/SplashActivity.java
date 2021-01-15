package com.jerryhong.kaohsiungparking.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.jerryhong.kaohsiungparking.ui.map.MainActivity;
import com.jerryhong.kaohsiungparking.R;

public class SplashActivity extends AppCompatActivity {

    private static final int DALLIS = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.ThemeLaunchScreen);
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, DALLIS);

    }
}
