package com.example.simpletv.MAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.simpletv.Interface.ChasingCheckCallBack;
import com.example.simpletv.Interface.MovieCallBack;
import com.example.simpletv.R;
import com.example.simpletv.Tools.RoundImageView;
import com.example.simpletv.UsersDataBase.FavoriteVideo;

import java.util.ArrayList;
import java.util.List;

public class ChasingDramaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private boolean flag, mark = false;
    private ChasingCheckCallBack checkCallBack;
    private List<Integer> list = new ArrayList<>();
    private List<FavoriteVideo> favoriteVideos;
    private MovieCallBack movieCallBack;

    public void setFavoriteVideos(List<FavoriteVideo> favoriteVideos) {
        this.favoriteVideos = favoriteVideos;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

    public ChasingDramaAdapter(ChasingCheckCallBack checkCallBack, MovieCallBack movieCallBack) {
        this.checkCallBack = checkCallBack;
        this.movieCallBack = movieCallBack;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chasing_drama_layout, null);
        return new Chasuing_layout(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Chasuing_layout) {
            //设置图片信息
            ((Chasuing_layout) holder).mMovieName.setText(favoriteVideos.get(position).getVideo_name());
            Glide.with(holder.itemView.getContext()).load(favoriteVideos.get(position).getVideo_pic()).into(((Chasuing_layout) holder).mMovieImg);
            //判断是否点击编辑
            if (flag) {
                //True，显示选择按钮
                ((Chasuing_layout) holder).mCheckDel.setVisibility(View.VISIBLE);
                //判断是否选择全选按钮
                if (mark) {
                    //True，选择全部数据
                    ((Chasuing_layout) holder).mCheckDel.setChecked(true);
                } else {
                    //False 取消全部选中
                    ((Chasuing_layout) holder).mCheckDel.setChecked(false);
                    //取消全部选中要把之前选中全部的list集合中的所有数据清空
                    list.clear();
                    ((Chasuing_layout) holder).mCheckDel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //判断Checkbox 是否选中
                            if (((Chasuing_layout) holder).mCheckDel.isChecked()) {
                                //将选中的影视VID放到集合中
                                list.add(favoriteVideos.get(position).getVideo_vid());
                                //遍历list集合，去重
                                for (int i = 0; i < list.size(); i++) {
                                    for (int j = 0; j < i; j++) {
                                        if (list.get(i).equals(list.get(j))) {
                                            list.remove(i);
                                            i = i - 1;
                                            break;
                                        }
                                    }
                                }
                            } else {
                                //当CheckBox取消选中的时候把当前影视VID在list集合中移除
                                for (int i = 0; i < list.size(); i++) {
                                    if (list.get(i).equals(favoriteVideos.get(position).getVideo_vid())) {
                                        list.remove(i);
                                        break;
                                    }
                                }
                            }
                            //将选中的信息回调到Activity
                            checkCallBack.CheckCallBack(position, list);
                        }
                    });
                }
            } else {
                //False，隐藏选择按钮
                ((Chasuing_layout) holder).mCheckDel.setVisibility(View.GONE);
                //影视布局点击事件执行
                ((Chasuing_layout) holder).mMovieClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!flag){
                            movieCallBack.movieCallback(favoriteVideos.get(position).getVideo_vid(), favoriteVideos.get(position).getVideo_name());
                        }
                    }
                });
            }


        }
    }

    @Override
    public int getItemCount() {
        return favoriteVideos == null ? 0 : favoriteVideos.size();
    }

    private class Chasuing_layout extends RecyclerView.ViewHolder {
        private LinearLayout mMovieClick;
        private RoundImageView mMovieImg;
        private TextView mMovieName;
        private CheckBox mCheckDel;

        public Chasuing_layout(View view) {
            super(view);
            mMovieClick = view.findViewById(R.id.movie_click);
            mMovieImg = view.findViewById(R.id.movie_img);
            mMovieName = view.findViewById(R.id.movie_name);
            mCheckDel = view.findViewById(R.id.check_del);

        }
    }
}
