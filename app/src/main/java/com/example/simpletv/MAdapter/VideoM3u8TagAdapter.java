package com.example.simpletv.MAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.simpletv.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VideoM3u8TagAdapter extends TagAdapter<String> {

    private TextView mHistoryTxt;
    public VideoM3u8TagAdapter(List<String> datas) {
        super(datas);
    }
    @Override
    public View getView(@NotNull FlowLayout parent, int position, String s) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.video_flowlayout_items,null);
        mHistoryTxt = view.findViewById(R.id.history_txt);
        mHistoryTxt.setText(s);
        return view;
    }
}
