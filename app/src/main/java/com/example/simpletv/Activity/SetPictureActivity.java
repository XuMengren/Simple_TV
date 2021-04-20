package com.example.simpletv.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.simpletv.R;
import com.example.simpletv.SearchHistoryDataBase.MyApp;
import com.example.simpletv.UsersDataBase.UsersDao;
import com.example.simpletv.UsersDataBase.Users_person;

public class SetPictureActivity extends AppCompatActivity {

    private String action;
    private UsersDao usersDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setpicture);
        Intent intent = getIntent();
        action = intent.getAction();
        initView();
    }
    /***
     *创建时间：2021/4/1 4:09 PM
     *作者：xyd
     *描述：监听系统返回键
     *参数：
     *返回值(Y/N):
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//如果返回键按下
            finish();
//            overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
        }
        return super.onKeyDown(keyCode, event);
    }
    @SuppressLint("NewApi")
    private void initView() {
        ImageView imageView=findViewById(R.id.see_img);
        LinearLayout ll=findViewById(R.id.see_ll);
        usersDao= MyApp.getmInstance().getU_db().usersDao();
        SharedPreferences sharedPreferences = getSharedPreferences("LoginSuccess", Context.MODE_PRIVATE);
        Users_person users_person=usersDao.QueryAll(sharedPreferences.getString("Username", ""));
        if(action.equals("one")){
            if(users_person.getHeadPortrait()==null){
                Glide.with(this).load(getDrawable(R.drawable.nologin)).into(imageView);
            }else{
                Glide.with(this).load(users_person.getHeadPortrait()).into(imageView);
            }
        } else{
            if(users_person.getBackground_pic()==null){
//                Glide.with(this).load(getDrawable(R.drawable.img)).into(img_bg);
                imageView.setImageResource(R.drawable.img);
            }else{
//                Glide.with(this).load(users_person.getBackground_pic()).into(img_bg);
                Bitmap bitmap = BitmapFactory.decodeByteArray(users_person.getBackground_pic(), 0, users_person.getBackground_pic().length);
                imageView.setImageBitmap(bitmap);
            }
        }
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
