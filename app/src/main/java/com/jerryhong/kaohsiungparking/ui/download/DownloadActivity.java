package com.jerryhong.kaohsiungparking.ui.download;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.Observer;

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

        //FIXME: 之後改成xml裡面加上ViewModel
        Button btnDownload = findViewById(R.id.btn_download);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                futureStudioIconFile = new File(getExternalFilesDir(null) + "test.csv");
                if(futureStudioIconFile.exists()){
                    boolean delete = futureStudioIconFile.delete();
                }
                viewModel.downloadParkingData(futureStudioIconFile);
            }
        });

        viewModel.toastMessage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(!TextUtils.isEmpty(s)){
                    Toast.makeText(DownloadActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.isShow.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    progressBar.setVisibility(View.VISIBLE);
                }
                else{
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}
