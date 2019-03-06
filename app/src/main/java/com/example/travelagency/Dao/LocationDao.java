package com.example.travelagency.Dao;

import com.example.travelagency.Entities.Location;

import java.util.List;

//@Dao
public interface LocationDao {

  //  @Query("Select * From Location")
    List<Location> getAllLocation();

    //@Insert("Insert countryName, inhabitants, description, language Into Location")
    void insert(String countryName, String inhabitant, String description, String language);

    //@Delete("Delete From Location Where countryName = :countryName")
    void delete(String countryName);
}
