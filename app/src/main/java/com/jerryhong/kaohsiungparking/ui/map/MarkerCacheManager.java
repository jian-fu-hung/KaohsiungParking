package com.jerryhong.kaohsiungparking.ui.map;

import com.google.android.gms.maps.model.Marker;
import com.jerryhong.kaohsiungparking.data.repository.model.ParkingEntity;

import java.util.HashMap;
import java.util.Map;

public class MarkerCacheManager {
    private HashMap<String, ParkingEntity> markers = new HashMap<>();

    public void add(String id, ParkingEntity parkingEntity){
        markers.put(id, parkingEntity);
    }

    public ParkingEntity get(String id){
        return (ParkingEntity)markers.get(id);
    }
}
