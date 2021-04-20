package com.example.simpletv.MAdapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletv.Interface.HotSearchCallBack;
import com.example.simpletv.NetworkRequestInterface.JavaBean.MovieBean;
import com.example.simpletv.R;

import java.util.List;

public class HotSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MovieBean.DataBean> movieBean;
    private HotSearchCallBack hotSearchCallBack;

    public HotSearchAdapter(HotSearchCallBack hotSearchCallBack) {
        this.hotSearchCallBack = hotSearchCallBack;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hot_search_item, null);
        return new HotSearch(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((HotSearch)holder).mHotSearchTxt.setText(movieBean.get(position).getV_name());
        //设置热门搜索排名字体的样式
        int i;
        i=position+1;
        switch (i){
            case 1:
                ((HotSearch) holder).mRankTxt.setText(String.valueOf(i));
                ((HotSearch) holder).mRankTxt.setTextAppearance(R.style.hot_text_style_one);
                break;
            case 2:
                ((HotSearch) holder).mRankTxt.setText(String.valueOf(i));
                ((HotSearch) holder).mRankTxt.setTextAppearance(R.style.hot_text_style_two);
                break;
            case 3:
                ((HotSearch) holder).mRankTxt.setText(String.valueOf(i));
                ((HotSearch) holder).mRankTxt.setTextAppearance(R.style.hot_text_style_three);
                break;
            default:
                ((HotSearch) holder).mRankTxt.setText(String.valueOf(i));
                ((HotSearch) holder).mRankTxt.setTextAppearance(R.style.hot_text_style_default);
        }
    }

    @Override
    public int getItemCount() {
        return movieBean==null?0:movieBean.size();
    }

    public void setMovieName(List<MovieBean.DataBean> movieBean) {
        this.movieBean=movieBean;
    }

    private class HotSearch extends RecyclerView.ViewHolder {
        private LinearLayout mHotLl;
        private TextView mHotSearchTxt;
        private TextView mRankTxt;
        public HotSearch(View view) {
            super(view);

            mHotLl = view.findViewById(R.id.hot_ll);
            mHotSearchTxt = view.findViewById(R.id.hot_search_txt);
            mRankTxt=view.findViewById(R.id.rank);

            mHotLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hotSearchCallBack.getHot(mHotSearchTxt.getText().toString().trim());
                }
            });
        }
    }
}
