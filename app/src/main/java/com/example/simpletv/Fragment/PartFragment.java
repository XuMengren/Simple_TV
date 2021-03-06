package com.example.simpletv.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletv.Activity.PlayerActivity;
import com.example.simpletv.Activity.SearchActivity;
import com.example.simpletv.MAdapter.MovieClassAdapter;
import com.example.simpletv.MAdapter.TopRecAdapter;
import com.example.simpletv.NetworkRequestInterface.Api;
import com.example.simpletv.Interface.JsonDataCallBack;
import com.example.simpletv.Interface.JsonURLCallBack;
import com.example.simpletv.Interface.MovieCallBack;
import com.example.simpletv.Interface.NetWorkCallBack;
import com.example.simpletv.Interface.OnClickCallBack;
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
import com.google.android.material.appbar.AppBarLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartFragment extends Fragment implements NetWorkCallBack, RequestFailedCallBack, JsonDataCallBack, OnClickCallBack, MovieCallBack, OnLoadMoreListener, View.OnClickListener, JsonURLCallBack {

    private com.example.simpletv.NetworkRequestInterface.mHandler mHandler = new mHandler(this, this,  this);
    private NetWork_Request netWork_request = new NetWork_Request(this);
    private List<String> regionlist = new ArrayList<>();//????????????
    private List<String> yearlist = new ArrayList<>();//????????????
    private List<String> languagelist = new ArrayList<>();//????????????
    private List<Map<String, String>> movieListmap = new ArrayList<>();//??????????????????
    private Map<String, String> movieMap;


    private ImageView search_img;
    private TextView mMyTitle;
    private Toolbar hint_toolbar;
    private TextView movie_hint;
    private ImageView pulldown;
    private SmartRefreshLayout mMovieClassRefresh;
    private RecyclerView mRecyclerView;
    private AppBarLayout appBarLayout;
    private RecyclerView fenlei_recTop;
    private TopRecAdapter topRecAdapter;
    private MovieClassAdapter adapter;

    private String RequestStr = Api.HOST + Api.DefaultRequest;//??????url??????
    private final static String ACTION_STR = "PartFragment";
    private final static int REFRESH_TIME = 1000;
    private final static String PICTUREKEY = "pic";
    private final static String MOVIENAME = "name";
    private final static String MOVIEVID = "vid";
    private int index = 0;

    //Tabs????????????????????????
    private String h1 = "0";
    private String h2 = "0";
    private String h3 = "0";
    private String h4 = "0";
    private static  String two, three, four;
    //?????????????????????????????????
    private static String h1str, h2str = null, h3str = null, h4str = null, movietype = null;
    private RelativeLayout hint_ll;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_part, null);
        initView(view);
        appBarOnClick();
        SetData();
        initTopRec();
        initAdapter();
        getHttp(0, 0, RequestStr);
        return view;
    }

    /***
     *???????????????2021/3/6 5:40 PM
     *?????????xyd
     *?????????appBar????????????
     *?????????
     *?????????(Y/N):
     */
    private void appBarOnClick() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (i == 0) {
                    //Appbarlayout ??????
                    movie_hint.setVisibility(View.GONE);
                    pulldown.setVisibility(View.GONE);
                } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
                    //Appbarlayout ??????
                    movie_hint.setVisibility(View.VISIBLE);
                    pulldown.setVisibility(View.VISIBLE);
                } else {
                    //Appbarlayout ????????????
                    movie_hint.setVisibility(View.VISIBLE);
                    pulldown.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    /***
     *???????????????2021/3/6 1:10 PM
     *?????????xyd
     *?????????????????????
     *?????????
     *?????????(Y/N):
     */
    private void getHttp(final int flag, final int index, final String requestStr) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                switch (flag) {
                    case 0:
                        //??????????????????
                        netWork_request.GetHttp(Api.HOST + Api.VideoType, 5);
                        //????????????????????????
                        netWork_request.GetHttp(Api.HOST + Api.DefaultRequest + 0, 1);
                        break;
                    case 1:
                        //??????????????????
                        netWork_request.GetHttp(requestStr + index, 1);
                        break;
                }
            }
        }).start();
    }


    /***
     *???????????????2021/3/4 3:08 PM
     *?????????xyd
     *??????????????????????????????Recycler ?????????
     *?????????
     *?????????(Y/N):
     */
    private void initTopRec() {
        topRecAdapter = new TopRecAdapter(this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        fenlei_recTop.setLayoutManager(manager);
        fenlei_recTop.setAdapter(topRecAdapter);
    }

    //????????????
    private void SetData() {
        mMyTitle.setText(R.string.movie_part);
        movie_hint.setText(R.string.movie_hint_defaultvalue);
        regionlist.add("??????");
        regionlist.add("??????");
        regionlist.add("??????");
        regionlist.add("??????");
        regionlist.add("??????");
        regionlist.add("??????");
        regionlist.add("??????");
        regionlist.add("??????");

        yearlist.add("??????");
        yearlist.add("2021");
        yearlist.add("2020");
        yearlist.add("2019");
        yearlist.add("2018");
        yearlist.add("2017");
        yearlist.add("2016");
        yearlist.add("2015");

        languagelist.add("??????");
        languagelist.add("??????");
        languagelist.add("??????");
        languagelist.add("??????");
        languagelist.add("??????");
        languagelist.add("??????");
        languagelist.add("??????");
        languagelist.add("??????");

    }

    /***
     *???????????????2021/3/6 1:11 PM
     *?????????xyd
     *?????????????????????????????????????????????
     *?????????
     *?????????(Y/N):
     */
    private void initAdapter() {
        adapter = new MovieClassAdapter(this);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 3, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
    }

    private void initView(View view) {
        search_img = view.findViewById(R.id.search_img);
        mMyTitle = view.findViewById(R.id.my_title);
        mMovieClassRefresh = view.findViewById(R.id.movie_class_refresh);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        pulldown = view.findViewById(R.id.pulldown);
        appBarLayout = view.findViewById(R.id.appBarLayout);
        fenlei_recTop = view.findViewById(R.id.fenlei_recTop);
        hint_toolbar = view.findViewById(R.id.hint_toolbar);
        movie_hint = view.findViewById(R.id.movie_hint);
        hint_ll = view.findViewById(R.id.hint_ll);
        mMovieClassRefresh.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mMovieClassRefresh.setOnLoadMoreListener(this);//????????????????????????
        mMovieClassRefresh.setEnableLoadMore(true);//??????????????????
        mMovieClassRefresh.setEnableRefresh(false);//??????????????????
        mMovieClassRefresh.setEnableOverScrollDrag(true);
        mMovieClassRefresh.setEnableOverScrollBounce(true);
        mMovieClassRefresh.setEnableFooterTranslationContent(true);//??????Footer????????????????????????
        mMovieClassRefresh.setEnableAutoLoadMore(false);//????????????????????????????????????????????????????????????
        search_img.setOnClickListener(this);
        hint_ll.setOnClickListener(this);

    }

    @Override
    public void success(String result, int flag) {
        //????????????
        Message message = new Message();
        message.what = flag;
        message.obj = result;
        mHandler.sendMessage(message);
    }

    @Override
    public void error(Exception result, int flag) {
        //????????????
        Message message = new Message();
        message.what = flag;
        message.obj = result;
        mHandler.sendMessage(message);
    }

    @Override
    public void requestError(String result) {

    }

    @Override
    public void BannerJsonData(List<BannerBean.DataBean> bannerBean) {

    }

    /***
     *???????????????2021/3/6 2:33 PM
     *?????????xyd
     *??????????????????????????????????????????????????????Recyclerview???
     *?????????
     *?????????(Y/N):
     */
    @Override
    public void MovieJsonData(List<MovieBean.DataBean> movieBean) {
        for (int i = 0; i < movieBean.size(); i++) {
            movieMap = new HashMap<>();
            movieMap.put(PICTUREKEY, movieBean.get(i).getV_pic());
            movieMap.put(MOVIENAME, movieBean.get(i).getV_name());
            movieMap.put(MOVIEVID, String.valueOf(movieBean.get(i).getV_id()));
            movieListmap.add(movieMap);
        }
        adapter.setMovieData(movieListmap);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void RecommendData(List<RecommendBean.DataBean> recommendBean) {

    }

    @Override
    public void FuzzySearchData(List<FuzzySearchBean.DataBean> fuzzyBean) {

    }

    /***
     *???????????????2021/3/6 2:34 PM
     *?????????xyd
     *????????????????????????????????????????????????????????????????????????
     *?????????
     *?????????(Y/N):
     */
    @Override
    public void MovieTypeData(List<MovieTypeBean.DataBean> movieTypeBean) {
        List<String> typelist = new ArrayList<>();
        typelist.add("??????");
        for (int i = 0; i < movieTypeBean.size(); i++) {
            typelist.add(movieTypeBean.get(i).getTname());
        }
        topRecAdapter.setTypelist(typelist);
        topRecAdapter.setRegionlist(regionlist);
        topRecAdapter.setLanguagelist(languagelist);
        topRecAdapter.setYearslist(yearlist);
        topRecAdapter.notifyDataSetChanged();
    }

    @Override
    public void MovieInfo(List<MovieInfo.DataBean> movieInfoBean) {

    }

    /***
        *???????????????2021/3/7 2:05 PM
        *?????????xyd
        *?????????????????????Tablayout????????????
        *?????????
        *?????????(Y/N):
    */
    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(int id, int position, String str) {
        index = 0;//????????????Tabs?????????index?????????

        //??????position????????????????????????Tabs?????????????????????
        switch (position) {
            case 0:
                //????????????
                h1 = String.valueOf(id);
                movietype = str;
                break;
            case 1:
                //????????????
                if (str.equals("??????")) {
                    h2 = String.valueOf(id);
                } else {
                    h2 = str;
                }
                h2str = regionlist.get(id);
                break;
            case 2:
                //????????????
                if (str.equals("??????")) {
                    h4 = String.valueOf(id);
                } else {
                    h4 = str;
                }
                h4str = languagelist.get(id);
                break;
            case 3:
                //????????????
                if (str.equals("??????")) {
                    h3 = String.valueOf(id);
                } else {
                    h3 = str;
                }
                h3str = yearlist.get(id);
                break;
        }
        //?????????????????????????????????????????????????????????

        h1str = movietype == null ? "??????" : movietype;
        two = h2str == null ? "??????" : h2str;
        three = h3str == null ? "??????" : h3str;
        four = h4str == null ? "??????" : h4str;
        //???????????????tabs?????????????????????hint???
        movie_hint.setText(h1str + "/" + two + "/" + four + "/" + three);
        //???????????????URL
        RequestStr = Api.HOST + Api.Movie_Part + h1 + "/" + h2 + "/" + h3 + "/" + h4 + "/" + index;
        //????????????Tabs???????????????????????????????????????
        movieListmap.clear();
        mRecyclerView.removeAllViews();
        //??????????????????URL????????????
        getHttp(1, index, RequestStr);
    }

    /***
     *???????????????2021/4/1 7:46 PM
     *?????????xyd
     *???????????????????????????
     *?????????
     *?????????(Y/N):
     */
    private void JumpVideo(int position, String title) {
        Intent intent = new Intent(getActivity(), PlayerActivity.class);
        Bundle bundle=new Bundle();
        bundle.putInt("vid",position);
        bundle.putString("title",title);
        intent.putExtra("vid",bundle);
        getContext().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.zoomin_right, R.anim.zoomout_left);
    }

    @Override
    public void movieCallback(int flag, String s) {
//        Toast.makeText(getContext(), "" + flag + "---" + s, Toast.LENGTH_SHORT).show();
        JumpVideo(flag,s);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        //???????????????index????????????18
        index += 18;
        getHttp(1, index, RequestStr);
        mMovieClassRefresh.finishLoadMore(REFRESH_TIME);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_img:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.setAction(ACTION_STR);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.bottom_pop_on, R.anim.bottom_on);
                break;
            case R.id.hint_ll:
                //??????????????????????????????appbarlayout
                appBarLayout.setExpanded(true);
                break;
        }
    }

    @Override
    public void URL(MovieURL.DataBean movieurl) {

    }
}
