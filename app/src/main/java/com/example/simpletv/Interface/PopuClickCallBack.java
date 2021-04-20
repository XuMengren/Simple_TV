package com.example.simpletv.Interface;

import android.view.View;

import com.example.simpletv.NetworkRequestInterface.JavaBean.MovieInfo;

import java.util.List;

public interface PopuClickCallBack {
    void PopuCallBack(View view, List<MovieInfo.DataBean> movieInfo);
}
