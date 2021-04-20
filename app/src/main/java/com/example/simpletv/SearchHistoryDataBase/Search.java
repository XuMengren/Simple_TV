package com.example.simpletv.SearchHistoryDataBase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
/***
    *创建时间：2021/1/31 8:48 PM
    *作者：xyd
    *描述：Search实体类
    *参数：
    *返回值(Y/N):
*/
@Entity(tableName = "Search")
public class Search {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int _Id;
    @ColumnInfo(name="names")
    private String names;

    public int get_Id() {
        return _Id;
    }

    public void set_Id(int _Id) {
        this._Id = _Id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }
}
