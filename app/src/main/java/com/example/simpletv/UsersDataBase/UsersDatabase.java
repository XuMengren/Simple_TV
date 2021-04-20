package com.example.simpletv.UsersDataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.simpletv.SearchHistoryDataBase.Search;
import com.example.simpletv.SearchHistoryDataBase.SearchDao;

/***
 *创建时间：2021/1/31 8:45 PM
 *作者：xyd
 *描述：通过继承改写room的database，把Users.class和UsersDao.class联系在一起，组成一个完整的数据库。
 *参数：
 *返回值(Y/N):
 */
@Database(entities = {Users_person.class},version=1,exportSchema = false)
public abstract class UsersDatabase extends RoomDatabase {
    public abstract UsersDao usersDao();
}

