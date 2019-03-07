package com.example.travelagency.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.example.travelagency.Entities.Location;

import java.util.List;

@Dao
public interface LocationDao {

    @Query("Select * From Location")
    List<Location> getAllLocation();

    @Query("Insert Into Location Values(:countryName, :inhabitants, :description, :language)")
    void insert(String countryName, String inhabitants, String description, String language);

    @Query("Delete From Location Where countryName = :countryName")
    void delete(String countryName);
}
