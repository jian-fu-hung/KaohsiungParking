package com.jerryhong.kaohsiungparking.data.repository.dao;

import com.jerryhong.kaohsiungparking.data.repository.DatabaseInfo;
import com.jerryhong.kaohsiungparking.data.repository.model.ParkingEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface ParkingDAO {

    @Insert
    void insert(ParkingEntity parkingEntity);

    @Query("Select * From " + DatabaseInfo.PARKING_TABLE + " where ID = :ID")
    Single<ParkingEntity> selectByID(int ID);

    @Query("SELECT * FROM " + DatabaseInfo.PARKING_TABLE)
    Single<List<ParkingEntity>> selectAll();

    @Query("SELECT * FROM "+ DatabaseInfo.PARKING_TABLE + " LIMIT 25")
    Single<List<ParkingEntity>> select10();

    @Query("Select * FROM " + DatabaseInfo.PARKING_TABLE + " where Name LIKE '%' || :keyword || '%' or District LIKE '%' || :keyword || '%' or Address LIKE '%' || :keyword || '%'")
    Single<List<ParkingEntity>> selectKeyword(String keyword);

    @Query("DELETE FROM " + DatabaseInfo.PARKING_TABLE)
    void deleteAll();
}
