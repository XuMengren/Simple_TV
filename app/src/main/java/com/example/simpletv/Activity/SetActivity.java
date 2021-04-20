package com.example.simpletv.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simpletv.MAdapter.SetListAdapter;
import com.example.simpletv.Interface.MovieCallBack;
import com.example.simpletv.R;
import com.example.simpletv.Tools.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class SetActivity extends AppCompatActivity implements View.OnClickListener, MovieCallBack {
    private SmartRefreshLayout mSetRefresh;
    private ListView mSetList;
    private ImageView mExitImg;
    private TextView mMyTitle;
    private SetListAdapter setListAdapter = new SetListAdapter(this);
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        setData();
        initView();

    }

    private void setData() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginSuccess", MODE_PRIVATE);
        boolean islogin = sharedPreferences.getBoolean("islogin", false);
        if (islogin) {
            list.add("账号注销");
            list.add("密码管理");
            list.add("隐私政策");
            list.add("退出登录");
        }else{
            list.add("隐私政策");
        }
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

    private void initView() {
        mExitImg = findViewById(R.id.exit_img);
        mMyTitle = findViewById(R.id.my_title);
        mSetRefresh = findViewById(R.id.set_refresh);
        mSetList = findViewById(R.id.set_list);
        mSetRefresh.setOverScrollMode(View.OVER_SCROLL_NEVER);//取消拖动水波纹
        mSetList.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mSetRefresh.setEnableLoadMore(false);//关闭上拉加载
        mSetRefresh.setEnableRefresh(false);//关闭下拉刷新
        mSetRefresh.setEnableOverScrollDrag(true);
        mSetRefresh.setEnableOverScrollBounce(true);
        mExitImg.setOnClickListener(this);
        mMyTitle.setText(R.string.set);
        setListAdapter.setList(list);
        mSetList.setAdapter(setListAdapter);

        StatusBarUtil.setTranslucentStatus(SetActivity.this);
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
        }
    }

    @Override
    public void movieCallback(int flag, String s) {
//        Toast.makeText(this, "" + flag + "---" + s, Toast.LENGTH_SHORT).show();
        switch (s) {
            case "退出登录":
                SharedPreferences sharedPreferences = getSharedPreferences("LoginSuccess", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Username", "");
                editor.putBoolean("islogin", false);
                editor.apply();
                finish();
                overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
                break;
            case "隐私政策":
                startActivity(new Intent(SetActivity.this, PrivacyPolicyActivity.class));
                overridePendingTransition(R.anim.zoomin_right, R.anim.zoomout_left);
                break;

            case "密码管理":
                startActivity(new Intent(SetActivity.this,Modify_PWDActivity.class));
                overridePendingTransition(R.anim.zoomin_right, R.anim.zoomout_left);

                break;

            case "账号注销":
                startActivity(new Intent(SetActivity.this,AccountLogoutActivity.class));
                overridePendingTransition(R.anim.zoomin_right, R.anim.zoomout_left);

                break;

        }
    }
}
