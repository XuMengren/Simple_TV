package com.example.simpletv.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.simpletv.R;
import com.example.simpletv.SearchHistoryDataBase.MyApp;
import com.example.simpletv.Tools.StatusBarUtil;
import com.example.simpletv.Tools.Translucent;
import com.example.simpletv.UsersDataBase.UsersDao;
import com.example.simpletv.UsersDataBase.Users_person;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.makeramen.roundedimageview.RoundedImageView;

public class PersonalInformationActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private String shpf;
    private UsersDao usersDao;
    private FloatingActionButton fab_edit;
    private TextView u_id;
    private TextView u_address;
    private TextView u_sex;
    private TextView u_birth;
    private TextView u_email;
    private TextView signature;
    private TextView u_nickname;
    private RoundedImageView icon_info;
    private ImageView img_bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        initView();
        ReadDatabase();

    }


    @Override
    protected void onResume() {
        super.onResume();
        ReadDatabase();
    }

    /***
     *创建时间：2021/3/10 6:52 PM
     *作者：xyd
     *描述：读取数据库,将数据填入控件中
     *参数：
     *返回值(Y/N):
     */
    @SuppressLint({"NewApi", "UseCompatLoadingForDrawables"})
    private void ReadDatabase() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginSuccess", Context.MODE_PRIVATE);
        shpf = sharedPreferences.getString("Username", "");
        //检查是否有登录过
        if (sharedPreferences.getBoolean("islogin", false)) {
            Users_person users_person = usersDao.QueryAll(shpf);
            //根据种种判断，查看用户有没有修改过个人资料，如果没有修改，填入默认值
            if (users_person.getSignature() == null) {
                signature.setText(R.string.signature_txt);
            } else {
                signature.setText(users_person.getSignature());
            }
            if (users_person.getNickname() == null) {
                u_nickname.setText(users_person.getAccountNumber());
            } else {
                u_nickname.setText(users_person.getNickname());
            }
            if (users_person.getAddress() == null) {
                u_address.setText(R.string.default_address);
            } else {
                u_address.setText(users_person.getAddress());
            }
            if (users_person.getSex() == null) {
                u_sex.setText(R.string.default_sex);
            } else {
                u_sex.setText(users_person.getSex());
            }
            if (users_person.getDateOfBirth() == null) {
                u_birth.setText(R.string.default_birth);
            } else {
                u_birth.setText(users_person.getDateOfBirth());
            }
            if (users_person.getEmail() == null) {
                u_email.setText(getString(R.string.input_email));
            } else {
                u_email.setText(users_person.getEmail());
            }
            if(users_person.getHeadPortrait()==null){
                Glide.with(this).load(getDrawable(R.drawable.nologin)).into(icon_info);
            }else{
                Glide.with(this).load(users_person.getHeadPortrait()).into(icon_info);
            }
            if(users_person.getBackground_pic()==null){
//                Glide.with(this).load(getDrawable(R.drawable.img)).into(img_bg);
                img_bg.setImageResource(R.drawable.img);
            }else{
//                Glide.with(this).load(users_person.getBackground_pic()).into(img_bg);
                Bitmap bitmap = BitmapFactory.decodeByteArray(users_person.getBackground_pic(), 0, users_person.getBackground_pic().length);
                img_bg.setImageBitmap(bitmap);
            }
            u_id.setText(users_person.getAccountNumber());
        }
    }

    /***
     *创建时间：2021/3/10 6:52 PM
     *作者：xyd
     *描述：
     *参数：
     *返回值(Y/N):
     */
    private void initView() {
        Translucent.setTranslucentStatus(this);
        toolbar = findViewById(R.id.toolbar);
        fab_edit = findViewById(R.id.fab_edit);
        u_id = findViewById(R.id.u_id);
        u_address = findViewById(R.id.u_address);
        u_sex = findViewById(R.id.u_sex);
        u_birth = findViewById(R.id.u_birth);
        u_email = findViewById(R.id.u_email);
        signature = findViewById(R.id.signature);
        u_nickname = findViewById(R.id.info_nickname);
        icon_info = findViewById(R.id.icon_info);
        img_bg = findViewById(R.id.img_bg);

        usersDao = MyApp.getmInstance().getU_db().usersDao();
        //给Toolbar设置返回键
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab_edit.setOnClickListener(this);

        //将屏幕状态栏设置成透明，并讲字体颜色设置成黑色
        StatusBarUtil.setTranslucentStatus(PersonalInformationActivity.this);
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
            finish();
            overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
        }
        return super.onKeyDown(keyCode, event);
    }
    //activity类中的方法
    //添加点击返回箭头事件
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_edit:
                startActivity(new Intent(PersonalInformationActivity.this,EditPersonalInfoActivity.class));
                overridePendingTransition(R.anim.zoomin_right, R.anim.zoomout_left);
                break;
        }
    }
}