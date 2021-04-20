package com.example.simpletv.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class Modify_PWDActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mExitImg;
    private TextView mMyTitle;
    private EditText mOldPwd;
    private EditText mNewPwd;
    private EditText mConfirmPwd;
    private TextView mForgetPwd;
    private Button mBtnFinish;
    private UsersDao usersDao;
    private boolean mark = false;
    private boolean symbol = false;
    private CoordinatorLayout coordinator;
    private String username;
    private boolean sign=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        initView();
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
        mOldPwd = findViewById(R.id.old_pwd);
        mNewPwd = findViewById(R.id.new_pwd);
        mConfirmPwd = findViewById(R.id.confirm_pwd);
        mForgetPwd = findViewById(R.id.forget_pwd);
        mBtnFinish = findViewById(R.id.btn_finish);
        coordinator = findViewById(R.id.coordinator);
        usersDao = MyApp.getmInstance().getU_db().usersDao();
        mMyTitle.setText(R.string.modify_pwd);
        mExitImg.setOnClickListener(this);
        mNewPwd.setOnClickListener(this);
        mBtnFinish.setOnClickListener(this);
        mForgetPwd.setOnClickListener(this);
        mBtnFinish.setEnabled(false);
        mBtnFinish.setBackground(getDrawable(R.drawable.edit_button_shape_non));
        SharedPreferences sharedPreferences = getSharedPreferences("LoginSuccess", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("Username", "");

        mOldPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    mark=true;
                    if (mark && symbol&&sign) {
                        mBtnFinish.setEnabled(true);
                        mBtnFinish.setBackground(getDrawable(R.drawable.edit_button_shape));
                    } else {
                        mBtnFinish.setEnabled(false);
                        mBtnFinish.setBackground(getDrawable(R.drawable.edit_button_shape_non));
                    }
                }else{
                    mark=false;
                    mBtnFinish.setEnabled(false);
                    mBtnFinish.setBackground(getDrawable(R.drawable.edit_button_shape_non));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mNewPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mark) {
                    if(s.length()>0){
                        symbol = true;
                        if (mark && symbol&&sign) {
                            mBtnFinish.setEnabled(true);
                            mBtnFinish.setBackground(getDrawable(R.drawable.edit_button_shape));
                        } else {
                            mBtnFinish.setEnabled(false);
                            mBtnFinish.setBackground(getDrawable(R.drawable.edit_button_shape_non));
                        }
                    }else{
                        mBtnFinish.setEnabled(false);
                        mBtnFinish.setBackground(getDrawable(R.drawable.edit_button_shape_non));
                    }
                } else {
                    symbol = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mConfirmPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    sign=true;
                    if (mark && symbol&&sign) {
                        mBtnFinish.setEnabled(true);
                        mBtnFinish.setBackground(getDrawable(R.drawable.edit_button_shape));
                    } else {
                        mBtnFinish.setEnabled(false);
                        mBtnFinish.setBackground(getDrawable(R.drawable.edit_button_shape_non));
                    }
                } else {
                    sign=false;
                    mBtnFinish.setEnabled(false);
                    mBtnFinish.setBackground(getDrawable(R.drawable.edit_button_shape_non));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        StatusBarUtil.setTranslucentStatus(Modify_PWDActivity.this);
        StatusBarUtil.setStatusBarColor(this, Color.parseColor("#FAFAFA"));
        StatusBarUtil.setImmersiveStatusBar(this, false);
        StatusBarUtil.setStatusBarFontIconDark(this, StatusBarUtil.TYPE_M);
    }

    public static boolean checkformat(String str) {
        Pattern p = null;
        String pwd = "^(?![0-9])(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        p = Pattern.compile(pwd);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
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

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit_img:
                finish();
                overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
                break;
            case R.id.btn_finish:
                if (CheckOldPwd(mOldPwd.getText().toString().trim())) {
                    //旧密码正确
                    if(checkformat(mNewPwd.getText().toString())){
                        if (mNewPwd.getText().toString().equals(mConfirmPwd.getText().toString())) {
                            //登录成功将数据写入SharedPreference
                            SharedPreferences sharedPreferences=getSharedPreferences("LoginSuccess",MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("Username","");
                            editor.putBoolean("islogin",false);
                            editor.apply();
                            Users_person userByName = usersDao.getUserByName(username);
                            userByName.setPassword(mConfirmPwd.getText().toString());
                            usersDao.Update(userByName);
                            Intent intent = new Intent(Modify_PWDActivity.this, LoginActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
                            finish();
                        } else {
                            SnackbarPop("两次密码不一致");
                        }
                    }else{
                        SnackbarPop("密码由6-16位数字字母混合,不能全为数字,不能全为字母,首位不能为数字");

                    }


                } else {
                    //旧密码错误
                    SnackbarPop("旧密码错误，请重新输入");

                }
                break;
            case R.id.forget_pwd:
                startActivity(new Intent(Modify_PWDActivity.this,ForgotPassword_Activity.class));
                overridePendingTransition(R.anim.zoomin_right, R.anim.zoomout_left);
                break;
        }
    }

    private boolean CheckOldPwd(String oldPwd) {

        boolean flag = usersDao.Login(username, oldPwd);
        return flag;
    }
}
