package com.example.simpletv.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simpletv.MAdapter.FavoritesAdapter;
import com.example.simpletv.Interface.RecyclerCallBack;
import com.example.simpletv.R;
import com.example.simpletv.SearchHistoryDataBase.MyApp;
import com.example.simpletv.Tools.StatusBarUtil;
import com.example.simpletv.UsersDataBase.FavoriteDao;
import com.example.simpletv.UsersDataBase.FavoriteVideo;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

public class FavoritesActivity extends AppCompatActivity implements RecyclerCallBack, View.OnClickListener {
    private ImageView mExitImg;
    private TextView mMyTitle;
    private SwipeMenuRecyclerView mWatchHistoryRecycler;
    private FavoriteDao favoriteDao;
    private FavoritesAdapter adapter;
    private TextView text_hint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        initView();
        initAdapter();

    }
    private void initAdapter() {
        if(favoriteDao.QueryAll().size()==0){
            text_hint.setVisibility(View.VISIBLE);
            mWatchHistoryRecycler.setVisibility(View.GONE);
        }else{
            text_hint.setVisibility(View.GONE);
            adapter = new FavoritesAdapter(this);
            RecyclerView.LayoutManager manager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
            mWatchHistoryRecycler.setLayoutManager(manager);
            mWatchHistoryRecycler.setAdapter(adapter);
            adapter.setFavoriteVideo(favoriteDao.QueryAll());
            adapter.notifyDataSetChanged();
        }


    }

    /***
     *创建时间：2021/4/1 23:00 PM
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
        text_hint = findViewById(R.id.text_hint);
        text_hint.setText(R.string.shoucang_text);
        mWatchHistoryRecycler = findViewById(R.id.watch_history_recycler);
        mWatchHistoryRecycler.setSwipeMenuCreator(swipeMenuCreator);
        favoriteDao= MyApp.getmInstance().getF_db().favoriteDao();
        mMyTitle.setText(R.string.shoucang);
        mExitImg.setOnClickListener(this);
        mWatchHistoryRecycler.setSwipeMenuItemClickListener(mMenuItemClickListener);
        //将屏幕状态栏设置成透明，并讲字体颜色设置成黑色
        StatusBarUtil.setTranslucentStatus(FavoritesActivity.this);
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
        }
    }
    /***
     *创建时间：2021/4/5 12:06 PM
     *作者：xyd
     *描述：测滑删除RecyclerView
     *参数：
     *返回值(Y/N):
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            DisplayMetrics dm=new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            SwipeMenuItem deleteItem = new SwipeMenuItem(FavoritesActivity.this)
                    .setBackgroundColor(getResources().getColor(R.color.red_bg)) // 背景颜色
                    .setText("删除") // 文字。
                    .setTextColor(Color.WHITE) // 文字颜色。
                    .setTextSize(16) // 文字大小。
                    .setWidth(dm.heightPixels/8) // 宽
                    .setHeight((int) getResources().getDimension(R.dimen.movie_width)); //高（MATCH_PARENT意为Item多高侧滑菜单多高 （推荐使用））
            swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
        }
    };
    /***
     *创建时间：2021/4/5 3:27 PM
     *作者：xyd
     *描述：测滑RecyclerView删除点击事件
     *参数：
     *返回值(Y/N):
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();
            //在menuBridge中我们可以得到侧滑的这一项item的position (menuBridge.getAdapterPosition())
//            Toast.makeText(WatchHistoryActivity.this, ""+watchHistoryDao.QueryAll().get(menuBridge.getPosition()).getW_id(), Toast.LENGTH_SHORT).show();
            FavoriteVideo favoriteVideo = favoriteDao.SearchForID(favoriteDao.QueryAll().get(menuBridge.getAdapterPosition()).getVideo_vid());
            favoriteDao.DELETE(favoriteVideo);

            //删除完成后刷新适配器
            adapter.setFavoriteVideo(favoriteDao.QueryAll());
            adapter.notifyDataSetChanged();
            if(favoriteDao.QueryAll().size()==0){
                text_hint.setVisibility(View.VISIBLE);
                mWatchHistoryRecycler.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void callback(int flag, String movieName) {
        JumpVideo(flag,movieName);
    }
    /***
     *创建时间：2021/4/1 7:46 PM
     *作者：xyd
     *描述：播放视频跳转
     *参数：
     *返回值(Y/N):
     */
    private void JumpVideo(int position, String title) {
        Intent intent = new Intent(FavoritesActivity.this, PlayerActivity.class);
        Bundle bundle=new Bundle();
        bundle.putInt("vid",position);
        bundle.putString("title",title);
        intent.putExtra("vid",bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.zoomin_right, R.anim.zoomout_left);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}