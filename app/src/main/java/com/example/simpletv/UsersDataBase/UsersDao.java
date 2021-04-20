package com.example.simpletv.UsersDataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/***
    *创建时间：2021/3/8 3:52 PM
    *作者：xyd
    *描述：用户信息接口，用于增删改查用户信息
    *参数：
    *返回值(Y/N):
*/
@Dao
public interface UsersDao {
    //查询当前账号所有数据
    @Query("Select * from Users_person where accountNumber=:number")
    Users_person QueryAll(String number);
    //修改当前数据
    @Update
    void Update(Users_person usersPerson);
    //添加数据
    @Insert
    void InsertAll(Users_person usersPerson);
    //检查用户名是否被注册
    @Query("select * from Users_person where accountNumber=:number")
    boolean CheckName(String number);
    //检测用户是否登录成功
    @Query("select * from Users_person where accountNumber=:name and password=:pwd")
    boolean Login(String name,String pwd);

    //根据字段查询
    @Query("SELECT * FROM Users_person WHERE accountNumber= :name")
    Users_person getUserByName(String name);

    //注销账号
    @Query("Delete from Users_person where accountNumber=:number")
    void DeleteForAccount(String number);
}
