package com.example.simpletv.MAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.simpletv.Interface.MovieCallBack;
import com.example.simpletv.NetworkRequestInterface.JavaBean.MovieBean;
import com.example.simpletv.R;
import com.example.simpletv.Tools.CheckTitle;
import com.example.simpletv.Tools.MatchSearchField;
import com.example.simpletv.Tools.RoundImageView;

import java.util.List;

public class SearchResultAdapter extends BaseAdapter {
    private List<MovieBean.DataBean> movieData;
    private String mKeyWord;
    private MovieCallBack movieCallBack;

    public SearchResultAdapter(MovieCallBack movieCallBack) {
        this.movieCallBack = movieCallBack;
    }

    public void setmKeyWord(String mKeyWord) {
        this.mKeyWord = mKeyWord;
    }

    public void setMovieData(List<MovieBean.DataBean> movieData) {
        this.movieData = movieData;
    }

    @Override
    public int getCount() {
        return movieData == null ? 0 : movieData.size();
    }

    @Override
    public Object getItem(int position) {
        return movieData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        @SuppressLint({"InflateParams", "ViewHolder"}) View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result, null);
        LinearLayout mPlayMovie = inflate.findViewById(R.id.play_movie);
        RoundImageView mResultPic = inflate.findViewById(R.id.result_pic);
        TextView type=inflate.findViewById(R.id.txt_type);
        TextView mMovieName = inflate.findViewById(R.id.movie_name);
        TextView mClarity = inflate.findViewById(R.id.clarity);
        TextView mScore = inflate.findViewById(R.id.score);
        Button btn_play=inflate.findViewById(R.id.btn_play);
        CheckTitle.ReplaceTitle(movieData.get(position).getTid(),type);
        Glide.with(parent.getContext()).load(movieData.get(position).getV_pic()).into(mResultPic);
        MatchSearchField.matcherSearchTitle(movieData.get(position).getV_name(),mKeyWord,mMovieName);//将搜索匹配的文字设置提示颜色
        mScore.setText(""+movieData.get(position).getV_score());
        mClarity.setText(movieData.get(position).getV_note());
        mPlayMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieCallBack.movieCallback(movieData.get(position).getV_id(),movieData.get(position).getV_name());
            }
        });
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieCallBack.movieCallback(movieData.get(position).getV_id(),movieData.get(position).getV_name());
            }
        });

        return inflate;
    }
}
