package com.example.simpletv.UsersDataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {WatchHistory.class}, version = 1, exportSchema = false)
public abstract class WatchHistoryDatabase extends RoomDatabase {
    public abstract WatchHistoryDao watchHistoryDao();
}
