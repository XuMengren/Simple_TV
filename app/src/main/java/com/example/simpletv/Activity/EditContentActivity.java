package com.example.simpletv.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.simpletv.R;
import com.example.simpletv.SearchHistoryDataBase.MyApp;
import com.example.simpletv.Tools.StatusBarUtil;
import com.example.simpletv.UsersDataBase.UsersDao;
import com.example.simpletv.UsersDataBase.Users_person;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditContentActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mExitImg;
    private TextView mMyTitle;
    private EditText mEditContent;
    private Button btn_finish;
    private UsersDao usersDao;
    private int position;
    private CoordinatorLayout coordinator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_content);
        initView();
        getData();
    }
    /****
     *  @作者：XuYunDong
     *  @参数：Null
     *  @描述：获取Intent传的参数
    */
    private void getData() {
        Intent intent = getIntent();
        String str = intent.getStringExtra("str");
        position = intent.getIntExtra("position", 0);
        switch (position) {
            case 1:
                //签名
                mMyTitle.setText("修改我的" + getString(R.string.signature));
                break;
            case 2:
                //昵称
                mMyTitle.setText("修改我的" + getString(R.string.nickname));
                break;
            case 6:
                //邮箱
                mMyTitle.setText("修改我的" + getString(R.string.email));
                break;
        }
        mEditContent.setText(str);
    }

    /****
     *  @作者：XuYunDong
     *  @参数：Null
     *  @描述：initView
    */
    private void initView() {
        mExitImg = findViewById(R.id.exit_img);
        mMyTitle = findViewById(R.id.my_title);
        mEditContent = findViewById(R.id.edit_content);
        btn_finish = findViewById(R.id.btn_finish);
        coordinator = findViewById(R.id.coordinator);
        usersDao = MyApp.getmInstance().getU_db().usersDao();
        mExitImg.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
        //将屏幕状态栏设置成透明，并讲字体颜色设置成黑色
        StatusBarUtil.setTranslucentStatus(EditContentActivity.this);
        StatusBarUtil.setStatusBarColor(this, Color.parseColor("#FAFAFA"));
        StatusBarUtil.setImmersiveStatusBar(this, false);
        StatusBarUtil.setStatusBarFontIconDark(this, StatusBarUtil.TYPE_M);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    /****
     *  @作者：XuYunDong
     *  @参数：[title]
     *  @描述：SnackbarPop自定义SnackBar
    */
    private void SnackbarPop(String title) {
        Snackbar snackbar = Snackbar.make(coordinator, title, Snackbar.LENGTH_LONG)
                .setAction(R.string.confirm, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击确认啥都不做
                    }
                });
        snackbar.getView().setBackground(getResources().getDrawable(R.color.hint_bg));
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.show();

    }

    /****
    * @描述：监听系统返回键
    */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//如果返回键按下
            finish();
            overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
        }
        return super.onKeyDown(keyCode, event);
    }
    @SuppressLint("NewApi")
    /****
    * @描述：onClick,按钮点击事件
    */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit_img:
                finish();
                overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
                break;
            case R.id.btn_finish:
                Users_person users_person = getUsersPerson();
                switch (position) {
                    case 1:
                        //签名
                        users_person.setSignature(mEditContent.getText().toString());
                        usersDao.Update(users_person);
                        finish();
                        overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
                        break;
                    case 2:
                        //昵称
                        users_person.setNickname(mEditContent.getText().toString());
                        usersDao.Update(users_person);
                        finish();
                        overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
                        break;
                    case 6:
                        //邮箱
                        Pattern pattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
                        Matcher mc = pattern.matcher(mEditContent.getText().toString().trim());
                        if(mc.matches()){
                            users_person.setEmail(mEditContent.getText().toString());
                            usersDao.Update(users_person);
                            finish();
                            overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
                        }else{
                            SnackbarPop("请输入正确的邮箱地址");
                        }

                        break;
                }
                break;
        }
    }
    /****
     *  @作者：XuYunDong
     *  @参数：Null
     *  @描述：getUsersPerson获取当前用户的users_person对象
    */
    private Users_person getUsersPerson(){
        SharedPreferences sharedPreferences = getSharedPreferences("LoginSuccess", Context.MODE_PRIVATE);
        Users_person users_person=usersDao.getUserByName(sharedPreferences.getString("Username", ""));
        return users_person;
    }
}
