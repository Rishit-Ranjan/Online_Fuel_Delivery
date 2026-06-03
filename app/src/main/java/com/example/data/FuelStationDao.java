package com.example.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import androidx.lifecycle.LiveData;
import java.util.List;

@Dao
public interface FuelStationDao {
    @Query("SELECT * FROM fuel_stations ORDER BY distance ASC")
    LiveData<List<FuelStation>> getAllStations();

    @Query("SELECT COUNT(*) FROM fuel_stations")
    int getStationCount();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStations(List<FuelStation> stations);

    @Update
    void updateStation(FuelStation station);

    @Query("SELECT * FROM fuel_stations WHERE id = :id")
    LiveData<FuelStation> getStationById(int id);
}
