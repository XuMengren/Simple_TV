package com.example.simpletv.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import com.example.simpletv.R;
import com.example.simpletv.SearchHistoryDataBase.MyApp;
import com.example.simpletv.Tools.StatusBarUtil;
import com.example.simpletv.UsersDataBase.UsersDao;
import com.example.simpletv.UsersDataBase.Users_person;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPassword3_Activity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mExitImg;
    private TextView mMyTitle;
    private EditText mEtNewPwd;
    private EditText mEtConfirmPwd;
    private Button mBtnFinish;
    private boolean flag=false;
    private String name;
    private UsersDao usersDao;
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
            Intent intent = new Intent(ForgotPassword3_Activity.this, ForgotPassword2_Activity.class);
            Bundle bundle=new Bundle();
            bundle.putString("Uname",name);
            intent.putExtra("F1",bundle);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password3_);
        GetData();
        initView();
    }

    private void GetData() {
        Intent intent = getIntent();
        Bundle f2 = intent.getBundleExtra("F2");
        name = f2.getString("name");
    }

    @SuppressLint({"NewApi", "UseCompatLoadingForDrawables"})
    private void initView() {
        mExitImg = findViewById(R.id.exit_img);
        mMyTitle = findViewById(R.id.my_title);
        mEtNewPwd = findViewById(R.id.et_newPwd);
        mEtConfirmPwd = findViewById(R.id.et_confirm_pwd);
        mBtnFinish = findViewById(R.id.btn_finish);
        mMyTitle.setText(R.string.modify_pwd);
        usersDao= MyApp.getmInstance().getU_db().usersDao();
        mBtnFinish.setEnabled(false);
        mBtnFinish.setBackground(getDrawable(R.drawable.edit_button_shape_non));
        mExitImg.setOnClickListener(this);
        mBtnFinish.setOnClickListener(this);
        mEtNewPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    flag=true;
                }else{
                    flag=false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEtConfirmPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    if(flag){
                        mBtnFinish.setEnabled(true);
                        mBtnFinish.setBackground(getDrawable(R.drawable.edit_button_shape));
                    }else{
                        mBtnFinish.setEnabled(false);
                        mBtnFinish.setBackground(getDrawable(R.drawable.edit_button_shape_non));
                    }
                }else{
                    mBtnFinish.setEnabled(false);
                    mBtnFinish.setBackground(getDrawable(R.drawable.edit_button_shape_non));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        StatusBarUtil.setTranslucentStatus(ForgotPassword3_Activity.this);
        StatusBarUtil.setStatusBarColor(this, Color.parseColor("#FAFAFA"));
        StatusBarUtil.setImmersiveStatusBar(this, false);
        StatusBarUtil.setStatusBarFontIconDark(this, StatusBarUtil.TYPE_M);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exit_img:
                Intent intent1 = new Intent(ForgotPassword3_Activity.this, ForgotPassword2_Activity.class);
                Bundle bundle=new Bundle();
                bundle.putString("Uname",name);
                intent1.putExtra("F1",bundle);
                startActivity(intent1);
                finish();
                overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
                break;
            case R.id.btn_finish:
                if(checkformat(mEtNewPwd.getText().toString().trim())){
                    if(mEtNewPwd.getText().toString().equals(mEtConfirmPwd.getText().toString())){
                        //登录成功将数据写入SharedPreference
                        SharedPreferences sharedPreferences=getSharedPreferences("LoginSuccess",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("Username","");
                        editor.putBoolean("islogin",false);
                        editor.apply();
                        Users_person userByName = usersDao.getUserByName(name);
                        userByName.setPassword(mEtConfirmPwd.getText().toString());
                        usersDao.Update(userByName);
                        Intent intent = new Intent(ForgotPassword3_Activity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
                    }
                    else{
                        Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "密码由6-16位数字字母混合,不能全为数字,不能全为字母,首位不能为数字", Toast.LENGTH_SHORT).show();
                }
                
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public static boolean checkformat(String str) {
        Pattern p = null;
        String pwd = "^(?![0-9])(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        p = Pattern.compile(pwd);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}