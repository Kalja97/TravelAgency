package com.example.travelagency.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.sqlite.SQLiteConstraintException;

import com.example.travelagency.Entities.Location;

import java.util.List;

@Dao
public abstract class LocationDao {

    @Query("Select * FROM locations WHERE countryName = :countryName")
    public abstract LiveData<Location> getByPK(String countryName);

    @Query("Select * From locations")
    public abstract LiveData<List<Location>> getAllLocations();

    @Insert
    public abstract long insert(Location location) throws SQLiteConstraintException;

    @Update
    public abstract void update(Location location);

    @Delete
    public abstract void delete(Location location);

    @Query("DELETE FROM locations")
    public abstract void deleteAll();
}
