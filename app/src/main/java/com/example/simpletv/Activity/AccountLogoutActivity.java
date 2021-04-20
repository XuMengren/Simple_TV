package com.example.simpletv.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simpletv.R;
import com.example.simpletv.SearchHistoryDataBase.MyApp;
import com.example.simpletv.Tools.StatusBarUtil;
import com.example.simpletv.Tools.mToast;
import com.example.simpletv.UsersDataBase.UsersDao;
import com.example.simpletv.UsersDataBase.Users_person;

public class AccountLogoutActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private ImageView mExitImg;
    private TextView mMyTitle;
    private TextView mAccountNick;
    private Button mBtnSkip;
    private CheckBox mAgreeCheck;
    private UsersDao usersDao;
    private TextView account;
    private Users_person users_person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_logout);
        initView();
        getDatabaseData();
    }

    @SuppressLint("SetTextI18n")
    private void getDatabaseData() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginSuccess", Context.MODE_PRIVATE);
        users_person = usersDao.QueryAll(sharedPreferences.getString("Username", ""));
        account.setText("-账号："+ users_person.getAccountNumber());
        mAccountNick.setText("-昵称："+ users_person.getNickname());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//如果返回键按下
            finish();
            overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
        }
        return super.onKeyDown(keyCode, event);
    }

    @SuppressLint({"NewApi", "UseCompatLoadingForDrawables"})
    private void initView() {
        mExitImg = findViewById(R.id.exit_img);
        mMyTitle = findViewById(R.id.my_title);
        mAccountNick = findViewById(R.id.account_nick);
        account = findViewById(R.id.account);
        mBtnSkip = findViewById(R.id.btn_skip);
        mAgreeCheck = findViewById(R.id.agree_check);
        mMyTitle.setText(R.string.logout_title);
        usersDao = MyApp.getmInstance().getU_db().usersDao();
        mBtnSkip.setEnabled(false);
        mBtnSkip.setBackground(getDrawable(R.drawable.edit_button_shape_non));
        mExitImg.setOnClickListener(this);
        mBtnSkip.setOnClickListener(this);
        mAgreeCheck.setOnCheckedChangeListener(this);

        StatusBarUtil.setTranslucentStatus(AccountLogoutActivity.this);
        StatusBarUtil.setStatusBarColor(this, Color.parseColor("#FAFAFA"));
        StatusBarUtil.setImmersiveStatusBar(this, false);
        StatusBarUtil.setStatusBarFontIconDark(this, StatusBarUtil.TYPE_M);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit_img:
                finish();
                overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
                break;
            case R.id.btn_skip:
                SharedPreferences sharedPreferences = getSharedPreferences("LoginSuccess", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Username", "");
                editor.putBoolean("islogin", false);
                editor.apply();
                usersDao.DeleteForAccount(users_person.getAccountNumber());
                mToast.single("注销成功",this);
                Intent intent = new Intent(AccountLogoutActivity.this, MainActivity.class);
                intent.setAction("Login");
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
                break;
        }
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "NewApi"})
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            mBtnSkip.setEnabled(true);
            mBtnSkip.setBackground(getDrawable(R.drawable.edit_button_shape));
        } else {
            mBtnSkip.setEnabled(false);
            mBtnSkip.setBackground(getDrawable(R.drawable.edit_button_shape_non));
        }
    }
}