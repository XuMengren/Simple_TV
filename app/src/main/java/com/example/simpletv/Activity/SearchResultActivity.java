package com.example.simpletv.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simpletv.Activity.WelcomePage.WelcomeActivity;
import com.example.simpletv.MAdapter.SearchResultAdapter;
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

import java.util.List;

public class SearchResultActivity extends AppCompatActivity implements View.OnClickListener, NetWorkCallBack, JsonDataCallBack, RequestFailedCallBack, MovieCallBack, JsonURLCallBack {

    private NetWork_Request netWork_request = new NetWork_Request(this);
    private mHandler handler = new mHandler(this, this,  this);
    private final static String INTENT_SEARCH_RESULT = "query_value";
    private static final String TAG = "SearchResultEx";
    private TextView mSearchName;
    private TextView mSearchResultBack;
    private ListView mSearchResultListview;
    private SearchResultAdapter adapter;
    private LinearLayout notfind_ll;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        initView();
        initAdapter();
        getHttp(getIntent().getStringExtra(INTENT_SEARCH_RESULT));
    }

    /***
     *???????????????2021/2/1 6:53 PM
     *?????????xyd
     *?????????????????????
     *?????????
     *?????????(Y/N):
     */
    private void getHttp(final String name) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                netWork_request.GetHttp(Api.HOST + Api.SearchResult + name, 4);
            }
        }).start();
    }


    /***
     *???????????????2021/2/1 5:06 PM
     *?????????xyd
     *????????????????????????
     *?????????
     *?????????(Y/N):
     */
    private void initAdapter() {
        adapter = new SearchResultAdapter(this);
        mSearchResultListview.setAdapter(adapter);
    }

    /***
     *???????????????2021/2/1 5:06 PM
     *?????????xyd
     *????????????????????????
     *?????????
     *?????????(Y/N):
     */
    private void initView() {
        mSearchName = findViewById(R.id.search_name);
        mSearchResultBack = findViewById(R.id.search_result_back);
        mSearchResultListview = findViewById(R.id.search_result_listview);
        mSearchName.setText(getIntent().getStringExtra(INTENT_SEARCH_RESULT));
        mSearchResultListview.setOverScrollMode( View.OVER_SCROLL_NEVER );//?????????????????????
        notfind_ll = findViewById(R.id.notfind_ll);
        progressBar = findViewById(R.id.progress);
        mSearchResultBack.setOnClickListener(this);
        StatusBarUtil.setTranslucentStatus(SearchResultActivity.this);
        StatusBarUtil.setStatusBarColor(this, Color.parseColor("#FAFAFA"));
        StatusBarUtil.setImmersiveStatusBar(this, false);
        StatusBarUtil.setStatusBarFontIconDark(this, StatusBarUtil.TYPE_M);
    }

    /***
     *???????????????2021/2/1 7:34 PM
     *?????????xyd
     *?????????????????????
     *?????????
     *?????????(Y/N):
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_result_back:
                finish();
                overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
                break;
        }
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
    @Override
    public void success(String result, int flag) {
        if(result.equals("{\"code\":0,\"data\":[],\"msg\":\"????????????\"}")){
            notfind_ll.setVisibility(View.VISIBLE);
            mSearchResultListview.setVisibility(View.GONE);
        }
        progressBar.setVisibility(View.GONE);
        //????????????
        Message message = new Message();
        message.obj = result;
        message.what = flag;
        handler.sendMessage(message);
    }

    @Override
    public void error(Exception result, int flag) {
        //????????????
        Message message = new Message();
        message.obj = result;
        message.what = flag;
        handler.sendMessage(message);
    }

    @Override
    public void BannerJsonData(List<BannerBean.DataBean> bannerBean) {

    }

    /***
     *???????????????2021/2/1 7:34 PM
     *?????????xyd
     *??????????????????????????????????????????
     *?????????
     *?????????(Y/N):
     */
    @Override
    public void MovieJsonData(List<MovieBean.DataBean> movieBean) {
        adapter.setmKeyWord(getIntent().getStringExtra(INTENT_SEARCH_RESULT));
        adapter.setMovieData(movieBean);
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
    public void requestError(String result) {

    }

    /***
     *???????????????2021/2/1 7:33 PM
     *?????????xyd
     *???????????????????????????????????????
     *?????????
     *?????????(Y/N):
     */
    @Override
    public void movieCallback(int flag, String s) {
//        mToast.single(s + "---" + flag, this);
        JumpVideo(flag,s);
    }

    @Override
    public void URL(MovieURL.DataBean movieurl) {
    }
    private void JumpVideo(int position, String title) {
        Intent intent = new Intent(SearchResultActivity.this, PlayerActivity.class);
        Bundle bundle=new Bundle();
        bundle.putInt("vid",position);
        bundle.putString("title",title);
        intent.putExtra("vid",bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.zoomin_right, R.anim.zoomout_left);
    }
}
