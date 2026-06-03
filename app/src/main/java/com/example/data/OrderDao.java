package com.example.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import androidx.lifecycle.LiveData;
import java.util.List;

@Dao
public interface OrderDao {
    @Query("SELECT * FROM fuel_orders ORDER BY timestamp DESC")
    LiveData<List<FuelOrder>> getAllOrders();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertOrder(FuelOrder order);

    @Update
    void updateOrder(FuelOrder order);

    @Query("SELECT * FROM fuel_orders WHERE id = :id")
    LiveData<FuelOrder> getOrderById(int id);

    @Query("SELECT * FROM fuel_orders WHERE id = :id")
    FuelOrder getOrderByIdSync(int id);
}
