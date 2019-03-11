package com.example.travelagency.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.travelagency.Entities.Trip;

import java.util.List;

@Dao
public interface TripDao {

    @Query("SELECT * FROM trip")
    List<Trip> getAll();

    @Query("Select * FROM trip WHERE location IN (:countryName)")
    Trip trip(String countryName);

    @Delete
    void delete(int id);

    @Insert
    void insert(String location, String tripname, String duration, String date, String price, String description);

    @Update
    void update();

}
