package com.jerryhong.kaohsiungparking.ui.download;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.jerryhong.kaohsiungparking.R;
import com.jerryhong.kaohsiungparking.base.BaseActivity;
import com.jerryhong.kaohsiungparking.data.DatabaseManager;
import com.jerryhong.kaohsiungparking.databinding.ActivityDownloadBinding;

import java.io.File;

public class DownloadActivity extends BaseActivity {

    private DownloadViewModel viewModel;

    private ActivityDownloadBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(DownloadActivity.this, R.layout.activity_download);
        viewModel = ViewModelProviders.of(DownloadActivity.this).get(DownloadViewModel.class);
        binding.setDownloadViewModel(viewModel);

        DatabaseManager.getInstance().initDatabase(getApplicationContext());

        //FIXME: 之後改成xml裡面加上ViewModel
        binding.btnDownload.setOnClickListener(view -> {
            File futureStudioIconFile = new File(DownloadActivity.this.getExternalFilesDir(null) + "test.csv");
            if (futureStudioIconFile.exists()) {
                //刪除檔案
                futureStudioIconFile.delete();
            }
            viewModel.downloadParkingData(futureStudioIconFile);
        });

        // TODO: HF 2021/4/5 有空研究一下 this::errorMessage為何可以這樣寫
        viewModel.toastMessage.observe(this, this::errorMessage);

        viewModel.isShow.observe(this, aBoolean -> {
            if (aBoolean) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
