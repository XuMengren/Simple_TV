package com.example.simpletv.Tools;

import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;


public class MatchSearchField{
    /***
        *创建时间：2021/1/31 5:49 PM
        *作者：xyd
        *描述：模糊搜索，检索搜索字段，并且将搜索字段给出提示颜色
        *参数：title,keyword,textView
        *返回值(Y/N): N
    */
    public static void matcherSearchTitle(String title, String keyword, TextView textView){
        String content = title;
        if (content != null && content.contains(keyword)) {
            int index = content.indexOf(keyword);
            int len = keyword.length();
            Spanned temp = Html.fromHtml(content.substring(0, index)
                    + "<font color=#FF5722>"
                    + content.substring(index, index + len) + "</font>"
                    + content.substring(index + len, content.length()));

            textView.setText(temp);
        } else {
            textView.setText(content);
        }
    }

}


