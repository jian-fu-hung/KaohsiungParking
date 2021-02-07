package com.jerryhong.kaohsiungparking.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.jerryhong.kaohsiungparking.R;
import com.jerryhong.kaohsiungparking.base.BaseActivity;
import com.jerryhong.kaohsiungparking.data.repository.model.ParkingEntity;
import com.jerryhong.kaohsiungparking.databinding.ActivitySearchBinding;

import java.util.List;

public class SearchActivity extends BaseActivity {

    private SearchViewModel viewModel;

    private ActivitySearchBinding binding;

    private SearchAdapter adapter;

    //監控虛擬鍵盤
    private InputMethodManager imm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

        //dataBinding設置ViewModel
        binding.setViewModel(viewModel);


        binding.imageMenu.setOnClickListener(v -> {
            finish();
        });

        binding.imageSearch.setOnClickListener(v -> {
            viewModel.searchParking(binding.editSearch.getText().toString());
        });

        viewModel.parkingList.observe(this, parkingEntities -> {
            //隱藏鍵盤
            imm.hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
            adapter.setList(parkingEntities);
            adapter.notifyDataSetChanged();
        });

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        initList();
    }

    private void initList() {
        binding.rvParkingList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchAdapter();
        binding.rvParkingList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.rvParkingList.setAdapter(adapter);

    }


}
