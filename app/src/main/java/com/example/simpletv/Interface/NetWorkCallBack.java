package com.example.simpletv.Interface;

public interface NetWorkCallBack {
    void success(String result,int flag);
    void error(Exception result,int flag);
}
