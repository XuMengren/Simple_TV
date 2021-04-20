package com.example.simpletv.UsersDataBase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/***
 *创建时间：2021/3/8 3:33 PM
 *作者：xyd
 *描述：WatchHistory实体类
 *参数：
 *返回值(Y/N):
 */
@Entity(tableName = "WatchHistory")
public class WatchHistory {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int w_id;
    @ColumnInfo(name="history_pic")
    private String history_pic;
    @ColumnInfo(name="history_video_vid")
    private int  history_video_vid;
    @ColumnInfo(name="history_video_name")
    private String history_video_name;
    @ColumnInfo(name = "history_date")
    private String history_date;

    public String getHistory_date() {
        return history_date;
    }

    public void setHistory_date(String history_date) {
        this.history_date = history_date;
    }

    public int getW_id() {
        return w_id;
    }

    public void setW_id(int w_id) {
        this.w_id = w_id;
    }

    public String getHistory_pic() {
        return history_pic;
    }

    public void setHistory_pic(String history_pic) {
        this.history_pic = history_pic;
    }

    public int getHistory_video_vid() {
        return history_video_vid;
    }

    public void setHistory_video_vid(int history_video_vid) {
        this.history_video_vid = history_video_vid;
    }

    public String getHistory_video_name() {
        return history_video_name;
    }

    public void setHistory_video_name(String history_video_name) {
        this.history_video_name = history_video_name;
    }
}
