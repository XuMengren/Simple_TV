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

public class ForgotPassword2_Activity extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    private ImageView mExitImg;
    private TextView mMyTitle;
    private TextView mQuestionTxt;
    private EditText mEtAnswer;
    private Button mBtnSkip;
    private UsersDao usersDao;
    private String uname;
    private Users_person users_person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password2_);
        GetData();
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
            startActivity(new Intent(ForgotPassword2_Activity.this,ForgotPassword_Activity.class));
            finish();
            overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
        }
        return super.onKeyDown(keyCode, event);
    }
    private void GetData() {
        Intent intent = getIntent();
        Bundle f1 = intent.getBundleExtra("F1");
        uname = f1.getString("Uname");
    }

    @SuppressLint({"NewApi", "UseCompatLoadingForDrawables"})
    private void initView() {
        mExitImg = findViewById(R.id.exit_img);
        mMyTitle = findViewById(R.id.my_title);
        mQuestionTxt = findViewById(R.id.question_txt);
        mEtAnswer = findViewById(R.id.et_answer);
        mBtnSkip = findViewById(R.id.btn_skip);
        usersDao= MyApp.getmInstance().getU_db().usersDao();
        mBtnSkip.setEnabled(false);
        mBtnSkip.setBackground(getDrawable(R.drawable.edit_button_shape_non));
        mMyTitle.setText("密保问题");
        mExitImg.setOnClickListener(this);
        mBtnSkip.setOnClickListener(this);
        mEtAnswer.addTextChangedListener(this);
        StatusBarUtil.setTranslucentStatus(ForgotPassword2_Activity.this);
        StatusBarUtil.setStatusBarColor(this, Color.parseColor("#FAFAFA"));
        StatusBarUtil.setImmersiveStatusBar(this, false);
        StatusBarUtil.setStatusBarFontIconDark(this, StatusBarUtil.TYPE_M);
        users_person = usersDao.getUserByName(uname);
        mQuestionTxt.setText(users_person.getSecurityQuestion());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exit_img:
                startActivity(new Intent(ForgotPassword2_Activity.this,ForgotPassword_Activity.class));
                finish();
                overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
                break;
            case R.id.btn_skip:
                if(mEtAnswer.getText().toString().equals(users_person.getAnswer())){
                    Intent intent = new Intent(ForgotPassword2_Activity.this, ForgotPassword3_Activity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("name",uname);
                    intent.putExtra("F2",bundle);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.zoomin_right, R.anim.zoomout_left);
                }else{
                    Toast.makeText(this, "答案错误", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @SuppressLint({"NewApi", "UseCompatLoadingForDrawables"})
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
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