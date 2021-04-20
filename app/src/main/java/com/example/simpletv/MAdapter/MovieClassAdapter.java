package com.example.simpletv.MAdapter;

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

import java.util.List;
import java.util.Map;

public class MovieClassAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Map<String,String>> movieData;
    private MovieCallBack movieCallBack;
    private final static String PICTUREKEY="pic";
    private final static String MOVIENAME="name";
    private final static String MOVIEVID="vid";
    public MovieClassAdapter(MovieCallBack movieCallBack) {
        this.movieCallBack=movieCallBack;
    }

    public void setMovieData(List<Map<String,String>> movieData) {
        this.movieData = movieData;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_model, null);
        return new MovieClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        Glide.with(holder.itemView.getContext()).load(movieData.get(position).get(PICTUREKEY)).into(((MovieClass)holder).mMovieImg);
        ((MovieClass)holder).mMovieName.setText(movieData.get(position).get(MOVIENAME));
        ((MovieClass) holder).mMovieClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieCallBack.movieCallback(Integer.parseInt(movieData.get(position).get(MOVIEVID)),movieData.get(position).get(MOVIENAME));
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieData==null?0:movieData.size();
    }

    private class MovieClass extends RecyclerView.ViewHolder {
        private LinearLayout mMovieClick;
        private RoundImageView mMovieImg;
        private TextView mMovieName;

        public MovieClass(View view) {
            super(view);

            mMovieClick = view.findViewById(R.id.movie_click);
            mMovieImg = view.findViewById(R.id.movie_img);
            mMovieName = view.findViewById(R.id.movie_name);

        }
    }
}
