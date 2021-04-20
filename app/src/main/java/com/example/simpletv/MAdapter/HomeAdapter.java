package com.example.simpletv.MAdapter;

import android.graphics.Outline;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.simpletv.Interface.BannerClickCallBack;
import com.example.simpletv.Interface.JumpCallBack;
import com.example.simpletv.Interface.LayoutJumpCallBack;
import com.example.simpletv.Interface.MovieCallBack;
import com.example.simpletv.Interface.RecyclerCallBack;
import com.example.simpletv.NetworkRequestInterface.JavaBean.MovieBean;
import com.example.simpletv.R;
import com.example.simpletv.Tools.GlideImageLoad;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/***
 *创建时间：2021/1/31 5:53 PM
 *作者：xyd
 *描述：主页的RecyclerView适配器，将recyclerview分成4个Item，将4个Item分别命名为
 *Flag_ONE、FLAG_TWO、FLAG_THREE、FLAG_FOUR
 *参数：
 *返回值(Y/N):
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RecyclerCallBack {
    private final static int FLAG_ONE = 0;
    private final static int FLAG_TWO = 1;
    private final static int FLAG_THREE = 2;
    private final static int FLAG_FOUR = 3;
    private final static String PIC_KEY = "img";
    private final static String STRING = "name";
    private final static String V_ID = "vid";
    private static final String TAG = "HomeAdapterEx";
    private ArrayList<String> picture = new ArrayList<>();
    private ArrayList<String> title = new ArrayList<>();
    private BannerClickCallBack bannerClickCallBack;
    private List<MovieBean.DataBean> movieBean;
    private MovieCallBack movieCallBack;
    private ArrayList<Map<String, String>> recommendBean;
    private LayoutJumpCallBack layoutJumpCallBack;
    private JumpCallBack jumpCallBack;

    public void setPicture(ArrayList<String> picture) {
        this.picture = picture;
    }

    public void setTitle(ArrayList<String> title) {
        this.title = title;
    }

    public void setRecommendBean(ArrayList<Map<String, String>> recommendBean) {
        this.recommendBean = recommendBean;
    }

    public void setMovieBean(List<MovieBean.DataBean> movieBean) {
        this.movieBean = movieBean;
    }

    public HomeAdapter(BannerClickCallBack bannerClickCallBack, MovieCallBack movieCallBack, LayoutJumpCallBack layoutJumpCallBack,JumpCallBack jumpCallBack) {
        this.bannerClickCallBack = bannerClickCallBack;
        this.movieCallBack = movieCallBack;
        this.layoutJumpCallBack = layoutJumpCallBack;
        this.jumpCallBack=jumpCallBack;
    }

    //数据实时添加



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == FLAG_ONE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_banner, null);
            return new Flag_One_View(view);
        }
        if (viewType == FLAG_TWO) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_jrtj, null);
            return new Flag_Two_View(view);
        }
        if (viewType == FLAG_THREE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.foryou, null);
            return new Flag_Three_View(view);
        }
        if (viewType == FLAG_FOUR) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommendedtoyou, null);
            return new Flag_Four_View(view);
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof Flag_One_View) {
            //设置banner样式
            ((Flag_One_View) holder).mHomeBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
            //设置图片加载器
            ((Flag_One_View) holder).mHomeBanner.setImageLoader(new GlideImageLoad());
            //设置图片集合
            ((Flag_One_View) holder).mHomeBanner.setImages(picture);
            //设置banner动画效果
            ((Flag_One_View) holder).mHomeBanner.setBannerAnimation(Transformer.ZoomOutSlide);
            //设置标题集合（当banner样式有显示title时）
            ((Flag_One_View) holder).mHomeBanner.setBannerTitles(title);
            //设置自动轮播，默认为true
            ((Flag_One_View) holder).mHomeBanner.isAutoPlay(true);
            //设置轮播时间
            ((Flag_One_View) holder).mHomeBanner.setDelayTime(3000);
            //设置指示器位置（当banner模式中有指示器时）
            ((Flag_One_View) holder).mHomeBanner.setIndicatorGravity(BannerConfig.RIGHT);
            //给banner设置圆角
            ((Flag_One_View) holder).mHomeBanner.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 20);
                }
            });
            ((Flag_One_View) holder).mHomeBanner.setClipToOutline(true);
            //banner设置方法全部调用完毕时最后调用
            ((Flag_One_View) holder).mHomeBanner.start();
            //banner点击事件
            ((Flag_One_View) holder).mHomeBanner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    bannerClickCallBack.bannerCallback(position,title.get(position));
                }
            });

            ((Flag_One_View) holder).search_home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layoutJumpCallBack.LayoutJump(((Flag_One_View) holder).search_home);
                }
            });
        } else if (holder instanceof Flag_Two_View) {
            //将大家都在看的RecyclerView设置适配器，将该适配器设置为横向滑动

            ((Flag_Two_View) holder).mRecommendRecycler.setOverScrollMode(View.OVER_SCROLL_NEVER);//去掉滑动水波纹
            HomeAdapter_H homeAdapter_h = new HomeAdapter_H(this);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(holder.itemView.getContext(), RecyclerView.HORIZONTAL, false);
            ((Flag_Two_View) holder).mRecommendRecycler.setLayoutManager(manager);
            homeAdapter_h.setMovieBean(movieBean);
            ((Flag_Two_View) holder).mRecommendRecycler.setAdapter(homeAdapter_h);
            homeAdapter_h.notifyDataSetChanged();
            ((Flag_Two_View) holder). mRecommend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jumpCallBack.goJump(((Flag_Two_View) holder).mRecommend);
                }
            });
        } else if (holder instanceof Flag_Three_View) {
            ((Flag_Three_View) holder).mRecommendTxt.setText(R.string.foryou);

        } else if (holder instanceof Flag_Four_View) {
            Glide.with(holder.itemView.getContext()).load(recommendBean.get(position - 3).get(PIC_KEY)).into(((Flag_Four_View) holder).mMovieImg);
            ((Flag_Four_View) holder).mMovieName.setText(recommendBean.get(position - 3).get(STRING));
            ((Flag_Four_View) holder).movie_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieCallBack.movieCallback(Integer.parseInt(recommendBean.get(position - 3).get(V_ID)), ((Flag_Four_View) holder).mMovieName.getText().toString());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return picture == null ? 0 : recommendBean == null ? 0 : recommendBean.size() + 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return FLAG_ONE;
        } else if (position == 1) {
            return FLAG_TWO;
        } else if (position == 2) {
            return FLAG_THREE;
        } else {
            return FLAG_FOUR;
        }
    }

    @Override
    public void callback(int flag, String movieName) {
        movieCallBack.movieCallback(flag, movieName);
    }


    private class Flag_One_View extends RecyclerView.ViewHolder {
        private LinearLayout search_home;
        private Banner mHomeBanner;

        public Flag_One_View(View view) {
            super(view);
            search_home = view.findViewById(R.id.home_search);
            mHomeBanner = view.findViewById(R.id.home_banner);
        }
    }

    private class Flag_Two_View extends RecyclerView.ViewHolder {
        private LinearLayout mRecommend;
        private TextView mRecommendTxt;
        private RecyclerView mRecommendRecycler;

        public Flag_Two_View(View view) {
            super(view);
            mRecommend = view.findViewById(R.id.recommend);
            mRecommendTxt = view.findViewById(R.id.recommend_txt);
            mRecommendRecycler = view.findViewById(R.id.recommend_recycler);
        }
    }

    private class Flag_Four_View extends RecyclerView.ViewHolder {
        private ImageView mMovieImg;
        private TextView mMovieName;
        private LinearLayout movie_click;

        public Flag_Four_View(View view) {
            super(view);
            mMovieImg = view.findViewById(R.id.movie_img);
            mMovieName = view.findViewById(R.id.movie_name);
            movie_click = view.findViewById(R.id.movie_click);

        }
    }

    private class Flag_Three_View extends RecyclerView.ViewHolder {
        private LinearLayout mRecommend;
        private TextView mRecommendTxt;


        public Flag_Three_View(View view) {
            super(view);
            mRecommend = view.findViewById(R.id.recommend);
            mRecommendTxt = view.findViewById(R.id.recommend_txt);


        }
    }
}
