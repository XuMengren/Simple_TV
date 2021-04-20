package com.example.simpletv.UsersDataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WatchHistoryDao {
    //根据Vid字段查询历史电影是否存在
    @Query("SELECT * FROM WatchHistory WHERE history_video_vid=:vid")
    boolean History_Vid(int vid);

    //插入观看历史数据
    @Insert
    void InsertAll(WatchHistory watchHistory);

    //查询所有观看历史
    @Query("Select * from WatchHistory order by w_id desc")
    List<WatchHistory> QueryAll();

    @Query("SELECT * FROM WatchHistory WHERE history_video_vid=:vid")
    WatchHistory QueryByVid(int vid);

    //删除观看记录
    @Delete
    void Delete(WatchHistory watchHistory);
}
