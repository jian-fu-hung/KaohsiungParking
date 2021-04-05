package com.jerryhong.kaohsiungparking.ui.map;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.jerryhong.kaohsiungparking.R;
import com.jerryhong.kaohsiungparking.data.repository.model.ParkingEntity;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Activity activity;

    private MarkerCacheManager cacheManager;

    public CustomInfoWindowAdapter(Activity activity, MarkerCacheManager cacheManager) {
        this.activity = activity;
        this.cacheManager = cacheManager;
    }

    @Override
    public View getInfoWindow(Marker marker) {

        ParkingEntity parkingEntity = cacheManager.get(marker.getSnippet());

        View view = activity.getLayoutInflater().inflate(R.layout.info_window, null);

        TextView parkingName = view.findViewById(R.id.tv_parking_name);
        TextView smallCarNumber = view.findViewById(R.id.tv_small_car_number);
        TextView scooterNumber = view.findViewById(R.id.tv_scooter_number);

        parkingName.setText(parkingEntity.Name);
        smallCarNumber.setText(!TextUtils.isEmpty(parkingEntity.SmallCar) ? parkingEntity.SmallCar : "0");
        scooterNumber.setText(!TextUtils.isEmpty(parkingEntity.Scooter) ? parkingEntity.Scooter : "0");
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
