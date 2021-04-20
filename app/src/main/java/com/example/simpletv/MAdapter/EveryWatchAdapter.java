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
import com.example.simpletv.R;
import com.example.simpletv.Tools.RoundImageView;

import java.util.ArrayList;
import java.util.Map;

public class EveryWatchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private MovieCallBack movieCallBack;
    private ArrayList<Map<String, String>> movieList ;
    private final static String PIC_KEY = "img";
    private final static String STRING = "name";
    private final static String V_ID = "vid";
    public void setMovieList(ArrayList<Map<String, String>> movieList) {
        this.movieList = movieList;
    }

    public EveryWatchAdapter(MovieCallBack movieCallBack) {
        this.movieCallBack = movieCallBack;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_model, null);
        return new EveryWatch(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        Glide.with(holder.itemView.getContext()).load(movieList.get(position).get(PIC_KEY)).into(((EveryWatch)holder).mMovieImg);
        ((EveryWatch) holder).mMovieName.setText(movieList.get(position).get(STRING));
        ((EveryWatch) holder).mMovieClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieCallBack.movieCallback(Integer.parseInt(movieList.get(position).get(V_ID)),((EveryWatch) holder).mMovieName.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList==null?0:movieList.size();
    }

    private class EveryWatch extends RecyclerView.ViewHolder {
        private LinearLayout mMovieClick;
        private RoundImageView mMovieImg;
        private TextView mMovieName;

        public EveryWatch(View view) {
            super(view);
            mMovieClick = view.findViewById(R.id.movie_click);
            mMovieImg = view.findViewById(R.id.movie_img);
            mMovieName = view.findViewById(R.id.movie_name);
        }
    }
}
