package com.example.simpletv.Activity.WelcomePage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.simpletv.Activity.CacheActivity;
import com.example.simpletv.Activity.MainActivity;
import com.example.simpletv.R;
import com.example.simpletv.Tools.StatusBarUtil;

import static java.lang.Thread.sleep;

public class WelcomeActivity extends AppCompatActivity {
    private static int DELAY_MILLIS=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        StatusBarUtil.setTranslucentStatus(WelcomeActivity.this);
        StatusBarUtil.setStatusBarColor(this, Color.parseColor("#FFFFFF"));
        StatusBarUtil.setImmersiveStatusBar(this, false);
        StatusBarUtil.setStatusBarFontIconDark(this, StatusBarUtil.TYPE_M);
        new Handler(new Handler.Callback() {
            //处理接收到的消息的方法
            @Override
            public boolean handleMessage(Message arg0) {
                //实现页面跳转
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                intent.setAction("welcome");
                startActivity(intent);
                overridePendingTransition(R.anim.welcome_open_anim,0);
                return false;
            }
        }).sendEmptyMessageDelayed(0, DELAY_MILLIS); //表示延时三秒进行任务的执行
    }
}