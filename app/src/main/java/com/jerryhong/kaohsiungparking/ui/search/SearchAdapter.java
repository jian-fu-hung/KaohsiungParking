package com.jerryhong.kaohsiungparking.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jerryhong.kaohsiungparking.R;
import com.jerryhong.kaohsiungparking.data.repository.model.ParkingEntity;
import com.jerryhong.kaohsiungparking.databinding.SearchItemBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    List<ParkingEntity> list = new ArrayList<>();

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // TODO: 2021/2/7 這邊要記錄一下為何是 SearchItemBinding 而不是 DataBindingUtil
        SearchItemBinding binding = SearchItemBinding.inflate(layoutInflater, parent, false);
        return new SearchViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        ParkingEntity parkingEntity = list.get(position);
        holder.binding.tvName.setText(parkingEntity.Name);
        holder.binding.tvAddress.setText(parkingEntity.Address);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<ParkingEntity> list){
        this.list = list;
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder{

        private SearchItemBinding binding;

        public SearchViewHolder(@NonNull SearchItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
