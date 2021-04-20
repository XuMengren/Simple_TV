package com.example.simpletv.SearchHistoryDataBase;

import android.app.Application;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.simpletv.UsersDataBase.FavoriteDatabase;
import com.example.simpletv.UsersDataBase.FavoriteVideo;
import com.example.simpletv.UsersDataBase.UsersDatabase;
import com.example.simpletv.UsersDataBase.WatchHistoryDatabase;

public class MyApp extends Application {
    private static MyApp mInstance;
    private SearchDatabase db;
    private UsersDatabase u_db;
    private FavoriteDatabase f_db;
    private WatchHistoryDatabase w_db;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        db = Room.databaseBuilder(this, SearchDatabase.class, "Search").addMigrations().allowMainThreadQueries().build();
        u_db = Room.databaseBuilder(this, UsersDatabase.class, "Users").addMigrations().allowMainThreadQueries().build();
        f_db = Room.databaseBuilder(this, FavoriteDatabase.class, "Favorite").addMigrations().allowMainThreadQueries().build();
        w_db = Room.databaseBuilder(this, WatchHistoryDatabase.class, "WatchHistory").addMigrations().allowMainThreadQueries().build();
    }

    public static MyApp getmInstance() {
        return mInstance;
    }

    public SearchDatabase getDb() {
        return db;
    }

    public UsersDatabase getU_db() {
        return u_db;
    }

    public FavoriteDatabase getF_db() {
        return f_db;
    }

    public WatchHistoryDatabase getW_db() {
        return w_db;
    }
}

