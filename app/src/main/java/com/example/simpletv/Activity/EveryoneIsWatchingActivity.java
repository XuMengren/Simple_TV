package com.example.simpletv.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletv.MAdapter.EveryWatchAdapter;
import com.example.simpletv.NetworkRequestInterface.Api;
import com.example.simpletv.Interface.JsonDataCallBack;
import com.example.simpletv.Interface.JsonURLCallBack;
import com.example.simpletv.Interface.MovieCallBack;
import com.example.simpletv.Interface.NetWorkCallBack;
import com.example.simpletv.Interface.RequestFailedCallBack;
import com.example.simpletv.NetworkRequestInterface.JavaBean.BannerBean;
import com.example.simpletv.NetworkRequestInterface.JavaBean.FuzzySearchBean;
import com.example.simpletv.NetworkRequestInterface.JavaBean.MovieBean;
import com.example.simpletv.NetworkRequestInterface.JavaBean.MovieInfo;
import com.example.simpletv.NetworkRequestInterface.JavaBean.MovieTypeBean;
import com.example.simpletv.NetworkRequestInterface.JavaBean.MovieURL;
import com.example.simpletv.NetworkRequestInterface.JavaBean.RecommendBean;
import com.example.simpletv.NetworkRequestInterface.NetWork_Request;
import com.example.simpletv.NetworkRequestInterface.mHandler;
import com.example.simpletv.R;
import com.example.simpletv.Tools.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EveryoneIsWatchingActivity extends AppCompatActivity implements View.OnClickListener, OnLoadMoreListener, RequestFailedCallBack, JsonDataCallBack, NetWorkCallBack, MovieCallBack, JsonURLCallBack {
    private ImageView mExitImg;
    private TextView mMyTitle;
    private RecyclerView recycler;
    private SmartRefreshLayout refreshlayout;
    private mHandler mHandler = new mHandler(this, this,  this);
    private NetWork_Request netWork_request = new NetWork_Request(this);
    private static int index = 1;
    private final static int REFRESH_TIME = 1500;
    private EveryWatchAdapter adapter;
    private final static String PIC_KEY = "img";
    private final static String STRING = "name";
    private final static String V_ID = "vid";
    private ArrayList<Map<String, String>> movieList = new ArrayList<>();
    ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.everyone_is_watching);
        initView();
        initAdatper();
        getHttp(1, index);
    }

    /***
     *???????????????2021/3/1 2:04 PM
     *?????????xyd
     *???????????????????????????
     *?????????
     *?????????(Y/N):
     */
    private void initAdatper() {
        adapter = new EveryWatchAdapter(this);
        GridLayoutManager manager = new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);
    }

    /***
     *???????????????2021/3/1 1:43 PM
     *?????????xyd
     *???????????????????????????
     *?????????
     *?????????(Y/N):
     */
    private void getHttp(final int flag, final int index) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                switch (flag) {
                    case 1:
                        Log.i("xydxyd", "run: "+index);
                        netWork_request.GetHttp(Api.HOST + Api.Home_swipe_left_and_right + index, 1);
                        break;
                }
            }
        }).start();
    }

    /***
     *???????????????2021/2/25 3:38 PM
     *?????????xyd
     *?????????????????????ID
     *?????????
     *?????????(Y/N):
     */
    private void initView() {
        mExitImg = findViewById(R.id.exit_img);
        mMyTitle = findViewById(R.id.my_title);
        recycler = findViewById(R.id.watching_recycler);
        refreshlayout = findViewById(R.id.everyone_refresh);
        refreshlayout.setOverScrollMode(View.OVER_SCROLL_NEVER);
        refreshlayout.setOnLoadMoreListener(this);//????????????????????????
        refreshlayout.setEnableLoadMore(true);//??????????????????
        refreshlayout.setEnableRefresh(false);//??????????????????
        refreshlayout.setEnableOverScrollDrag(true);
        refreshlayout.setEnableOverScrollBounce(true);
        refreshlayout.setEnableFooterTranslationContent(true);//??????Footer????????????????????????
        refreshlayout.setEnableAutoLoadMore(false);//????????????????????????????????????????????????????????????
        mMyTitle.setText(R.string.every_watching);
        mExitImg.setOnClickListener(this);

        StatusBarUtil.setTranslucentStatus(EveryoneIsWatchingActivity.this);
        StatusBarUtil.setStatusBarColor(this, Color.parseColor("#FAFAFA"));
        StatusBarUtil.setImmersiveStatusBar(this, false);
        StatusBarUtil.setStatusBarFontIconDark(this, StatusBarUtil.TYPE_M);
    }


    /***
     *???????????????2021/2/25 3:41 PM
     *?????????xyd
     *?????????????????????
     *?????????
     *?????????(Y/N):
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit_img:
                index=1;
                finish();
                overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
                break;
        }
    }
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
    }
    /***
     *???????????????2021/3/1 1:46 PM
     *?????????xyd
     *?????????????????????
     *?????????
     *?????????(Y/N):
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        //????????????9???????????????1??????
        if (index < 10) {
            index++;
            getHttp(1, index);
            refreshlayout.finishLoadMore(REFRESH_TIME);
        } else {
            //????????????????????????????????????????????????
            refreshlayout.finishLoadMoreWithNoMoreData();
        }
    }

    @Override
    public void requestError(String result) {

    }

    @Override
    public void BannerJsonData(List<BannerBean.DataBean> bannerBean) {

    }

    @Override
    public void MovieJsonData(List<MovieBean.DataBean> movieBean) {
        Map<String, String> map;
        for (int i = 0; i < movieBean.size(); i++) {
            map = new HashMap<>();
            map.put(PIC_KEY, movieBean.get(i).getV_pic());
            map.put(STRING, movieBean.get(i).getV_name());
            map.put(V_ID, String.valueOf(movieBean.get(i).getV_id()));
            movieList.add(map);
        }
        adapter.setMovieList(movieList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void RecommendData(List<RecommendBean.DataBean> recommendBean) {

    }

    @Override
    public void FuzzySearchData(List<FuzzySearchBean.DataBean> fuzzyBean) {

    }

    @Override
    public void MovieTypeData(List<MovieTypeBean.DataBean> movieTypeBean) {

    }

    @Override
    public void MovieInfo(List<MovieInfo.DataBean> movieInfoBean) {

    }

    @Override
    public void success(String result, int flag) {
        Message message = new Message();
        message.what = flag;
        message.obj = result;
        mHandler.sendMessage(message);
    }

    @Override
    public void error(Exception result, int flag) {
        Message message = new Message();
        message.what = flag;
        message.obj = result;
        mHandler.sendMessage(message);
    }

    @Override
    public void movieCallback(int flag, String s) {
        JumpVideo(flag,s);
    }
    /***
     *???????????????2021/4/1 7:46 PM
     *?????????xyd
     *???????????????????????????
     *?????????
     *?????????(Y/N):
     */
    private void JumpVideo(int position, String title) {
        Intent intent = new Intent(EveryoneIsWatchingActivity.this, PlayerActivity.class);
        Bundle bundle=new Bundle();
        bundle.putInt("vid",position);
        bundle.putString("title",title);
        intent.putExtra("vid",bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.zoomin_right, R.anim.zoomout_left);
    }
    @Override
    public void URL(MovieURL.DataBean movieurl) {

    }
    /***
     *???????????????2021/4/1 4:09 PM
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
}
