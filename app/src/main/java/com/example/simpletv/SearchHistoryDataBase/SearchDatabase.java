package com.example.simpletv.SearchHistoryDataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;
/***
    *创建时间：2021/1/31 8:45 PM
    *作者：xyd
    *描述：通过继承改写room的database，把Search.class和SearchDao.class联系在一起，组成一个完整的数据库。
    *参数：
    *返回值(Y/N):
*/
@Database(entities = {Search.class},version=1,exportSchema = false)
public abstract class SearchDatabase extends RoomDatabase {
    public abstract SearchDao searchDao();
}
