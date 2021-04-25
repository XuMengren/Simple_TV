package com.example.simpletv.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.simpletv.MAdapter.VideoRecyAdapter;
import com.example.simpletv.NetworkRequestInterface.Api;
import com.example.simpletv.Interface.BottomNavCallBack;
import com.example.simpletv.Interface.JsonDataCallBack;
import com.example.simpletv.Interface.JsonURLCallBack;
import com.example.simpletv.Interface.MovieCallBack;
import com.example.simpletv.Interface.MovieCallBack_Video;
import com.example.simpletv.Interface.NetWorkCallBack;
import com.example.simpletv.Interface.PopuClickCallBack;
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
import com.example.simpletv.SearchHistoryDataBase.MyApp;
import com.example.simpletv.Tools.DialogUtil;
import com.example.simpletv.Tools.RoundImageView;
import com.example.simpletv.Tools.mToast;
import com.example.simpletv.UsersDataBase.FavoriteDao;
import com.example.simpletv.UsersDataBase.FavoriteVideo;
import com.example.simpletv.UsersDataBase.WatchHistory;
import com.example.simpletv.UsersDataBase.WatchHistoryDao;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class PlayerActivity extends AppCompatActivity implements NetWorkCallBack, JsonURLCallBack, View.OnClickListener, RequestFailedCallBack, JsonDataCallBack, BottomNavCallBack, MovieCallBack, PopuClickCallBack, MovieCallBack_Video {
    private JzvdStd mVideoPlayer;
    private RecyclerView mVideoRecycler;
    private int vid;
    private NetWork_Request netWork_request=new NetWork_Request(this);
    private com.example.simpletv.NetworkRequestInterface.mHandler mHandler=new mHandler( this, this,this);
    private String title;
    private VideoRecyAdapter adapter;
    private List<String> movieUrl_m3u8=new ArrayList<>();
    private List<String> movieUrl_other=new ArrayList<>();
    private List<String> movieList_m3u8=new ArrayList<>();
    private List<String> movieList_other=new ArrayList<>();
    private PopupWindow popupWindow;
    private ImageView mPopuFinish;
    private TextView mMovieName;
    private TextView mMovieRegion;
    private TextView mMovieYear;
    private TextView mMovieYuyan;
    private TextView mMovieAuthor;
    private TextView mMovieYanyuan;
    private RoundImageView mMovieImg;
    private TextView mMovieIntroduction;
    private ImageView cancel_video;
    private List<MovieInfo.DataBean> movieInfo;
    private WatchHistoryDao watchHistoryDao;
    private FavoriteDao favoriteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        getData();
        initView();
        initAdapter();
        GetHttp(0,0,vid);
    }
    /***
        *创建时间：2021/4/1 10:10 PM
        *作者：xyd
        *描述：保存观看历史
        *参数：
        *返回值(Y/N):
    */
    private void SaveWatchHistory() {
        if ( !watchHistoryDao.History_Vid(vid)){
            WatchHistory watchHistory=new WatchHistory();
            //获取当前时间
            @SuppressLint("SimpleDateFormat") SimpleDateFormat year = new SimpleDateFormat("yyyy");//获取年份
            @SuppressLint("SimpleDateFormat") SimpleDateFormat month = new SimpleDateFormat("MM");//获取月份
            @SuppressLint("SimpleDateFormat") SimpleDateFormat day=new SimpleDateFormat("dd");//获取日
            String years=year.format(new Date());
            String months=month.format(new Date()).substring(0,1);
            String days=day.format(new Date()).substring(0,1);
            String confirmMonth;
            String confirmDays;
            if(months.equals("0")){
                confirmMonth=month.format(new Date()).substring(1,2);
            }else{
                confirmMonth=month.format(new Date());
            }
            if(days.equals("0")){
                confirmDays=day.format(new Date()).substring(1,2);
            }else{
                confirmDays=day.format(new Date());
            }
            watchHistory.setHistory_pic(movieInfo.get(0).getV_pic());
            watchHistory.setHistory_video_name(movieInfo.get(0).getV_name());
            watchHistory.setHistory_video_vid(movieInfo.get(0).getV_id());
            watchHistory.setHistory_date(years+"年"+confirmMonth+"月"+confirmDays+"日");
            watchHistoryDao.InsertAll(watchHistory);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(movieUrl_m3u8!=null){
            movieUrl_m3u8.clear();
            movieInfo.clear();
            movieList_m3u8.clear();
            movieList_other.clear();
            movieUrl_other.clear();
            Jzvd.releaseAllVideos();

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
    /***
        *创建时间：2021/4/1 6:11 PM
        *作者：xyd
        *描述：设置m3u8播放剧集
        *参数：
        *返回值(Y/N):
    */
    private void setVideo_m3u8(int i) {
//        Log.i("xydxyd", "setVideo_m3u8: "+movieInfo.get(0).getV_pic());
        mVideoPlayer.setUp(movieUrl_m3u8.get(i),"\u3000\u3000"+title+" "+movieList_m3u8.get(i));
        Glide.with(this).load(movieInfo.get(0).getV_pic()).into(mVideoPlayer.posterImageView);
        mVideoPlayer.startVideo();
    }
    /***
     *创建时间：2021/4/1 6:11 PM
     *作者：xyd
     *描述：设置other播放剧集
     *参数：
     *返回值(Y/N):
     */
    private void setVideo_other(int i) {
        mVideoPlayer.setUp(movieUrl_other.get(i),"\u3000\u3000"+title+" "+movieList_other.get(i));
        mVideoPlayer.startVideo();
    }

    private void initAdapter() {
        adapter =  new VideoRecyAdapter(this,this,this,this);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        mVideoRecycler.setLayoutManager(manager);
        mVideoRecycler.setAdapter(adapter);
    }

    private void GetHttp(int flag,int tid,int vid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                switch (flag){
                    case 0:
                        netWork_request.GetHttp(Api.HOST+Api.Movie_info+vid,9);
                        netWork_request.GetHttp(Api.HOST+Api.Movie_URL+vid,8);
                        break;
                    case 1:
                        netWork_request.GetHttp(Api.HOST+Api.ByTypeReturnVideo+tid,1);
                        break;
                }

            }
        }).start();
    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("vid");
        vid = bundle.getInt("vid");
        title = bundle.getString("title");
    }

    private void initView() {
        mVideoPlayer = findViewById(R.id.video_player);
        mVideoRecycler = findViewById(R.id.video_recycler);
        cancel_video = findViewById(R.id.cancel_video);
        watchHistoryDao=MyApp.getmInstance().getW_db().watchHistoryDao();
        favoriteDao=MyApp.getmInstance().getF_db().favoriteDao();
        cancel_video.setOnClickListener(this);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);  //设置横屏
//            Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;  //横向
            cancel_video.setVisibility(View.GONE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏
//            Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;  //纵向
            cancel_video.setVisibility(View.VISIBLE);
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if(Jzvd.backPress()){
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    public void success(String result, int flag) {
        Message message = new Message();
        message.obj=result;
        message.what=flag;
        mHandler.sendMessage(message);
    }

    @Override
    public void error(Exception result, int flag) {
        Message message = new Message();
        message.obj=result;
        message.what=flag;
        mHandler.sendMessage(message);
    }
    /***
        *创建时间：2021/4/1 6:12 PM
        *作者：xyd
        *描述：请求回调接口视频播放链接
        *参数：
        *返回值(Y/N):
    */
    @Override
    public void URL(@NotNull MovieURL.DataBean movieurl) {
        movieUrl_m3u8=new ArrayList<>();
        movieList_m3u8=new ArrayList<>();
        movieUrl_other=new ArrayList<>();
        movieList_other=new ArrayList<>();
        for (int i = 0; i < movieurl.getM3u8List().size(); i++) {
            movieUrl_m3u8.add(movieurl.getM3u8List().get(i).getUrl());
            movieList_m3u8.add(movieurl.getM3u8List().get(i).getName());
        }
        for (int i = 0; i < movieurl.getOtherList().size(); i++) {
            movieUrl_other.add(movieurl.getOtherList().get(i).getUrl());
            movieList_other.add(movieurl.getOtherList().get(i).getName());
        }
        adapter.setMovieUrl(movieurl);
        adapter.notifyDataSetChanged();
        setVideo_m3u8(0);
    }

    @Override
    public void onClick(@NotNull View v) {
        switch (v.getId()){
            case R.id.cancel_video:
                finish();
                overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
                break;
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
        adapter.setMovieBean(movieBean);
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
    /***
        *创建时间：2021/4/1 6:13 PM
        *作者：xyd
        *描述：请求回调电影信息
        *参数：
        *返回值(Y/N):
    */
    @Override
    public void MovieInfo(List<MovieInfo.DataBean> movieInfoBean) {
        movieInfo = movieInfoBean;
        adapter.setMovieInfo(movieInfoBean);
        adapter.notifyDataSetChanged();
        SaveWatchHistory();
        //请求到数据直接播放视频
        GetHttp(1,movieInfoBean.get(0).getTid(),0);
    }

    /***
        *创建时间：2021/4/1 6:13 PM
        *作者：xyd
        *描述：功能按钮点击事件
        *参数：
        *返回值(Y/N):
    */
    @Override
    public void navCallBack(int flag) {
        SharedPreferences sharedPreferences=getSharedPreferences("LoginSuccess",MODE_PRIVATE);
        boolean islogin = sharedPreferences.getBoolean("islogin", false);
        switch (flag){
            //追剧
            case 0:
                if(islogin){
                    collect(1);
                }else{
                    BulletFrame();
                }
                break;
                //缓存
            case 1:
                if(islogin){
                    mToast.single("功能还未开发，敬请期待",this);
//                    Aria.download(this).load(movieUrl_m3u8.get(0)).setDownloadPath(Environment.getExternalStorageDirectory().getPath()+title+".m3u8").add();//文件保存路径
                }else{
                    BulletFrame();

                }
                break;
                //收藏
            case 2:
                if(islogin){
                    collect(2);
                }else{
                    BulletFrame();
                }
                break;
        }
    }
    /***
        *创建时间：2021/4/1 10:27 PM
        *作者：xyd
        *描述：收藏电影
        *参数：
        *返回值(Y/N):
    */
    private void collect(int flag) {
        if(favoriteDao.ISVid(movieInfo.get(0).getV_id())){
            //如果该视频已被收藏，再次点击即可取消收藏
            FavoriteVideo favoriteVideo = favoriteDao.SearchForID(movieInfo.get(0).getV_id());
            favoriteDao.DELETE(favoriteVideo);
            switch (flag){
                case 1:
                    mToast.single("以从追剧列表中移除",this);
                    break;
                case 2:
                    mToast.single("以从收藏列表中移除",this);
                    break;
            }
        }else{
            FavoriteVideo favoriteVideo=new FavoriteVideo();
            favoriteVideo.setVideo_name(movieInfo.get(0).getV_name());
            favoriteVideo.setVideo_pic(movieInfo.get(0).getV_pic());
            favoriteVideo.setVideo_vid(movieInfo.get(0).getV_id());
            favoriteDao.InsertAll(favoriteVideo);
            switch (flag){
                case 1:
                    mToast.single("已加入追剧列表，在<我的追剧>中查看",this);
                    break;
                case 2:
                    mToast.single("已加入收藏列表，在<我的收藏>中查看",this);
                    break;
            }
        }
    }

    /***
        *创建时间：2021/4/1 10:04 PM
        *作者：xyd
        *描述：消息弹框
        *参数：
        *返回值(Y/N):
    */
    private void BulletFrame() {
        final DialogUtil dialogUtil = new DialogUtil(PlayerActivity.this,this);
        dialogUtil.dialog(getString(R.string.login_txt),getString(R.string.cancel),getString(R.string.gologin));
        dialogUtil.setOnItemClickListener(new DialogUtil.OnItemClickListener(){
            @Override
            public void onItemCancelClick() {
                //取消
            }

            @Override
            public void onItemConfirmClick() {
                JumpLogin();
            }
        });
    }
    /***
        *创建时间：2021/4/1 10:02 PM
        *作者：xyd
        *描述：跳转Login界面方法
        *参数：
        *返回值(Y/N):
    */
    private void JumpLogin(){
        startActivity(new Intent(PlayerActivity.this,LoginActivity.class));
        overridePendingTransition(R.anim.zoomin_right, R.anim.zoomout_left);
    }
    /***
        *创建时间：2021/4/1 6:13 PM
        *作者：xyd
        *描述：播放剧集点击事件
        *参数：
        *返回值(Y/N):
    */
    @Override
    public void movieCallback(int flag, @NotNull String s) {
        switch (s){
            case "m3u8":
                setVideo_m3u8(flag);
                break;
            case "other":
                setVideo_other(flag);
                break;
        }
    }

    /***
        *创建时间：2021/4/1 6:14 PM
        *作者：xyd
        *描述：电影介绍窗口
        *参数：
        *返回值(Y/N):
    */
    @SuppressLint("SetTextI18n")
    @Override
    public void PopuCallBack(View callbackview, @NotNull List<MovieInfo.DataBean> movieInfo) {
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        View view= LayoutInflater.from(this).inflate(R.layout.detail_popu,null);
        mPopuFinish = view.findViewById(R.id.popu_finish);
        mMovieName = view.findViewById(R.id.movie_name);
        mMovieRegion = view.findViewById(R.id.movie_region);
        mMovieYear = view.findViewById(R.id.movie_year);
        mMovieYuyan = view.findViewById(R.id.movie_yuyan);
        mMovieAuthor = view.findViewById(R.id.movie_author);
        mMovieYanyuan = view.findViewById(R.id.movie_yanyuan);
        mMovieImg = view.findViewById(R.id.movie_img);
        mMovieIntroduction = view.findViewById(R.id.movieIntroduction);

        mMovieName.setText("剧名:"+movieInfo.get(0).getV_name());
        mMovieRegion.setText("地区:"+movieInfo.get(0).getV_publisharea());
        mMovieYear.setText("年份:"+movieInfo.get(0).getV_publishyear());
        mMovieYuyan.setText("语言:"+movieInfo.get(0).getV_lang());
        mMovieAuthor.setText("导演:"+movieInfo.get(0).getV_director());
        mMovieYanyuan.setText("演员:"+movieInfo.get(0).getV_actor());
        Glide.with(this).load(movieInfo.get(0).getV_pic()).into(mMovieImg);
        mMovieIntroduction.setText("\u3000\u3000"+movieInfo.get(0).getBody());
        mPopuFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, dm.heightPixels-mVideoPlayer.getHeight()-60);//获取屏幕高度减去控件高度
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setAnimationStyle(R.style.take_photo_anim);
        popupWindow.showAtLocation(mVideoPlayer, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void Video_Callback(int flag, String s) {
        vid=flag;
        title=s;
        GetHttp(0,0,vid);
    }

}