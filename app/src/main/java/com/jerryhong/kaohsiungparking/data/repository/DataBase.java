package com.jerryhong.kaohsiungparking.data.repository;

import android.content.Context;

import com.jerryhong.kaohsiungparking.data.repository.dao.ParkingDAO;
import com.jerryhong.kaohsiungparking.data.repository.model.ParkingEntity;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ParkingEntity.class}, version = DatabaseInfo.DB_Version)
public abstract class DataBase extends RoomDatabase {

    public abstract ParkingDAO getParkingDAO();



}
