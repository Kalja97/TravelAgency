package com.example.travelagency.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.travelagency.Entities.Location;
import com.example.travelagency.Entities.Trip;

import java.util.List;

@Dao
public interface TripDao {

    @Query("SELECT * FROM trips")
    LiveData<List<Trip>> getAll();

    @Query("Select * FROM trips WHERE location = :countryName")
    LiveData<Trip> trip(String countryName);

    @Delete
    void delete(Trip trip);

    @Insert
    long insert(Trip trip);

    @Update
    void update(Trip trip);

    @Query("DELETE FROM trips")
    void deleteAll();
}
