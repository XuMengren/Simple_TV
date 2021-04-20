package com.example.simpletv.MAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.simpletv.Interface.RecyclerCallBack;
import com.example.simpletv.NetworkRequestInterface.JavaBean.FuzzySearchBean;
import com.example.simpletv.R;
import com.example.simpletv.Tools.MatchSearchField;

import java.util.List;
/***
    *创建时间：2021/1/31 4:52 PM
    *作者：xyd
    *描述：模糊搜索的ListView适配器
    *参数：
    *返回值(Y/N):N
*/
public class FuzzySearchAdapter extends BaseAdapter {
    private LinearLayout mFuzzySearchView;
    private TextView mFuzzySearchText;
    private List<FuzzySearchBean.DataBean> data;
    private RecyclerCallBack recyclerCallBack;
    private String mKeyWord;

    public void setmKeyWord(String mKeyWord) {
        this.mKeyWord = mKeyWord;
    }

    public FuzzySearchAdapter(RecyclerCallBack recyclerCallBack) {
        this.recyclerCallBack = recyclerCallBack;
    }

    public List<FuzzySearchBean.DataBean> getData() {
        return data;
    }

    public void setData(List<FuzzySearchBean.DataBean> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"InflateParams", "ViewHolder"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fuzzy_search_items, null);
        mFuzzySearchView = convertView.findViewById(R.id.fuzzy_search_view);
        mFuzzySearchText=convertView.findViewById(R.id.fuzzy_search_text);
        MatchSearchField.matcherSearchTitle(data.get(position).getV_name(),mKeyWord,mFuzzySearchText);//将搜索匹配的文字设置提示颜色
        mFuzzySearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerCallBack.callback(position,data.get(position).getV_name());
            }
        });

        return convertView;
    }
}
