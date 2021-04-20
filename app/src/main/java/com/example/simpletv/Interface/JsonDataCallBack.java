package com.example.simpletv.Interface;

import com.example.simpletv.NetworkRequestInterface.JavaBean.BannerBean;
import com.example.simpletv.NetworkRequestInterface.JavaBean.FuzzySearchBean;
import com.example.simpletv.NetworkRequestInterface.JavaBean.MovieBean;
import com.example.simpletv.NetworkRequestInterface.JavaBean.MovieInfo;
import com.example.simpletv.NetworkRequestInterface.JavaBean.MovieTypeBean;
import com.example.simpletv.NetworkRequestInterface.JavaBean.RecommendBean;

import java.util.List;

public interface JsonDataCallBack {
    void BannerJsonData(List<BannerBean.DataBean> bannerBean);
    void MovieJsonData(List<MovieBean.DataBean> movieBean);
    void RecommendData(List<RecommendBean.DataBean> recommendBean);
    void FuzzySearchData(List<FuzzySearchBean.DataBean> fuzzyBean);
    void MovieTypeData(List<MovieTypeBean.DataBean> movieTypeBean);
    void MovieInfo(List<MovieInfo.DataBean> movieInfoBean);
}
