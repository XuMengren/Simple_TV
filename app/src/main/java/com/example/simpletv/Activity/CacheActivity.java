package com.example.simpletv.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simpletv.Fragment.CachePageFragment;
import com.example.simpletv.Fragment.FinishPageFragment;
import com.example.simpletv.MAdapter.CacheFragmentPagerAdapter;
import com.example.simpletv.R;
import com.example.simpletv.Tools.StatusBarUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class CacheActivity extends AppCompatActivity implements TabLayout.BaseOnTabSelectedListener, View.OnClickListener {
    private ImageView mExitImg;
    private TextView mMyTitle;
    private TabLayout mCacheTablayout;
    private ViewPager mCacheViewpager;
    private List<String> titles=new ArrayList<>();
    private List<Fragment> fragments=new ArrayList<>();
    private CachePageFragment cachePageFragment=new CachePageFragment();
    private FinishPageFragment finishPageFragment=new FinishPageFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache);
        setDate();
        initView();
        BindViewPager();
    }

    private void setDate() {
        titles.add("已完成");
        titles.add("缓存中");

        fragments.add(finishPageFragment);
        fragments.add(cachePageFragment);
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
    private void BindViewPager() {
        CacheFragmentPagerAdapter adapter=new CacheFragmentPagerAdapter(getSupportFragmentManager(),fragments,titles);
        mCacheViewpager.setAdapter(adapter);
        mCacheTablayout.setupWithViewPager(mCacheViewpager);
        mCacheTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void initView() {
        mExitImg = findViewById(R.id.exit_img);
        mMyTitle = findViewById(R.id.my_title);
        mCacheTablayout = findViewById(R.id.cache_tablayout);
        mCacheViewpager = findViewById(R.id.cache_viewpager);
        mMyTitle.setText(R.string.loading);
        mCacheTablayout.setOnTabSelectedListener(this);
        mExitImg.setOnClickListener(this);
        StatusBarUtil.setTranslucentStatus(CacheActivity.this);
        StatusBarUtil.setStatusBarColor(this, Color.parseColor("#FAFAFA"));
        StatusBarUtil.setImmersiveStatusBar(this, false);
        StatusBarUtil.setStatusBarFontIconDark(this, StatusBarUtil.TYPE_M);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        //选中Tab时字体变大
        TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.cache_tab_text, null);
        textView.setTextSize(20);
        textView.setText(tab.getText().toString());
        tab.setCustomView(textView);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        //当取消选中是恢复默认
        tab.setCustomView(null);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.exit_img){
            finish();
            overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
        }
    }
}