package com.example.simpletv.MAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.simpletv.Interface.MovieCallBack;
import com.example.simpletv.NetworkRequestInterface.JavaBean.MovieBean;
import com.example.simpletv.R;
import com.example.simpletv.Tools.RoundImageView;

import java.util.List;

public class SameVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MovieBean.DataBean> movieBean;
    private MovieCallBack movieCallBack;

    public SameVideoAdapter(List<MovieBean.DataBean> movieBean, MovieCallBack movieCallBack) {
        this.movieBean = movieBean;
        this.movieCallBack = movieCallBack;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recommend, null);
        return new Related_Video(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof Related_Video){
            Glide.with(holder.itemView.getContext()).load(movieBean.get(position).getV_pic()).into(((Related_Video) holder).mMovieImg);
            ((Related_Video) holder).mMovieName.setText(movieBean.get(position).getV_name());
            ((Related_Video) holder).mMovieClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieCallBack.movieCallback(movieBean.get(position).getV_id(),movieBean.get(position).getV_name());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return movieBean==null?0:movieBean.size();
    }

    private class Related_Video extends RecyclerView.ViewHolder {
        private LinearLayout mMovieClick;
        private RoundImageView mMovieImg;
        private TextView mMovieName;

        public Related_Video(View view) {
            super(view);
            mMovieClick = view.findViewById(R.id.movie_click);
            mMovieImg = view.findViewById(R.id.movie_img);
            mMovieName = view.findViewById(R.id.movie_name);

        }
    }
}
