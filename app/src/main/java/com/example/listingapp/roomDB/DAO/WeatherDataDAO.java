package com.example.listingapp.roomDB.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.listingapp.roomDB.model.WeatherDataModel;

@Dao
public interface WeatherDataDAO {
    @Query("SELECT * FROM weatherdetails")
    LiveData<WeatherDataModel> getAllWeatherDetails();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void INSERTWEATHERDETAILS(WeatherDataModel... data);

    @Query("DELETE FROM weatherdetails")
    void DELETEWEATHERDETAILS();
}
