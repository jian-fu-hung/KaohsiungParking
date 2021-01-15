package com.jerryhong.kaohsiungparking.data;

import android.content.Context;

import com.jerryhong.kaohsiungparking.data.repository.DataBase;
import com.jerryhong.kaohsiungparking.data.repository.DatabaseInfo;
import com.jerryhong.kaohsiungparking.data.repository.dao.ParkingDAO;

import androidx.room.Room;

public class DatabaseManager {

    private static DatabaseManager instance = null;

    private static DataBase db;

    private DatabaseManager() {

    }

    public static synchronized DatabaseManager getInstance(){
        if(instance == null){
            instance = new DatabaseManager();
        }
        return instance;
    }

    public void initDatabase(Context context){
        db = Room.databaseBuilder(context, DataBase.class, DatabaseInfo.DATABASE_NAME).build();
    }

    public void closeDatabase(){
        db.close();
    }

    public ParkingDAO getParkingDAO(){
        return db.getParkingDAO();
    }

}
