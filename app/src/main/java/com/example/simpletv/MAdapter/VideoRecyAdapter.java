package com.example.simpletv.MAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletv.Interface.BottomNavCallBack;
import com.example.simpletv.Interface.MovieCallBack;
import com.example.simpletv.Interface.MovieCallBack_Video;
import com.example.simpletv.Interface.PopuClickCallBack;
import com.example.simpletv.NetworkRequestInterface.JavaBean.MovieBean;
import com.example.simpletv.NetworkRequestInterface.JavaBean.MovieInfo;
import com.example.simpletv.NetworkRequestInterface.JavaBean.MovieURL;
import com.example.simpletv.R;
import com.example.simpletv.Tools.MarqueeTextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VideoRecyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MovieCallBack {

    private final static int FLAG_ONE = 0;
    private final static int FLAG_TWO = 1;
    private final static int FLAG_THREE = 2;
    private final static int FLAG_FOUR = 3;
    private final static int FLAG_FIVE=4;
    private BottomNavCallBack bottomNavCallBack;
    private List<MovieInfo.DataBean> movieInfo;
    private MovieURL.DataBean movieUrl;
    private MovieCallBack movieCallBack;
    private PopuClickCallBack popuClickCallBack;
    private List<MovieBean.DataBean> movieBean;
    private MovieCallBack_Video video;
    private int flag=1,mark=1;
    private VideoM3u8TagAdapter adapter_m3u8;
    private VideoOtherTagAdapter adapter_other;

    public void setMovieBean(List<MovieBean.DataBean> movieBean) {
        this.movieBean = movieBean;
    }

    public void setMovieUrl(MovieURL.DataBean movieUrl) {
        this.movieUrl = movieUrl;
    }

    public void setMovieInfo(List<MovieInfo.DataBean> movieInfo) {
        this.movieInfo = movieInfo;
    }

    public VideoRecyAdapter(BottomNavCallBack bottomNavCallBack, MovieCallBack movieCallBack, PopuClickCallBack popuClickCallBack, MovieCallBack_Video video) {
        this.bottomNavCallBack = bottomNavCallBack;
        this.movieCallBack = movieCallBack;
        this.popuClickCallBack=popuClickCallBack;
        this.video=video;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == FLAG_ONE) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_title, null);
            return new Flage_One(view);
        }
        if (viewType == FLAG_TWO) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_funcation, null);
            return new Flage_Two(view);
        }
        if (viewType == FLAG_THREE) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.m3u8list, null);
            return new Flage_Three(view);
        }
        if (viewType == FLAG_FOUR) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.otherlist, null);
            return new Flage_Four(view);
        }
        if(viewType==FLAG_FIVE){
            @SuppressLint("InflateParams") View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.related_video,null);
            return new Flage_Five(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        /***
            *???????????????2021/4/1 5:35 PM
            *?????????xyd
            *??????????????????????????????
            *?????????
            *?????????(Y/N):
        */
        if (holder instanceof Flage_One) {
            ((Flage_One) holder).mMovieName.setText(movieInfo.get(0).getV_name());
            ((Flage_One) holder).mScore.setText(String.valueOf(Float.parseFloat(String.valueOf(movieInfo.get(0).getV_score()))));
            ((Flage_One) holder).mScoreLv.setRating(Float.parseFloat(String.valueOf(movieInfo.get(0).getV_score()))*0.5f);
            ((Flage_One) holder).mDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                popuClickCallBack.PopuCallBack(((Flage_One) holder).mDetail,movieInfo);
                }
            });
        } else if (holder instanceof Flage_Two) {

        } else if (holder instanceof Flage_Three) {
            /***
                *???????????????2021/4/1 5:35 PM
                *?????????xyd
                *?????????mu38??????
                *?????????
                *?????????(Y/N):
            */
            List<String> list=new ArrayList<>();
            for (int i = 0; i < movieUrl.getM3u8List().size(); i++) {
                list.add(movieUrl.getM3u8List().get(i).getName());
            }
            adapter_m3u8 = new VideoM3u8TagAdapter(list);
            ((Flage_Three) holder).video_playBackSource.setAdapter(adapter_m3u8);
            ((Flage_Three) holder).video_playBackSource.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    movieCallBack.movieCallback(position,"m3u8");
                    adapter_other.notifyDataChanged();
                    return true;
                }
            });
        } else if (holder instanceof Flage_Four) {
            /***
                *???????????????2021/4/1 5:36 PM
                *?????????xyd
                *?????????other ??????
                *?????????
                *?????????(Y/N):
            */
            List<String> list=new ArrayList<>();
            for (int i = 0; i < movieUrl.getOtherList().size(); i++) {
                list.add(movieUrl.getOtherList().get(i).getName());
            }
            adapter_other = new VideoOtherTagAdapter(list);
//            adapter.setMovieUrl(movieUrl);
            ((Flage_Four) holder).video_playBackSource.setAdapter(adapter_other);
//            adapter.notifyDataChanged();
            ((Flage_Four) holder).video_playBackSource.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    movieCallBack.movieCallback(position,"other");

                    adapter_m3u8.notifyDataChanged();
                    return true;
                }
            });
        }else if(holder instanceof Flage_Five){
            /***
                *???????????????2021/4/1 5:36 PM
                *?????????xyd
                *???????????????????????????
                *?????????
                *?????????(Y/N):
            */
            SameVideoAdapter adapter=new SameVideoAdapter(movieBean,this);
            RecyclerView.LayoutManager manager=new LinearLayoutManager(holder.itemView.getContext(),RecyclerView.HORIZONTAL,false);
            ((Flage_Five) holder).recycler.setLayoutManager(manager);
            ((Flage_Five) holder).recycler.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
    /***
        *???????????????2021/4/12 4:35 PM
        *?????????xyd
        *???????????????????????????
        *?????????
        *?????????(Y/N):
    */
    @NotNull
    private String GetTheCurrentDate(){
        //??????????????????
        SimpleDateFormat year = new SimpleDateFormat("yyyy");//????????????
        SimpleDateFormat month = new SimpleDateFormat("MM");//????????????
        SimpleDateFormat day=new SimpleDateFormat("dd");//?????????
        String years=year.format(new Date());
        String months=month.format(new Date()).substring(0,1);
        String days=day.format(new Date()).substring(0,1);
        String confirmMonth;
        String confirmDays;
        if(months.equals("0")){
            confirmMonth=month.format(new Date()).substring(1,2);
        }else{
            confirmMonth=month.format(new Date());
        }
        if(days.equals("0")){
            confirmDays=day.format(new Date()).substring(1,2);
        }else{
            confirmDays=day.format(new Date());
        }
        return years+"???"+confirmMonth+"???"+confirmDays+"???";
    }
    @Override
    public int getItemCount() {
        return movieInfo == null ? 0 : movieUrl == null ? 1 : movieBean==null?4:5;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == FLAG_ONE) {
            return FLAG_ONE;
        } else if (position == FLAG_TWO) {
            return FLAG_TWO;
        } else if (position == FLAG_THREE) {
            return FLAG_THREE;
        } else if(position==FLAG_FOUR) {
            return FLAG_FOUR;
        }else{
            return FLAG_FIVE;
        }
    }

    @Override
    public void movieCallback(int flag, String s) {
        video.Video_Callback(flag,s);
    }



    private class Flage_One extends RecyclerView.ViewHolder {

        private RelativeLayout mDetail;
        private TextView mMovieName;
        private TextView mInfo;
        private LinearLayout mLl;
        private TextView mScore;
        private RatingBar mScoreLv;

        public Flage_One(View view) {
            super(view);
            mDetail = view.findViewById(R.id.detail);
            mMovieName = view.findViewById(R.id.movie_name);
            mInfo = view.findViewById(R.id.info);
            mLl = view.findViewById(R.id.ll);
            mScore = view.findViewById(R.id.score);
            mScoreLv = view.findViewById(R.id.score_lv);
        }
    }

    private class Flage_Two extends RecyclerView.ViewHolder {
        private BottomNavigationView mVideoNavFunction;
        private BottomNavigationView.OnNavigationItemSelectedListener video_nav;
        private MarqueeTextView marqueeTextView;

        @SuppressLint("SetTextI18n")
        public Flage_Two(View view) {
            super(view);
            mVideoNavFunction = view.findViewById(R.id.video_nav_Function);
            marqueeTextView=view.findViewById(R.id.marquee);
            marqueeTextView.setText("["+GetTheCurrentDate()+"]"+"?????????????????????????????????????????????????????????????????????????????????????????????????????????");
            video_nav = new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.chasingDrama:
                            bottomNavCallBack.navCallBack(0);
                            return true;
                        case R.id.download:
                            bottomNavCallBack.navCallBack(1);
                            return true;
                        case R.id.sc:
                            bottomNavCallBack.navCallBack(2);
                            return true;
                        default:
                            return false;
                    }
                }
            };
            mVideoNavFunction.setOnNavigationItemSelectedListener(video_nav);
        }
    }

    private class Flage_Three extends RecyclerView.ViewHolder {
        private final TagFlowLayout video_playBackSource;

        public Flage_Three(View view) {
            super(view);
            video_playBackSource = view.findViewById(R.id.video_playBackSource);

        }
    }

    private class Flage_Four extends RecyclerView.ViewHolder {
        private final TagFlowLayout video_playBackSource;

        public Flage_Four(View view) {
            super(view);
            video_playBackSource = view.findViewById(R.id.video_playBackSource);

        }
    }

    private class Flage_Five extends RecyclerView.ViewHolder {

        private final RecyclerView recycler;

        public Flage_Five(View view) {
            super(view);
            recycler = view.findViewById(R.id.same_video_recycler);
        }
    }
}
