package com.example.simpletv.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletv.Activity.EveryoneIsWatchingActivity;
import com.example.simpletv.Activity.SearchActivity;
import com.example.simpletv.MAdapter.HomeAdapter;
import com.example.simpletv.NetworkRequestInterface.Api;
import com.example.simpletv.Interface.BannerClickCallBack;
import com.example.simpletv.Interface.JsonDataCallBack;
import com.example.simpletv.Interface.JsonURLCallBack;
import com.example.simpletv.Interface.JumpCallBack;
import com.example.simpletv.Interface.LayoutJumpCallBack;
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
import com.example.simpletv.Activity.PlayerActivity;
import com.example.simpletv.R;
import com.example.simpletv.Tools.NetWorkUtils;
import com.example.simpletv.Tools.mToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment implements NetWorkCallBack, JsonDataCallBack, BannerClickCallBack, MovieCallBack, OnLoadMoreListener, RequestFailedCallBack, OnRefreshListener, LayoutJumpCallBack, View.OnKeyListener, JumpCallBack, JsonURLCallBack {

    private RecyclerView recycler;
    private HomeAdapter adapter;
    private NetWork_Request netWorkRequest = new NetWork_Request(this);
    private mHandler handler = new mHandler(this, this,  this);
    private ArrayList<Integer> vid = new ArrayList<>();
    private final static String PIC_KEY = "img";
    private final static String STRING = "name";
    private final static String V_ID = "vid";
    private final static int REFRESH_TIME = 1500;
    private static int INDEX = 1;//????????????????????????
    private SmartRefreshLayout smart_refresh;
    private ArrayList<Map<String, String>> recommendList = new ArrayList<>();
    private LinearLayout net_hint;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        initView(view);
        initGetNetType();
        getHttp(0);
        initAdapter();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initGetNetType();
    }

    @Override
    public void onResume() {
        super.onResume();
        initGetNetType();
    }

    @Override
    public void onPause() {
        super.onPause();
        initGetNetType();
    }


    private void initGetNetType() {
        boolean netWorkType = NetWorkUtils.isNetworkAvailable(getContext());
        if (netWorkType) {
            net_hint.setVisibility(View.GONE);
        } else {
            net_hint.setVisibility(View.VISIBLE);
        }
    }

    private void initView(View view) {
        net_hint = view.findViewById(R.id.net_hint);
        recycler = view.findViewById(R.id.home_recycler);
        recycler.setOverScrollMode(View.OVER_SCROLL_NEVER);//?????????????????????
        smart_refresh = view.findViewById(R.id.smart_refresh);
        smart_refresh.setOnLoadMoreListener(this);
        smart_refresh.setOnRefreshListener(this);
        smart_refresh.setEnableRefresh(true);
        smart_refresh.setEnableOverScrollDrag(true);
        smart_refresh.setEnableOverScrollBounce(true);
        smart_refresh.setEnableFooterTranslationContent(true);
        smart_refresh.setEnableAutoLoadMore(false);
    }

    /**
     * ????????????
     */
    private void getHttp(final int flag) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                switch (flag) {
                    case 0:
                        netWorkRequest.GetHttp(Api.HOST + Api.BannerVideo, 0);
                        netWorkRequest.GetHttp(Api.HOST + Api.Home_swipe_left_and_right + INDEX, 1);
                        netWorkRequest.GetHttp(Api.HOST + Api.Home_Recommend + 1, 2);
                        break;
                    case 1:
                        netWorkRequest.GetHttp(Api.HOST + Api.Home_Recommend + INDEX, 2);
                        break;
                }

            }
        }).start();
    }


    /**
     * ??????????????????RecyclerView?????????
     * ??????GridLayoutManager ??????????????????????????????????????????????????????
     */
    private void initAdapter() {
        adapter = new HomeAdapter(this, this, this,this);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (position) {
                    case 0:
                    case 1:
                    case 2:
                        return 2;
                    default:
                        return 1;
                }
            }
        });
        recycler.setAdapter(adapter);
    }


    @Override
    public void success(String result, int flag) {
        //????????????
        Message message = new Message();
        message.what = flag;
        message.obj = result;
        handler.sendMessage(message);
    }

    @Override
    public void error(Exception result, int flag) {
        //????????????
        Message message = new Message();
        message.what = flag;
        message.obj = result;
        handler.sendMessage(message);
    }

    /**
     * ??????????????????????????????
     *
     * @param dataBeans
     */
    @Override
    public void BannerJsonData(List<BannerBean.DataBean> dataBeans) {
        ArrayList<String> picture=new ArrayList<>();
        ArrayList<String> title=new ArrayList<>();
        vid.clear();
        for (int i = 0; i < dataBeans.size(); i++) {
            vid.add(dataBeans.get(i).getV_id());
            picture.add(dataBeans.get(i).getV_spic());
            title.add(dataBeans.get(i).getV_name());
        }
        adapter.setPicture(picture);
        adapter.setTitle(title);
        adapter.notifyDataSetChanged();
    }

    /**
     * ???????????????????????????
     *
     * @param movieBean
     */
    @Override
    public void MovieJsonData(List<MovieBean.DataBean> movieBean) {
        adapter.setMovieBean(movieBean);
        adapter.notifyDataSetChanged();
    }

    /**
     * ?????????????????????????????????
     *
     * @param recommendBean
     */
    @Override
    public void RecommendData(List<RecommendBean.DataBean> recommendBean) {
        //????????????????????????????????????????????????????????????????????????????????????????????????
        Map<String, String> map;
        for (int i = 0; i < recommendBean.size(); i++) {
            map = new HashMap<>();
            map.put(PIC_KEY, recommendBean.get(i).getV_pic());
            map.put(STRING, recommendBean.get(i).getV_name());
            map.put(V_ID, String.valueOf(recommendBean.get(i).getV_id()));
            recommendList.add(map);
        }
        adapter.setRecommendBean(recommendList);
        //?????????????????????????????????????????????????????????????????????????????????
        adapter.notifyItemChanged(3, recommendList.size());
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

    /**
     * ????????????????????????????????????position????????????????????????position??????vid???????????????????????????vid
     *
     * @param position
     */
    @Override
    public void bannerCallback(int position,String title) {
//        Toast.makeText(getContext(), "" + vid.get(position), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), PlayerActivity.class);
        Bundle bundle=new Bundle();
        bundle.putInt("vid",vid.get(position));
        bundle.putString("title",title);
        intent.putExtra("vid",bundle);
        getContext().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.zoomin_right, R.anim.zoomout_left);

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

    /***
     *???????????????2021/1/31 6:02 PM
     *?????????xyd
     *??????????????????????????????????????????
     *?????????
     *?????????(Y/N):
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        //??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        boolean netWorkType = NetWorkUtils.isNetworkAvailable(getContext());
        if (netWorkType) {
            net_hint.setVisibility(View.GONE);

        } else {
            net_hint.setVisibility(View.VISIBLE);
            //????????????????????????????????????????????????
            smart_refresh.finishLoadMoreWithNoMoreData();
        }

        //????????????????????????7???
        if (INDEX < 10) {
            //?????????????????????????????????
            getHttp(1);
            INDEX++;
            //??????????????????
            smart_refresh.finishLoadMore(REFRESH_TIME);
        } else {
            //????????????????????????????????????????????????
            smart_refresh.finishLoadMoreWithNoMoreData();
        }

    }

    @Override
    public void requestError(String result) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        //??????????????????????????????????????????????????????????????????????????????
        boolean netWorkType = NetWorkUtils.isNetworkAvailable(getContext());
        if (netWorkType) {
            net_hint.setVisibility(View.GONE);
            mToast.single(R.string.refreshSuccess,getContext());
        } else {
            net_hint.setVisibility(View.VISIBLE);
            mToast.single(R.string.checkInternet,getContext());
        }
        getHttp(0);
        smart_refresh.finishRefresh(REFRESH_TIME);
    }

    @Override
    public void LayoutJump(View view) {
        getContext().startActivity(new Intent(getActivity(), SearchActivity.class));
        getActivity().overridePendingTransition(R.anim.bottom_pop_on,R.anim.bottom_on);

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void goJump(LinearLayout linearLayout) {
        Intent intent = new Intent(getActivity(), EveryoneIsWatchingActivity.class);
        getContext().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.zoomin_right, R.anim.zoomout_left);
    }

    @Override
    public void URL(MovieURL.DataBean movieurl) {

    }
}
