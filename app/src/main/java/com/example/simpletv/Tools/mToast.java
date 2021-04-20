package com.example.simpletv.Tools;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;

public class mToast {
    private static Toast toast;

    public static void show(String text, Context context) {
        if (toast == null) {
            toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
            //这个地方第二个参数需要为null
            toast.setText(text);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    public static void show(@StringRes int resId,Context context) {
        if (toast == null) {
            toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
            toast.setText(resId);
        } else {
            toast.setText(resId);
        }
        toast.show();
    }

    /**
     * 40      * 弹出多个toast时, 不会一个一个的弹, 后面一个要显示的内容直接显示在当前的toast上
     * 41
     */
    public static void single(String msg,Context context) {
        if (toast == null) {
            toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
            toast.setText(msg);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public static void singleLong(String msg,Context context) {
        if (toast == null) {
            toast = Toast.makeText(context, null, Toast.LENGTH_LONG);
            toast.setText(msg);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 63      * 多行居中显示
     * 64
     */
    public static void singleCenter(@StringRes int msg,Context context) {
        if (toast == null) {
            toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
            toast.setText(msg);
        } else {
            toast.setText(msg);
        }
        ((TextView) toast.getView().findViewById(android.R.id.message)).setGravity(Gravity.CENTER);
        toast.show();
    }

    /**
     * 77      * 多行居中显示
     * 78
     */
    public static void singleCenter(String msg,Context context) {
        if (toast == null) {
            toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
            toast.setText(msg);
        } else {
            toast.setText(msg);
        }
        ((TextView) toast.getView().findViewById(android.R.id.message)).setGravity(Gravity.CENTER);
        toast.show();
    }

    /**
     * 91      * 弹出多个toast时, 不会一个一个的弹, 后面一个要显示的内容直接显示在当前的toast上
     * 92
     */
    public static void single(@StringRes int msg,Context context) {
        if (toast == null) {
            toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
            toast.setText(msg);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
