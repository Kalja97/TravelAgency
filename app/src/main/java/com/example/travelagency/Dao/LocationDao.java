package com.example.travelagency.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.travelagency.Entities.Location;

import java.util.List;

@Dao
public interface LocationDao {

    @Query("Select * From Location")
    List<Location> getAllLocation();

    @Insert
    void insert(String countryName, String inhabitants, String description, String language);

    @Delete
    void delete(String countryName);

    @Update
    void update();
}
