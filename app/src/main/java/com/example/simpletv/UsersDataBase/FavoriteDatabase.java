package com.example.simpletv.UsersDataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteVideo.class}, version = 1, exportSchema = false)
public abstract class FavoriteDatabase extends RoomDatabase {
    public abstract FavoriteDao favoriteDao();
}
