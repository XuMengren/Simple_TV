package com.example.simpletv.Activity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
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
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.simpletv.R;
import com.example.simpletv.SearchHistoryDataBase.MyApp;
import com.example.simpletv.Tools.StatusBarUtil;
import com.example.simpletv.Tools.mToast;
import com.example.simpletv.UsersDataBase.UsersDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etUsername;
    private EditText etPassword;
    private Button btGo;
    private CardView cv;
    private FloatingActionButton fab;
    private TextView forget_pwd;
    private ImageView login_exit;
    private ImageView mExitImg;
    private TextView mMyTitle;
    private CoordinatorLayout login_coordinator;
    private UsersDao usersDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        mExitImg = findViewById(R.id.exit_img);
        mMyTitle = findViewById(R.id.my_title);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btGo = findViewById(R.id.bt_go);
        cv = findViewById(R.id.cv);
        fab = findViewById(R.id.fab);
        forget_pwd = findViewById(R.id.forget_pwd);
        login_coordinator = findViewById(R.id.login_coordinator);
        usersDao= MyApp.getmInstance().getU_db().usersDao();
        forget_pwd.setOnClickListener(this);
        btGo.setOnClickListener(this);
        fab.setOnClickListener(this);
        mExitImg.setOnClickListener(this);
        mMyTitle.setVisibility(View.GONE);
        StatusBarUtil.setTranslucentStatus(LoginActivity.this);
        StatusBarUtil.setStatusBarColor(this, Color.parseColor("#FAFAFA"));
        StatusBarUtil.setImmersiveStatusBar(this, false);
        StatusBarUtil.setStatusBarFontIconDark(this, StatusBarUtil.TYPE_M);
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
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setAction("Login");
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
        }
        return super.onKeyDown(keyCode, event);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_go:
                //登录
                if (etUsername.getText().toString().equals("")) {
                    SnackbarPop("请输入账号");
                } else if (etPassword.getText().toString().equals("")) {
                    SnackbarPop("请输入密码");
                } else if (!usersDao.Login(etUsername.getText().toString(),etPassword.getText().toString())) {
                    SnackbarPop("账号或密码错误，请重新输入");
                } else {
                    Explode explode = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        explode = new Explode();
                    }
                    explode.setDuration(500);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setExitTransition(explode);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setEnterTransition(explode);
                    }
                    mToast.single("登录成功",this);
                    //登录成功将数据写入SharedPreference
                    SharedPreferences sharedPreferences=getSharedPreferences("LoginSuccess",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("Username",etUsername.getText().toString());
                    editor.putBoolean("islogin",true);
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setAction("Login");
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
                }
                break;
            case R.id.fab:
                //注册
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setExitTransition(null);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setEnterTransition(null);
                }
                ActivityOptions options = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, fab, fab.getTransitionName());
                }
                startActivity(new Intent(LoginActivity.this, RegistActivity.class), options.toBundle());
                break;
            case R.id.exit_img:
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setAction("Login");
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
                break;
            case R.id.forget_pwd:
                startActivity(new Intent(LoginActivity.this,ForgotPassword_Activity.class));
                overridePendingTransition(R.anim.zoomin_right, R.anim.zoomout_left);
                break;
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void SnackbarPop(String title) {
        Snackbar snackbar = Snackbar.make(login_coordinator, title, Snackbar.LENGTH_LONG)
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

    @SuppressLint("RestrictedApi")
    @Override
    protected void onRestart() {
        super.onRestart();
        fab.setVisibility(View.GONE);
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onResume() {
        super.onResume();
        fab.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
    }
}
