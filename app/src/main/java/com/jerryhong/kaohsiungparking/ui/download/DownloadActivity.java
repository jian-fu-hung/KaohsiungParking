package com.jerryhong.kaohsiungparking.ui.download;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jerryhong.kaohsiungparking.R;
import com.jerryhong.kaohsiungparking.base.BaseActivity;
import com.jerryhong.kaohsiungparking.data.DatabaseManager;

import java.io.File;

public class DownloadActivity extends BaseActivity {

    private DownloadViewModel viewModel = new DownloadViewModel();

    File futureStudioIconFile ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        DatabaseManager.getInstance().initDatabase(getApplicationContext());

        Button btnDownload = findViewById(R.id.btn_download);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                futureStudioIconFile = new File(getExternalFilesDir(null) + "test.csv");
                viewModel.downloadParkingData(futureStudioIconFile);
            }
        });
    }
}
