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
import com.example.simpletv.UsersDataBase.FavoriteVideo;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FavoriteVideo> favoriteVideos;
    private RecyclerCallBack recyclerCallBack;

    public FavoritesAdapter( RecyclerCallBack recyclerCallBack) {
        this.recyclerCallBack = recyclerCallBack;
    }

    public void setFavoriteVideo(List<FavoriteVideo> favoriteVideos) {
        this.favoriteVideos = favoriteVideos;
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
            Glide.with(holder.itemView.getContext()).load(favoriteVideos.get(position).getVideo_pic()).into(((WatchHistoryItems) holder).mHistoryImg);
            ((WatchHistoryItems) holder).mHistoryMovieName.setText(favoriteVideos.get(position).getVideo_name());
            ((WatchHistoryItems) holder).mLayoutClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerCallBack.callback(favoriteVideos.get(position).getVideo_vid(),favoriteVideos.get(position).getVideo_name());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return favoriteVideos==null?0:favoriteVideos.size();
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
