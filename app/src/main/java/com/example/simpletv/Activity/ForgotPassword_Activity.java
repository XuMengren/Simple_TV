package com.example.simpletv.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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

public class ForgotPassword_Activity extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    private ImageView mExitImg;
    private TextView mMyTitle;
    private EditText mEtUname;
    private Button mBtnSkip;
    private UsersDao usersDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();

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
            overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
        }
        return super.onKeyDown(keyCode, event);
    }
    @SuppressLint({"NewApi", "UseCompatLoadingForDrawables"})
    private void initView() {
        mExitImg = findViewById(R.id.exit_img);
        mMyTitle = findViewById(R.id.my_title);
        mEtUname = findViewById(R.id.et_uname);
        mBtnSkip = findViewById(R.id.btn_skip);
        mMyTitle.setText(R.string.find_pwd);
        mBtnSkip.setEnabled(false);
        mBtnSkip.setBackground(getDrawable(R.drawable.edit_button_shape_non));
        usersDao= MyApp.getmInstance().getU_db().usersDao();
        mExitImg.setOnClickListener(this);
        mBtnSkip.setOnClickListener(this);
        mEtUname.addTextChangedListener(this);
        StatusBarUtil.setTranslucentStatus(ForgotPassword_Activity.this);
        StatusBarUtil.setStatusBarColor(this, Color.parseColor("#FAFAFA"));
        StatusBarUtil.setImmersiveStatusBar(this, false);
        StatusBarUtil.setStatusBarFontIconDark(this, StatusBarUtil.TYPE_M);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exit_img:
                finish();
                overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
                break;
            case R.id.btn_skip:
                if(usersDao.CheckName(mEtUname.getText().toString().trim())){
                    Intent intent = new Intent(ForgotPassword_Activity.this,ForgotPassword2_Activity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("Uname",mEtUname.getText().toString().trim());
                    intent.putExtra("F1",bundle);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.zoomin_right, R.anim.zoomout_left);
                }else{
                    Toast.makeText(this, "未找到该用户", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    @SuppressLint({"NewApi", "UseCompatLoadingForDrawables"})
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //根据用户所输入到EditText中的字符长度判断用户是否输入了值，如果大于0按钮可用状态，否则按钮不可用状态
        if(s.length()>0){
            mBtnSkip.setEnabled(true);
            mBtnSkip.setBackground(getDrawable(R.drawable.edit_button_shape));
        }else{
            mBtnSkip.setEnabled(false);
            mBtnSkip.setBackground(getDrawable(R.drawable.edit_button_shape_non));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}