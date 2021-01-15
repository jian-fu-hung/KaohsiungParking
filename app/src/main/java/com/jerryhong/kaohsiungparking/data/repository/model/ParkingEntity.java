package com.jerryhong.kaohsiungparking.data.repository.model;

import com.jerryhong.kaohsiungparking.data.repository.DatabaseInfo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = DatabaseInfo.PARKING_TABLE)
public class ParkingEntity {

    @PrimaryKey(autoGenerate = true)
    public int ID;

    public String District;

    public String Type;

    public String Name;

    public String Address;

    public String Longitude;

    public String Latitude;

    public String LargeCar;

    public String SmallCar;

    public String Scooter;

    public String Bicycle;

    public String Charge;

    public String Remarks;


    public ParkingEntity() {

    }

    public ParkingEntity(String district, String type, String name, String address, String longitude, String latitude, String largeCar, String smallCar, String scooter, String bicycle, String charge, String remarks) {
        this.District = district;
        this.Type = type;
        this.Name = name;
        this.Address = address;
        this.Longitude = longitude;
        this.Latitude = latitude;
        this.LargeCar = largeCar;
        this.SmallCar = smallCar;
        this.Scooter = scooter;
        this.Bicycle = bicycle;
        this.Charge = charge;
        this.Remarks = remarks;
    }
}
