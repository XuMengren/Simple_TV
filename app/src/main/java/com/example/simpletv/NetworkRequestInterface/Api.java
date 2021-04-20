package com.example.simpletv.NetworkRequestInterface;

public class Api {
    public static String getHOST() {
        return HOST;
    }

    public static String HOST="http://129.211.123.193:8081";//请求头
    public static String VideoType="/video/classification/allType";//影视类型
    public static String ByTypeReturnVideo="/video/classification/randomByType/";//通过类型ID返回视频
    public static String BannerVideo="/video/carousel/all";//轮播图
    public static String Home_swipe_left_and_right="/video/classification/type/";//首页大家都在看
    public static String Home_Recommend="/video/classification/randomByType/";//首页推荐
    public static String FuzzySearch="/video/search/hint/";//模糊搜索
    public static String SearchResult="/video/search/name/";//按照名称搜索
    public static String HotSearchType="/video/classification/allType";//电影类型
    public static String returnVideosBasedOnType="/video/classification/randomByType/";//根据类型搜索内容
    public static String Movie_Part="/video/classification/type/";//根据各种条件筛选出影视信息
    public static String DefaultRequest="/video/classification/type/0/0/0/0/";//默认请求所有电影数据
    public static String Movie_info="/video/video/details/";//根据Vid查询影视信息
    public static String Movie_URL="/video/video/url/";//根据Vid查询影视URL
}
