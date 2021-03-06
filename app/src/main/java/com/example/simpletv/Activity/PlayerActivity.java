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
        *???????????????2021/4/1 10:10 PM
        *?????????xyd
        *???????????????????????????
        *?????????
        *?????????(Y/N):
    */
    private void SaveWatchHistory() {
        if ( !watchHistoryDao.History_Vid(vid)){
            WatchHistory watchHistory=new WatchHistory();
            //??????????????????
            @SuppressLint("SimpleDateFormat") SimpleDateFormat year = new SimpleDateFormat("yyyy");//????????????
            @SuppressLint("SimpleDateFormat") SimpleDateFormat month = new SimpleDateFormat("MM");//????????????
            @SuppressLint("SimpleDateFormat") SimpleDateFormat day=new SimpleDateFormat("dd");//?????????
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
            watchHistory.setHistory_date(years+"???"+confirmMonth+"???"+confirmDays+"???");
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
    /***
        *???????????????2021/4/1 6:11 PM
        *?????????xyd
        *???????????????m3u8????????????
        *?????????
        *?????????(Y/N):
    */
    private void setVideo_m3u8(int i) {
//        Log.i("xydxyd", "setVideo_m3u8: "+movieInfo.get(0).getV_pic());
        mVideoPlayer.setUp(movieUrl_m3u8.get(i),"\u3000\u3000"+title+" "+movieList_m3u8.get(i));
        Glide.with(this).load(movieInfo.get(0).getV_pic()).into(mVideoPlayer.posterImageView);
        mVideoPlayer.startVideo();
    }
    /***
     *???????????????2021/4/1 6:11 PM
     *?????????xyd
     *???????????????other????????????
     *?????????
     *?????????(Y/N):
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
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);  //????????????
//            Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;  //??????
            cancel_video.setVisibility(View.GONE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//????????????
//            Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;  //??????
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
        *???????????????2021/4/1 6:12 PM
        *?????????xyd
        *?????????????????????????????????????????????
        *?????????
        *?????????(Y/N):
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
        *???????????????2021/4/1 6:13 PM
        *?????????xyd
        *?????????????????????????????????
        *?????????
        *?????????(Y/N):
    */
    @Override
    public void MovieInfo(List<MovieInfo.DataBean> movieInfoBean) {
        movieInfo = movieInfoBean;
        adapter.setMovieInfo(movieInfoBean);
        adapter.notifyDataSetChanged();
        SaveWatchHistory();
        //?????????????????????????????????
        GetHttp(1,movieInfoBean.get(0).getTid(),0);
    }

    /***
        *???????????????2021/4/1 6:13 PM
        *?????????xyd
        *?????????????????????????????????
        *?????????
        *?????????(Y/N):
    */
    @Override
    public void navCallBack(int flag) {
        SharedPreferences sharedPreferences=getSharedPreferences("LoginSuccess",MODE_PRIVATE);
        boolean islogin = sharedPreferences.getBoolean("islogin", false);
        switch (flag){
            //??????
            case 0:
                if(islogin){
                    collect(1);
                }else{
                    BulletFrame();
                }
                break;
                //??????
            case 1:
                if(islogin){
                    mToast.single("?????????????????????????????????",this);
//                    Aria.download(this).load(movieUrl_m3u8.get(0)).setDownloadPath(Environment.getExternalStorageDirectory().getPath()+title+".m3u8").add();//??????????????????
                }else{
                    BulletFrame();

                }
                break;
                //??????
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
        *???????????????2021/4/1 10:27 PM
        *?????????xyd
        *?????????????????????
        *?????????
        *?????????(Y/N):
    */
    private void collect(int flag) {
        if(favoriteDao.ISVid(movieInfo.get(0).getV_id())){
            //????????????????????????????????????????????????????????????
            FavoriteVideo favoriteVideo = favoriteDao.SearchForID(movieInfo.get(0).getV_id());
            favoriteDao.DELETE(favoriteVideo);
            switch (flag){
                case 1:
                    mToast.single("???????????????????????????",this);
                    break;
                case 2:
                    mToast.single("???????????????????????????",this);
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
                    mToast.single("???????????????????????????<????????????>?????????",this);
                    break;
                case 2:
                    mToast.single("???????????????????????????<????????????>?????????",this);
                    break;
            }
        }
    }

    /***
        *???????????????2021/4/1 10:04 PM
        *?????????xyd
        *?????????????????????
        *?????????
        *?????????(Y/N):
    */
    private void BulletFrame() {
        final DialogUtil dialogUtil = new DialogUtil(PlayerActivity.this,this);
        dialogUtil.dialog(getString(R.string.login_txt),getString(R.string.cancel),getString(R.string.gologin));
        dialogUtil.setOnItemClickListener(new DialogUtil.OnItemClickListener(){
            @Override
            public void onItemCancelClick() {
                //??????
            }

            @Override
            public void onItemConfirmClick() {
                JumpLogin();
            }
        });
    }
    /***
        *???????????????2021/4/1 10:02 PM
        *?????????xyd
        *???????????????Login????????????
        *?????????
        *?????????(Y/N):
    */
    private void JumpLogin(){
        startActivity(new Intent(PlayerActivity.this,LoginActivity.class));
        overridePendingTransition(R.anim.zoomin_right, R.anim.zoomout_left);
    }
    /***
        *???????????????2021/4/1 6:13 PM
        *?????????xyd
        *?????????????????????????????????
        *?????????
        *?????????(Y/N):
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
        *???????????????2021/4/1 6:14 PM
        *?????????xyd
        *???????????????????????????
        *?????????
        *?????????(Y/N):
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

        mMovieName.setText("??????:"+movieInfo.get(0).getV_name());
        mMovieRegion.setText("??????:"+movieInfo.get(0).getV_publisharea());
        mMovieYear.setText("??????:"+movieInfo.get(0).getV_publishyear());
        mMovieYuyan.setText("??????:"+movieInfo.get(0).getV_lang());
        mMovieAuthor.setText("??????:"+movieInfo.get(0).getV_director());
        mMovieYanyuan.setText("??????:"+movieInfo.get(0).getV_actor());
        Glide.with(this).load(movieInfo.get(0).getV_pic()).into(mMovieImg);
        mMovieIntroduction.setText("\u3000\u3000"+movieInfo.get(0).getBody());
        mPopuFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, dm.heightPixels-mVideoPlayer.getHeight()-60);//????????????????????????????????????
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