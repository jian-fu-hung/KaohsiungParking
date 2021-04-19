package com.jerryhong.kaohsiungparking.ui.info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.jerryhong.kaohsiungparking.R;
import com.jerryhong.kaohsiungparking.base.BaseActivity;
import com.jerryhong.kaohsiungparking.databinding.ActivityInfoBinding;

public class InfoActivity extends BaseActivity {

    private ActivityInfoBinding binding;

    private InfoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(InfoActivity.this, R.layout.activity_info);

        viewModel = ViewModelProviders.of(this).get(InfoViewModel.class);

        if(getIntent() != null){
            viewModel.readDB(getIntent().getExtras().getInt("ID"));
        }

        viewModel.parkingEntityMutableLiveData.observe(this, parkingEntity -> {
            binding.tvName.setText(parkingEntity.Name);

            binding.tvAddress.setText(parkingEntity.Address);
        });
    }

}
