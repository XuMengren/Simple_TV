package com.example.simpletv.Activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletv.MAdapter.FuzzySearchAdapter;
import com.example.simpletv.MAdapter.HotSearchAdapter;
import com.example.simpletv.MAdapter.SearchTagAdapter;
import com.example.simpletv.NetworkRequestInterface.Api;
import com.example.simpletv.Interface.HotSearchCallBack;
import com.example.simpletv.Interface.JsonDataCallBack;
import com.example.simpletv.Interface.JsonURLCallBack;
import com.example.simpletv.Interface.NetWorkCallBack;
import com.example.simpletv.Interface.RecyclerCallBack;
import com.example.simpletv.Interface.RequestFailedCallBack;
import com.example.simpletv.Interface.SearchHistoryCallBack;
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
import com.example.simpletv.SearchHistoryDataBase.Search;
import com.example.simpletv.SearchHistoryDataBase.SearchDao;
import com.example.simpletv.Tools.ActionSheet;
import com.example.simpletv.Tools.DialogUtil;
import com.example.simpletv.Tools.StatusBarUtil;
import com.google.android.material.tabs.TabLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener, NetWorkCallBack, JsonDataCallBack, RequestFailedCallBack, RecyclerCallBack, SearchHistoryCallBack, HotSearchCallBack, TabLayout.BaseOnTabSelectedListener, JsonURLCallBack {

    private mHandler handler = new mHandler(this, this,  this);
    private NetWork_Request netWork_request = new NetWork_Request(this);
    private static final String TAG = "SearchActivityEx";
    private final static String FUZZY_NAME_KEY = "name";
    private final static String FUZZY_VID_KEY = "vid";
    private final static String TNAME_KEY = "tname";
    private final static String TID_KEY = "tid";
    private final static String INTENT_SEARCH_RESULT = "query_value";
    private List<String> SearchHistoryList = new ArrayList<>();
    private FuzzySearchAdapter fuzzySearchAdapter;
    private TagFlowLayout search_history;
    private SearchDao searchDao;
    private LinearLayout search_history_title;
    private ListView fuzzySearvh;
    private SearchView searchView;
    private ImageView clearall;
    private ImageView back;
    private long clicktime;
    private ArrayList<Map<String, String>> typedata;
    private List<String> hot_search_title;
    private LinearLayout ll;
    private ActionSheet actionSheet;
    private ImageView mShow;
    private TextView mHintTxt;
    private boolean flag=true;
    private final static String NAME="name";
    private TabLayout hot_tab;
    private RecyclerView recycler_hot_search;
    private HotSearchAdapter hotSearchAdapter;
    private String action="Home";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getAction();
        initView();
        initFlowAdapter();
        initFuzzyAdapter();
        initHotSearchAdapter();
        getHttp(3,"");
    }

    private void getAction() {
        Intent intent=getIntent();
        action=intent.getAction()==null?"Home":intent.getAction();
    }

    /***
        *创建时间：2021/2/25 2:05 PM
        *作者：xyd
        *描述：初始化热搜适配器方法
        *参数：
        *返回值(Y/N):
    */
    private void initHotSearchAdapter() {
        hotSearchAdapter = new HotSearchAdapter(this);
        GridLayoutManager manager=new GridLayoutManager(this,2,RecyclerView.VERTICAL,false);
        recycler_hot_search.setLayoutManager(manager);
        recycler_hot_search.setAdapter(hotSearchAdapter);
        hotSearchAdapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /***
     *创建时间：2021/1/31 11:37 PM
     *作者：xyd
     *描述：系统返回键监听
     *参数：
     *返回值(Y/N):
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//如果返回键按下
            finish();
            overridePendingTransition(R.anim.bottom_off, R.anim.bottom_pop_off);
        }
        return super.onKeyDown(keyCode, event);
    }

    /***
     *创建时间：2021/2/1 2:03 PM
     *作者：xyd
     *描述：隐藏搜索历史
     *参数：
     *返回值(Y/N):
     */
    private void HideSearchHistory() {
        search_history_title.setVisibility(View.GONE);
        search_history.setVisibility(View.GONE);
    }

    /***
     *创建时间：2021/2/1 2:31 PM
     *作者：xyd
     *描述：显示搜索历史
     *参数：
     *返回值(Y/N):
     */
    private void ShowSearchHistory() {
        search_history_title.setVisibility(View.VISIBLE);
        search_history.setVisibility(View.VISIBLE);
    }

    /***
     *创建时间：2021/1/31 6:19 PM
     *作者：xyd
     *描述：设置流式布局FlowLayout的适配器
     *参数：
     *返回值(Y/N):
     */
    private void initFlowAdapter() {
        SearchHistoryList.clear();//执行设置适配器代码前必须将要插入数据的集合清空，不然会重复插入数据
        //将返回的List<Search>集合遍历一遍，放到SearchHistoryList集合中
        if (searchDao.GetSearch().size() > 0) {
            ShowSearchHistory();
            for (int i = 0; i < searchDao.GetSearch().size(); i++) {
                SearchHistoryList.add(searchDao.GetSearch().get(i).getNames());
            }
            try {
                //设置适配器
                SearchTagAdapter tagAdapter = new SearchTagAdapter(SearchHistoryList, this);
                search_history.setAdapter(tagAdapter);
                tagAdapter.notifyDataChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            HideSearchHistory();
        }

    }


    /***
     *创建时间：2021/1/31 11:25 PM
     *作者：xyd
     *描述：设置模糊搜索适配器
     *参数：
     *返回值(Y/N):
     */
    private void initFuzzyAdapter() {
        fuzzySearchAdapter = new FuzzySearchAdapter(this);
        fuzzySearvh.setAdapter(fuzzySearchAdapter);
    }

    /***
        *创建时间：2021/2/25 1:59 PM
        *作者：xyd
        *描述：网络请求
        *参数：index 指标，s 请求参数
        *返回值(Y/N):
    */
    private void getHttp(final int index, final String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                switch (index) {
                    //模糊搜索
                    case 0:
                        netWork_request.GetHttp(Api.HOST + Api.FuzzySearch + s, 3);
                        break;
                    //热搜名称
                    case 1:
                        netWork_request.GetHttp(Api.HOST + Api.returnVideosBasedOnType + s, 6);
                        break;
                    //热搜标题
                    case 3:
                        netWork_request.GetHttp(Api.HOST + Api.HotSearchType, 5);
                        break;
                }
            }
        }).start();
    }

    /***
     *创建时间：2021/2/1 3:40 PM
     *作者：xyd
     *描述：初始化控件
     *参数：
     *返回值(Y/N):
     */
    private void initView() {
        back = findViewById(R.id.back);
        searchView = findViewById(R.id.search_view);
        fuzzySearvh = findViewById(R.id.fuzzySearch);
        TextView txt = findViewById(R.id.search_txt);
        search_history = findViewById(R.id.search_history);
        ll = findViewById(R.id.hot_search_ll);
        mShow = findViewById(R.id.show);
        mHintTxt = findViewById(R.id.hint_txt);
        search_history_title = findViewById(R.id.search_history_title);
        clearall = findViewById(R.id.clearall);
        hot_tab = findViewById(R.id.hotsearch_tab);
        recycler_hot_search = findViewById(R.id.recycler_hot_search);
        recycler_hot_search.setOverScrollMode(View.OVER_SCROLL_NEVER);//去掉滑动水波纹
        mShow.setOnClickListener(this);
        clearall.setOnClickListener(this);
        searchDao = MyApp.getmInstance().getDb().searchDao();
        txt.setText(R.string.history);
        searchView.setOverScrollMode(View.OVER_SCROLL_NEVER);//去掉滑动水波纹
        searchView.setIconifiedByDefault(false);//设置搜索图标是否显示在搜索框内
        back.setOnClickListener(this);
        searchView.setOnQueryTextListener(this);
        hot_tab.addOnTabSelectedListener(this);

        StatusBarUtil.setTranslucentStatus(SearchActivity.this);
        StatusBarUtil.setStatusBarColor(this, Color.parseColor("#FAFAFA"));
        StatusBarUtil.setImmersiveStatusBar(this, false);
        StatusBarUtil.setStatusBarFontIconDark(this, StatusBarUtil.TYPE_M);
    }
    /***
        *创建时间：2021/2/25 10:41 AM
        *作者：xyd
        *描述：隐藏热门搜索
        *参数：
        *返回值(Y/N):
    */
    @SuppressLint({"NewApi", "UseCompatLoadingForDrawables"})
    private void HideHotSearch(){
        ll.setVisibility(View.GONE);
        mHintTxt.setVisibility(View.VISIBLE);
        mShow.setBackground(getDrawable(R.drawable.show_non));
    }
    /***
        *创建时间：2021/2/25 10:41 AM
        *作者：xyd
        *描述：显示热门搜索
        *参数：
        *返回值(Y/N):
    */
    @SuppressLint({"NewApi", "UseCompatLoadingForDrawables"})
    private void ShowHotSearch(){
        ll.setVisibility(View.VISIBLE);
        mHintTxt.setVisibility(View.GONE);
        mShow.setBackground(getDrawable(R.drawable.show));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                //点击返回的时候，判断输入框是否有数据，有的话先清除数据
                if (searchView.getQuery().length() > 0) {
                    searchView.setQuery("", false);
                } else {
                    finish();
                    overridePendingTransition(R.anim.bottom_off, R.anim.bottom_pop_off);

                }
                break;
            case R.id.clearall:
                BulletFrame();
                break;
            case R.id.show:
                if(flag){
                    HideHotSearch();
                    flag=false;
                }else{
                    ShowHotSearch();
                    flag=true;
                }
                break;
        }
    }
    /***
        *创建时间：2021/3/16 10:33 PM
        *作者：xyd
        *描述：删除历史记录弹框
        *参数：
        *返回值(Y/N):
    */
    private void BulletFrame() {
        final DialogUtil dialogUtil = new DialogUtil(SearchActivity.this,this);
        dialogUtil.dialog(getString(R.string.delete_history_all),getString(R.string.cancel),getString(R.string.confirm));
        dialogUtil.setOnItemClickListener(new DialogUtil.OnItemClickListener(){
            @Override
            public void onItemCancelClick() {
                //取消
            }

            @Override
            public void onItemConfirmClick() {
                //确定
                //删除所有搜索记录，刷新适配器
                searchDao.DeleteAll();
                initFlowAdapter();
            }
        });
    }

    /***
     *创建时间：2021/2/1 5:24 PM
     *作者：xyd
     *描述：跳转界面，从当前界面跳转到搜索结果界面
     *参数：
     *返回值(Y/N):
     */
    private void IntentToSearchResult(String result) {
        Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
        intent.putExtra(INTENT_SEARCH_RESULT, result);
        startActivity(intent);
        overridePendingTransition(R.anim.zoomin_right, R.anim.zoomout_left);
    }

    /***
     *创建时间：2021/2/1 2:18 PM
     *作者：xyd
     *描述：根据搜索历史中点击的字段在数据表中检索
     *参数：
     *返回值(Y/N):
     */
    public Search queryByHistory(String name) {
        Search search = searchDao.getSearchByName(name);
        return search;
    }

    /**
     * search view 监听事件
     *
     * @param query
     * @return
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        //保存搜索历史之前，先查询数据库是否有相同的数据，如果有，直接跳过，不给保存
        if (!searchDao.CheckName(query)) {
            insertData(query);
            //每次点击搜索的时候保存搜索历史
            initFlowAdapter();
        }
        IntentToSearchResult(query);
        return false;
    }

    /***
     *创建时间：2021/1/31 10:26 PM
     *作者：xyd
     *描述：插入搜索历史数据
     *参数：query
     *返回值(Y/N):
     */
    private void insertData(String query) {
        Search search = new Search();
        search.setNames(query);
        searchDao.InsertAll(search);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // 当搜索内容改变时触发该方法
        getHttp(0, newText);
        if (newText.length() <= 0) {
            fuzzySearvh.setVisibility(View.GONE);
        }
        fuzzySearchAdapter.setmKeyWord(newText);
        return false;
    }

    @Override
    public void success(String result, int flag) {
        Log.i(TAG, "success: "+result);
        //请求成功
        Message message = new Message();
        message.obj = result;
        message.what = flag;
        handler.sendMessage(message);
    }

    @Override
    public void error(Exception result, int flag) {
        //请求失败
        Message message = new Message();
        message.obj = result;
        message.what = flag;
        handler.sendMessage(message);
    }

    @Override
    public void BannerJsonData(List<BannerBean.DataBean> bannerBean) {

    }

    @Override
    public void MovieJsonData(List<MovieBean.DataBean> movieBean) {
        hotSearchAdapter.setMovieName(movieBean);
        hotSearchAdapter.notifyDataSetChanged();
    }

    @Override
    public void RecommendData(List<RecommendBean.DataBean> recommendBean) {

    }

    @Override
    public void FuzzySearchData(List<FuzzySearchBean.DataBean> fuzzyBean) {
        fuzzySearchAdapter.setData(fuzzyBean);
        fuzzySearchAdapter.notifyDataSetChanged();
        if (fuzzyBean.size() > 0) {
            fuzzySearvh.setVisibility(View.VISIBLE);
        } else {
            fuzzySearvh.setVisibility(View.GONE);
        }
    }

    /***
     *创建时间：2021/2/1 11:22 PM
     *作者：xyd
     *描述：将请求的影视类型数据解析完成后回调到SearchActivity 然后再将数据设置到Viewpager中
     *参数：
     *返回值(Y/N):
     */
    @SuppressLint("InflateParams")
    @Override
    public void MovieTypeData(List<MovieTypeBean.DataBean> movieTypeBean) {
        for (int i = 0; i < 7; i++) {
            hot_tab.addTab(hot_tab.newTab().setText(movieTypeBean.get(i).getTname()));
        }
    }

    @Override
    public void MovieInfo(List<MovieInfo.DataBean> movieInfoBean) {

    }

    @Override
    public void requestError(String result) {

    }

    /***
     *创建时间：2021/1/31 11:20 PM
     *作者：xyd
     *描述：模糊搜索点击事件执行方法，点击保存到搜索历史，保存搜索历史之前先检索表中有没有相同的数据，在进行保存，最后执行设置适配器方法，刷新数据
     *参数：
     *返回值(Y/N):
     */
    @Override
    public void callback(int flag, String movieName) {
        if (!searchDao.CheckName(movieName)) {
            Search search = new Search();
            search.setNames(movieName);
            searchDao.InsertAll(search);
        }
        initFlowAdapter();
        searchView.setQuery(movieName, true);
    }

    /***
     *创建时间：2021/2/1 3:43 PM
     *作者：xyd
     *描述：搜索历史点击事件回调
     *参数：
     *返回值(Y/N):
     */
    @Override
    public void getHistoryName(int flag, String name) {
        //根据接口传过来的flag判断要执行那个方法
        switch (flag) {
            case 0:
                //先根据想更改的内容得到那一项的实体类,然后进行删除
                Search history = queryByHistory(name);
                searchDao.Delete(history);
                initFlowAdapter();
                break;
            case 1:
                //将点击搜索历史的文字放到 searchview 中，并且搜索
                searchView.setQuery(name, true);
                break;
        }

    }

    /***
        *创建时间：2021/2/25 3:07 PM
        *作者：xyd
        *描述：热门搜索点击事件回调，点击内容直接搜索
        *参数：
        *返回值(Y/N):
    */
    @Override
    public void getHot(String id) {
        searchView.setQuery(id,true);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        //设置tablayout选中字体有放大效果
        TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.select_txt,null);
        textView.setTextSize(18);
        textView.setText(tab.getText());
        tab.setCustomView(textView);
        int i ;
        i=tab.getPosition()+1;
        getHttp(1, String.valueOf(i));
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
    public void URL(MovieURL.DataBean movieurl) {

    }
}
