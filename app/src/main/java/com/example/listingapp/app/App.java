package com.example.listingapp.app;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.listingapp.roomDB.RoomDB;
import com.example.listingapp.roomDB.model.RandomUserModel;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    private static App INSTANCE;
    private static RequestQueue requestQueue;
    public static Context appContext;
    private static RoomDB database;
    private static String DATABASE_NAME="listingapp";


    public static Context getAppContext() {
        return appContext;
    }

    public static App get() {
        return INSTANCE;
    }

    public static App getINSTANCE() {
        return INSTANCE;
    }
    public RoomDB getDB() {
        return database;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        initRoomDB();
    }

    private void initRoomDB() {


        database = Room.databaseBuilder(this, RoomDB.class, DATABASE_NAME)
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                        super.onOpen(db);
                    }
                })
                .allowMainThreadQueries()
                .build();

        INSTANCE = this;

    }

    public <T> void addRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }
    public RequestQueue getRequestQueue() {

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(this/*, new OkHttpStack()*/);
        }
        return requestQueue;

    }
}
