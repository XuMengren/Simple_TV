package com.example.simpletv.NetworkRequestInterface;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.example.simpletv.Interface.JsonDataCallBack;
import com.example.simpletv.Interface.JsonURLCallBack;
import com.example.simpletv.Interface.RequestFailedCallBack;
import com.example.simpletv.NetworkRequestInterface.JavaBean.BannerBean;
import com.example.simpletv.NetworkRequestInterface.JavaBean.FuzzySearchBean;
import com.example.simpletv.NetworkRequestInterface.JavaBean.MovieBean;
import com.example.simpletv.NetworkRequestInterface.JavaBean.MovieInfo;
import com.example.simpletv.NetworkRequestInterface.JavaBean.MovieTypeBean;
import com.example.simpletv.NetworkRequestInterface.JavaBean.MovieURL;
import com.example.simpletv.NetworkRequestInterface.JavaBean.RecommendBean;
import com.google.gson.Gson;

import java.util.List;

/***
    *创建时间：2021/1/31 5:51 PM
    *作者：xyd
    *描述：网络请求解析请求内容，将解析完成的内容通过接口传到Activity或Fragment
    *参数：
    *返回值(Y/N):
*/
public class mHandler extends Handler {
    private JsonDataCallBack jsonDataCallBack;
    private RequestFailedCallBack requestFailedCallBack;
    private final static String TAG = "mHandler_Ex";
    private List<BannerBean.DataBean> bannerData;
    private JsonURLCallBack jsonURLCallBack;

    public mHandler(JsonDataCallBack jsonDataCallBack, RequestFailedCallBack requestFailedCallBack, JsonURLCallBack jsonURLCallBack) {
        this.jsonDataCallBack = jsonDataCallBack;
        this.requestFailedCallBack=requestFailedCallBack;
        this.jsonURLCallBack=jsonURLCallBack;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case 0:
                try {
                    Gson gson = new Gson();
                    bannerData = gson.fromJson(msg.obj.toString(), BannerBean.class).getData();
                    jsonDataCallBack.BannerJsonData(bannerData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 1:
            case 4:
            case 6:
            case 7:
                try {
                    List<MovieBean.DataBean> MovieData = new Gson().fromJson(msg.obj.toString(), MovieBean.class).getData();
                    jsonDataCallBack.MovieJsonData(MovieData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    List<RecommendBean.DataBean> recommendData=new Gson().fromJson(msg.obj.toString(),RecommendBean.class).getData();
                    jsonDataCallBack.RecommendData(recommendData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try{
                    List<FuzzySearchBean.DataBean> FuzzySearch=new Gson().fromJson(msg.obj.toString(),FuzzySearchBean.class).getData();
                    jsonDataCallBack.FuzzySearchData(FuzzySearch);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case 5:
                try{
                    List<MovieTypeBean.DataBean> MovieType=new Gson().fromJson(msg.obj.toString(),MovieTypeBean.class).getData();
                    jsonDataCallBack.MovieTypeData(MovieType);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case 8:
                try{
                    MovieURL.DataBean MovieURL=new Gson().fromJson(msg.obj.toString(), MovieURL.class).getData();
                    jsonURLCallBack.URL(MovieURL);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case 9:
                try{
                    List<MovieInfo.DataBean> MovieInfo=  new Gson().fromJson(msg.obj.toString(), MovieInfo.class).getData();
                    jsonDataCallBack.MovieInfo(MovieInfo);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case 404:
                requestFailedCallBack.requestError(msg.obj.toString());
                break;
        }
    }
}
