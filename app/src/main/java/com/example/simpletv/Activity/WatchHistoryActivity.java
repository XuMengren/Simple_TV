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

import com.example.simpletv.MAdapter.WatchHistoryAdapter;
import com.example.simpletv.Interface.RecyclerCallBack;
import com.example.simpletv.R;
import com.example.simpletv.SearchHistoryDataBase.MyApp;
import com.example.simpletv.Tools.StatusBarUtil;
import com.example.simpletv.UsersDataBase.WatchHistory;
import com.example.simpletv.UsersDataBase.WatchHistoryDao;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

public class WatchHistoryActivity extends AppCompatActivity implements View.OnClickListener, RecyclerCallBack {
    private ImageView mExitImg;
    private TextView mMyTitle;
    private SwipeMenuRecyclerView mWatchHistoryRecycler;
    private WatchHistoryDao watchHistoryDao;
    private WatchHistoryAdapter adapter;
    private TextView text_hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_history);
        initView();
        initAdapter();
    }

    private void initAdapter() {
        if(watchHistoryDao.QueryAll().size()==0){
            text_hint.setVisibility(View.VISIBLE);
            mWatchHistoryRecycler.setVisibility(View.GONE);
        }else{
            text_hint.setVisibility(View.GONE);
            adapter = new WatchHistoryAdapter(this);
            RecyclerView.LayoutManager manager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
            mWatchHistoryRecycler.setLayoutManager(manager);
            mWatchHistoryRecycler.setAdapter(adapter);
            adapter.setWatchHistories(watchHistoryDao.QueryAll());
            adapter.notifyDataSetChanged();
        }


    }

    /***
     *???????????????2021/4/1 23:00 PM
     *?????????xyd
     *??????????????????????????????
     *?????????
     *?????????(Y/N):
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//?????????????????????
            finish();
            overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
        }
        return super.onKeyDown(keyCode, event);
    }
    private void initView() {

        mExitImg = findViewById(R.id.exit_img);
        mMyTitle = findViewById(R.id.my_title);
        text_hint = findViewById(R.id.text_hint);
        mWatchHistoryRecycler = findViewById(R.id.watch_history_recycler);
        mWatchHistoryRecycler.setSwipeMenuCreator(swipeMenuCreator);
        watchHistoryDao= MyApp.getmInstance().getW_db().watchHistoryDao();
        mMyTitle.setText(R.string.watch_history);
        mExitImg.setOnClickListener(this);
        mWatchHistoryRecycler.setSwipeMenuItemClickListener(mMenuItemClickListener);
        //?????????????????????????????????????????????????????????????????????
        StatusBarUtil.setTranslucentStatus(WatchHistoryActivity.this);
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
        *???????????????2021/4/5 12:06 PM
        *?????????xyd
        *?????????????????????RecyclerView
        *?????????
        *?????????(Y/N):
    */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            DisplayMetrics dm=new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            SwipeMenuItem deleteItem = new SwipeMenuItem(WatchHistoryActivity.this)
                    .setBackgroundColor(getResources().getColor(R.color.red_bg)) // ????????????
                    .setText("??????") // ?????????
                    .setTextColor(Color.WHITE) // ???????????????
                    .setTextSize(16) // ???????????????
                    .setWidth(dm.heightPixels/8) // ???
                    .setHeight((int) getResources().getDimension(R.dimen.movie_width)); //??????MATCH_PARENT??????Item???????????????????????? ?????????????????????
            swipeRightMenu.addMenuItem(deleteItem);// ???????????????????????????????????????
        }
    };
    /***
        *???????????????2021/4/5 3:27 PM
        *?????????xyd
        *???????????????RecyclerView??????????????????
        *?????????
        *?????????(Y/N):
    */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            // ??????????????????????????????????????????????????????Item???????????????????????????
            menuBridge.closeMenu();
            //???menuBridge???????????????????????????????????????item???position (menuBridge.getAdapterPosition())
//            Toast.makeText(WatchHistoryActivity.this, ""+watchHistoryDao.QueryAll().get(menuBridge.getPosition()).getW_id(), Toast.LENGTH_SHORT).show();
            WatchHistory watchHistory = watchHistoryDao.QueryByVid(watchHistoryDao.QueryAll().get(menuBridge.getAdapterPosition()).getHistory_video_vid());
            watchHistoryDao.Delete(watchHistory);

            //??????????????????????????????
            adapter.setWatchHistories(watchHistoryDao.QueryAll());
            adapter.notifyDataSetChanged();
            if(watchHistoryDao.QueryAll().size()==0){
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
     *???????????????2021/4/1 7:46 PM
     *?????????xyd
     *???????????????????????????
     *?????????
     *?????????(Y/N):
     */
    private void JumpVideo(int position, String title) {
        Intent intent = new Intent(WatchHistoryActivity.this, PlayerActivity.class);
        Bundle bundle=new Bundle();
        bundle.putInt("vid",position);
        bundle.putString("title",title);
        intent.putExtra("vid",bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.zoomin_right, R.anim.zoomout_left);
    }
}