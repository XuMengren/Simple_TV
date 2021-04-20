package com.example.simpletv.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.example.simpletv.R;
import com.example.simpletv.Fragment.HomeFragment;
import com.example.simpletv.Fragment.MineFragment;
import com.example.simpletv.Fragment.PartFragment;
import com.example.simpletv.Tools.StatusBarUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottom_nva;
    private ViewPager viewPager;
    private FragmentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        GetAction();
    }

    private void GetAction() {
        String action = getIntent().getAction();
        if (action.equals("Login")) {
            viewPager.setCurrentItem(2);
        }else{
            viewPager.setCurrentItem(0);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent();
// 为Intent设置Action、Category属性
        intent.setAction(Intent.ACTION_MAIN);// "android.intent.action.MAIN"
        intent.addCategory(Intent.CATEGORY_HOME); //"android.intent.category.HOME"
        startActivity(intent);
        return super.onKeyDown(keyCode, event);
    }

    private void initView() {
        StatusBarUtil.setTranslucentStatus(MainActivity.this);
        StatusBarUtil.setStatusBarColor(this, Color.parseColor("#FAFAFA"));
        StatusBarUtil.setImmersiveStatusBar(this, false);
        StatusBarUtil.setStatusBarFontIconDark(this, StatusBarUtil.TYPE_M);
        /**
         * 初始化底部导航栏
         */
        bottom_nva = findViewById(R.id.bottom_nva);
        //设置导航切换监听
        bottom_nva.setOnNavigationItemSelectedListener(changeFragment);
        /**
         * viewpager初始化
         */
        viewPager = findViewById(R.id.main_viewpager);
        //去掉滑动到末尾的水纹
        viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        /**
         * ViewPager的监听
         */
        setViewPagerListener();
        /**
         * fragment相关
         */
        initFragment();

        viewPager.setAdapter(mPagerAdapter);   //设置适配器
        viewPager.setOffscreenPageLimit(3); //预加载所有页


    }

    private void initFragment() {
        //底部导航栏有几项就有几个Fragment
        final ArrayList<Fragment> fgLists = new ArrayList<>(5);
        fgLists.add(new HomeFragment());
        fgLists.add(new PartFragment());
        fgLists.add(new MineFragment());

        //设置适配器用于装载Fragment,ViewPager的好朋友
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fgLists.get(position);  //得到Fragment
            }

            @Override
            public int getCount() {
                return fgLists.size();  //得到数量
            }
        };
    }

    private void setViewPagerListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //滑动页面后做的事，这里与BottomNavigationView结合，使其与正确page对应
                bottom_nva.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //判断选择的菜单,点击哪个就设置到对应的Fragment
    private BottomNavigationView.OnNavigationItemSelectedListener changeFragment = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home: {
                    viewPager.setCurrentItem(0);
                    return true;
                }
                case R.id.part: {
                    viewPager.setCurrentItem(1);
                    return true;
                }
                case R.id.mine: {
                    viewPager.setCurrentItem(2);
                    return true;
                }
            }
            return false;
        }
    };
}