package com.example.simpletv.MAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.simpletv.Interface.MovieCallBack;
import com.example.simpletv.R;

import java.util.List;

public class SetListAdapter extends BaseAdapter {
    private MovieCallBack movieCallBack;
    private List<String> list;

    public SetListAdapter(MovieCallBack movieCallBack) {
        this.movieCallBack = movieCallBack;
    }

    public void setList(List<String> list) {
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"InflateParams", "ViewHolder"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_list_item, null);
        RelativeLayout mItemClick = convertView.findViewById(R.id.item_click);
        final TextView mSetItemText = convertView.findViewById(R.id.set_item_text);
        ImageView mSetItemImg = convertView.findViewById(R.id.set_item_img);
        mSetItemText.setText(list.get(position));
        mItemClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieCallBack.movieCallback(position,mSetItemText.getText().toString());
            }
        });
        return convertView;
    }
}
