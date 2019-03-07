package com.example.travelagency.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.example.travelagency.Entities.Trip;

import java.util.List;

@Dao
public interface TripDao {

    @Query("SELECT * FROM trip")
    List<Trip> getAll();

    @Query("Select * FROM trip WHERE location IN (:countryName)")
    Trip trip(String countryName);

    @Query("Delete From Trip where id = :id")
    void delete(int id);

    @Query("Insert Into Trip Values(:location, :tripname, :duration, :date, :price, :description)")
    void insert(String location, String tripname, String duration, String date, String price, String description);

}
