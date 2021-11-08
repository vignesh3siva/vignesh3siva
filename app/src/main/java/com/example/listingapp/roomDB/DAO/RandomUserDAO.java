package com.example.listingapp.roomDB.DAO;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.listingapp.roomDB.model.RandomUserModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface RandomUserDAO {


    @Query("SELECT * FROM randomusers")
    List<RandomUserModel> getAllRandomUsers();


    @Query("SELECT * FROM randomusers")
    DataSource.Factory<Integer, RandomUserModel> getAllRandomUserPaging();

    @Query("SELECT * FROM randomusers where id=:id")
    RandomUserModel getRandomUserByID(String id);

    @Query("SELECT * FROM randomusers")
    LiveData<List<RandomUserModel>> getAllRandomUsersLiveData();

    @Query("select city  from randomusers where id=:id")
    String getCityName(String id);

    @Query("select *  from randomusers where first like :search or last like :search")
    List<RandomUserModel> searchFirstAndLastName(String search);

    @Query("select *  from randomusers where first like :search or last like :search")
    DataSource.Factory<Integer, RandomUserModel>  searchFirstAndLastNamePaging(String search);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void INSERTRANDOMUSERS(List<RandomUserModel> data);

    @Query("DELETE FROM randomusers")
    void DELETEALLRANDOMUSERS();
}
