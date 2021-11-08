package com.example.listingapp.roomDB;

import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.RoomDatabase;

import com.example.listingapp.roomDB.DAO.RandomUserDAO;
import com.example.listingapp.roomDB.DAO.WeatherDataDAO;
import com.example.listingapp.roomDB.model.RandomUserModel;
import com.example.listingapp.roomDB.model.WeatherDataModel;

@Database(entities = {RandomUserModel.class, WeatherDataModel.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    public abstract RandomUserDAO randomUserDAO();
    public abstract WeatherDataDAO weatherDataDAO();
}
