package com.example.simpletv.MAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.simpletv.Interface.RecyclerCallBack;
import com.example.simpletv.R;
import com.example.simpletv.UsersDataBase.WatchHistory;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class WatchHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<WatchHistory> watchHistories;
    private RecyclerCallBack recyclerCallBack;

    public WatchHistoryAdapter( RecyclerCallBack recyclerCallBack) {
        this.recyclerCallBack = recyclerCallBack;
    }

    public void setWatchHistories(List<WatchHistory> watchHistories) {
        this.watchHistories = watchHistories;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.watch_history_items,null);
        return new WatchHistoryItems(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof WatchHistoryItems) {
            Glide.with(holder.itemView.getContext()).load(watchHistories.get(position).getHistory_pic()).into(((WatchHistoryItems) holder).mHistoryImg);
            ((WatchHistoryItems) holder).mHistoryMovieName.setText(watchHistories.get(position).getHistory_video_name());
            ((WatchHistoryItems) holder).mHistoryDate.setText(watchHistories.get(position).getHistory_date());
            ((WatchHistoryItems) holder).mLayoutClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerCallBack.callback(watchHistories.get(position).getHistory_video_vid(),watchHistories.get(position).getHistory_video_name());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return watchHistories==null?0:watchHistories.size();
    }

    private class WatchHistoryItems extends RecyclerView.ViewHolder {
        private LinearLayout mLayoutClick;
        private RoundedImageView mHistoryImg;
        private TextView mHistoryMovieName,mHistoryDate;
        public WatchHistoryItems(View view) {
            super(view);

            mHistoryDate=view.findViewById(R.id.history_date);
            mLayoutClick = view.findViewById(R.id.layout_click);
            mHistoryImg = view.findViewById(R.id.history_img);
            mHistoryMovieName = view.findViewById(R.id.history_movie_name);

        }
    }
}
