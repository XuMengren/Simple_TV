package com.example.simpletv.UsersDataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface FavoriteDao {
    //根据Vid字段查询收藏电影是否存在
    @Query("SELECT * FROM FavoriteVideo WHERE video_vid=:vid")
    boolean ISVid(int vid);

    @Query("select * from FavoriteVideo order by f_id desc")
    List<FavoriteVideo> QueryAll();
    //删除所有数据
    @Query("delete from favoritevideo")
    void DeleteAll();
    //插入收藏电影数据
    @Insert
    void InsertAll(FavoriteVideo favoriteVideo);

    //根据ID查询出收藏电影的数据
    @Query("select * from FavoriteVideo where video_vid=:id")
    FavoriteVideo SearchForID(int id);
    //删除收藏电影数据
    @Delete
    void DELETE(FavoriteVideo favoriteVideo);
}
