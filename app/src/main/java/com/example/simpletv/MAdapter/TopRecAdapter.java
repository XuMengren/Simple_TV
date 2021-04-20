package com.example.simpletv.MAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletv.Interface.OnClickCallBack;
import com.example.simpletv.R;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class TopRecAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> typelist;
    private List<String> regionlist;
    private List<String> yearslist;
    private List<String> languagelist;

    public void setLanguagelist(List<String> languagelist) {
        this.languagelist = languagelist;
    }

    private OnClickCallBack onClickCallBack;

    public TopRecAdapter(OnClickCallBack onClickCallBack) {
        this.onClickCallBack = onClickCallBack;
    }

    public void setTypelist(List<String> typelist) {
        this.typelist = typelist;
    }

    public void setRegionlist(List<String> regionlist) {
        this.regionlist = regionlist;
    }

    public void setYearslist(List<String> yearslist) {
        this.yearslist = yearslist;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.part_tabs, null);
        return new TopRec(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        /***
         *创建时间：2021/3/4 3:12 PM
         *作者：xyd
         *描述：通过position 设置tabs的内容和点击事件
         *参数：
         *返回值(Y/N):
         */
        switch (position) {
            case 0:
//                ((TopRec)holder).mFenleiTabs.getTabAt(0);
                ((TopRec)holder).mFenleiTabs.setScrollPosition(0,0,true);
                setTabs(holder, typelist);
                setOnclik(holder, position);
                break;
            case 1:
//                ((TopRec)holder).mFenleiTabs.getTabAt(0);
                setTabs(holder, regionlist);
                setOnclik(holder, position);
                break;
            case 2:
//                ((TopRec)holder).mFenleiTabs.getTabAt(0);
                setTabs(holder, languagelist);
                setOnclik(holder, position);
                break;
            case 3:
//                ((TopRec)holder).mFenleiTabs.getTabAt(0);
                setTabs(holder, yearslist);
                setOnclik(holder, position);
                break;
        }
    }

    private void setOnclik(RecyclerView.ViewHolder holder, final int position) {
        ((TopRec) holder).mFenleiTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int i = tab.getPosition();
                if (i > 12) {
                    i++;
                    onClickCallBack.onClick(i, position, tab.getText().toString());
                } else {
                    onClickCallBack.onClick(i, position, tab.getText().toString());
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setTabs(RecyclerView.ViewHolder holder, List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            ((TopRec) holder).mFenleiTabs.addTab(((TopRec) holder).mFenleiTabs.newTab().setText(list.get(i)));
        }
        ((TopRec) holder).mFenleiTabs.getTabAt(0).select();
    }

    @Override
    public int getItemCount() {
        return typelist == null ? 0 : 4;
    }

    private class TopRec extends RecyclerView.ViewHolder {
        private TabLayout mFenleiTabs;

        public TopRec(View view) {
            super(view);

            mFenleiTabs = view.findViewById(R.id.part_tabs);

        }
    }
}
