package com.example.simpletv.MAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.simpletv.NetworkRequestInterface.JavaBean.MovieURL;
import com.example.simpletv.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

public class VideoOtherTagAdapter extends TagAdapter<String> {

    private TextView mHistoryTxt;
//    private MovieURL.DataBean movieUrl;

//    public void setMovieUrl(MovieURL.DataBean movieUrl) {
//        this.movieUrl = movieUrl;
//    }

    public VideoOtherTagAdapter(List<String> datas) {
        super(datas);
    }
    @Override
    public View getView(FlowLayout parent, int position, String s) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.video_flowlayout_items,null);
        mHistoryTxt = view.findViewById(R.id.history_txt);
        mHistoryTxt.setText(s);
        return view;
    }
}
