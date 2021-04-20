package com.example.simpletv.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.simpletv.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

public class CachePageFragment extends Fragment {
    private SwipeMenuRecyclerView mWatchHistoryRecycler;
    private TextView mTextHint;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_cachepage,null);
        initView(view);
        return view;
    }
    private void initView(View view) {
        mWatchHistoryRecycler = view.findViewById(R.id.watch_history_recycler);
        mTextHint = view.findViewById(R.id.text_hint);
        mWatchHistoryRecycler.setSwipeMenuCreator(swipeMenuCreator);
        mWatchHistoryRecycler.setSwipeMenuItemClickListener(swipeMenuItemClickListener);
        mTextHint.setText("暂无缓存内容，去添加缓存吧～");
        mTextHint.setVisibility(View.VISIBLE);
        mWatchHistoryRecycler.setVisibility(View.GONE);
    }
    /***
        *创建时间：2021/4/5 10:22 PM
        *作者：xyd
        *描述：测滑RecyclerView
        *参数：
        *返回值(Y/N):
    */
    private SwipeMenuCreator swipeMenuCreator=new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            DisplayMetrics dm=new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity())
                    .setBackgroundColor(getResources().getColor(R.color.red_bg)) // 背景颜色
                    .setText("删除") // 文字。
                    .setTextColor(Color.WHITE) // 文字颜色。
                    .setTextSize(16) // 文字大小。
                    .setWidth(dm.heightPixels/8) // 宽
                    .setHeight((int) getResources().getDimension(R.dimen.movie_width)); //高（MATCH_PARENT意为Item多高侧滑菜单多高 （推荐使用））
            swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
        }
    };

    /***
        *创建时间：2021/4/5 10:25 PM
        *作者：xyd
        *描述：删除按钮点击事件
        *参数：
        *返回值(Y/N):
    */
    private SwipeMenuItemClickListener swipeMenuItemClickListener=new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();
        }
    };
}
