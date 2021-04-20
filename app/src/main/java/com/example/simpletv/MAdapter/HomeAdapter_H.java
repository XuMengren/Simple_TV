package com.example.simpletv.MAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.simpletv.Interface.RecyclerCallBack;
import com.example.simpletv.NetworkRequestInterface.JavaBean.MovieBean;
import com.example.simpletv.R;

import java.util.List;
/***
    *创建时间：2021/1/31 5:57 PM
    *作者：xyd
    *描述：大家都在看RecyclerView适配器
    *参数：
    *返回值(Y/N):
*/
public class HomeAdapter_H extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MovieBean.DataBean> movieBean;
    private RecyclerCallBack recyclerCallBack;

    public HomeAdapter_H(RecyclerCallBack recyclerCallBack) {
        this.recyclerCallBack = recyclerCallBack;
    }

    public void setMovieBean(List<MovieBean.DataBean> movieBean) {
        this.movieBean = movieBean;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recommend, null);
        return new Recommend_view(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        Glide.with(holder.itemView.getContext()).load(movieBean.get(position).getV_pic()).into(((Recommend_view) holder).mMovieImg);
        ((Recommend_view) holder).mMovieName.setText(movieBean.get(position).getV_name());
        ((Recommend_view) holder).movie_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /***
                 * 大家都在看点击事件，将点击的Vid通过interface传出去
                 */
               recyclerCallBack.callback(movieBean.get(position).getV_id(),((Recommend_view) holder).mMovieName.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieBean == null ? 0 : movieBean.size();
    }

    private class Recommend_view extends RecyclerView.ViewHolder {
        private ImageView mMovieImg;
        private TextView mMovieName;
        private LinearLayout movie_click;

        public Recommend_view(View view) {
            super(view);
            mMovieImg = view.findViewById(R.id.movie_img);
            mMovieName = view.findViewById(R.id.movie_name);
            movie_click = view.findViewById(R.id.movie_click);
        }
    }
}
