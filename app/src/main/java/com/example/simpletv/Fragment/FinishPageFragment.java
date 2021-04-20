package com.example.simpletv.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.simpletv.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.List;

public class FinishPageFragment extends Fragment {
    private SwipeMenuRecyclerView mWatchHistoryRecycler;
    private TextView mTextHint;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mWatchHistoryRecycler = view.findViewById(R.id.watch_history_recycler);
        mTextHint = view.findViewById(R.id.text_hint);

        mTextHint.setText("没有缓存视频,去下载视频吧~");
        mWatchHistoryRecycler.setVisibility(View.GONE);
        mTextHint.setVisibility(View.VISIBLE);
    }
}
