package com.example.simpletv.SearchHistoryDataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/***
 *创建时间：2021/1/31 7:15 PM
 *作者：xyd
 *描述：搜索历史接口,用于增、删、查搜索历史
 *参数：
 *返回值(Y/N):
 */
@Dao
public interface SearchDao {
    @Query("Select  * from Search order by _Id desc limit 6")
    List<Search> GetSearch();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertAll(Search search);

    //删除某一个
    @Delete
    void Delete(Search search);

    @Query("select * from search where names=:word")
    boolean CheckName(String word);

    //根据字段查询
    @Query("SELECT * FROM Search WHERE names= :name")
    Search getSearchByName(String name);

    //删除所有
    @Query("Delete From Search")
    void DeleteAll();

}
