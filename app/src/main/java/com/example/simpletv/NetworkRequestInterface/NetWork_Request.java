package com.example.simpletv.NetworkRequestInterface;

import android.util.Log;

import com.example.simpletv.Interface.NetWorkCallBack;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class NetWork_Request {
    private NetWorkCallBack callBack;
    private final static String TAG = "NetWorkTools_Ex";

    public NetWork_Request(NetWorkCallBack callBack) {
        this.callBack = callBack;
    }

    /***
     *创建时间：2021/1/31 5:50 PM
     *作者：xyd
     *描述：网络请求Get请求工具类
     *参数：urlStr,flag
     *返回值(Y/N): N
     */
    public void GetHttp(String urlStr, final int flag) {
        Log.i(TAG, "GetHttp: " + urlStr);
        OkHttpUtils
                .get()
                .url(urlStr)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callBack.error(e, flag);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        callBack.success(response, flag);
                    }
                });
    }
}
