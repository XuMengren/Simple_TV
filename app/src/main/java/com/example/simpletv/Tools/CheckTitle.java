package com.example.simpletv.Tools;

import android.widget.TextView;

import com.example.simpletv.R;
/***
    *创建时间：2021/2/2 1:31 PM
    *作者：xyd
    *描述：检测搜索结果为什么类型视频
    *参数：
    *返回值(Y/N):
*/
public class CheckTitle {
    public static void ReplaceTitle (int id, TextView textView){
        switch (id){
            case 1:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 33:
            case 34:
                textView.setText( R.string.movie1);
                break;
            case 2:
            case 14:
            case 15:
            case 16:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 45:
                textView.setText(R.string.movie2);
                break;
            case 3:
            case 38:
            case 39:
            case 40:
            case 41:
                textView.setText(R.string.movie3);
                break;
            case 4:
            case 35:
            case 36:
            case 37:
            case 42:
            case 43:
                textView.setText(R.string.movie4);
                break;
            case 11:
                textView.setText(R.string.movie5);
                break;
            case 44:
                textView.setText(R.string.movie6);
                break;
            default:
                textView.setText("--");
                break;
        }
    }
}
