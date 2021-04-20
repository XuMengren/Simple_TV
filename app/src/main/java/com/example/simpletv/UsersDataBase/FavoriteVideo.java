package com.example.simpletv.UsersDataBase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/***
 *创建时间：2021/3/8 3:33 PM
 *作者：xyd
 *描述：FavoriteVideo实体类
 *参数：
 *返回值(Y/N):
 */
@Entity(tableName = "FavoriteVideo")
public class FavoriteVideo {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int f_id;
    @ColumnInfo(name="video_name")
    private String video_name;
    @ColumnInfo(name="video_pic")
    private String video_pic;
    @ColumnInfo(name="video_vid")
    private int video_vid;

    public int getF_id() {
        return f_id;
    }

    public void setF_id(int f_id) {
        this.f_id = f_id;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public String getVideo_pic() {
        return video_pic;
    }

    public void setVideo_pic(String video_pic) {
        this.video_pic = video_pic;
    }

    public int getVideo_vid() {
        return video_vid;
    }

    public void setVideo_vid(int video_vid) {
        this.video_vid = video_vid;
    }
}
