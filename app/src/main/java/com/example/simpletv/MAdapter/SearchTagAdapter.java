package com.example.simpletv.MAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simpletv.Interface.SearchHistoryCallBack;
import com.example.simpletv.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

public class SearchTagAdapter extends TagAdapter<String> {
    private SearchHistoryCallBack historyCallBack;
    public SearchTagAdapter(List<String> datas,SearchHistoryCallBack historyCallBack) {
        super(datas);
        this.historyCallBack=historyCallBack;
    }

    @Override
    public View getView(FlowLayout parent, int position, String s) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.flowlayout_item, null);
        ImageView del = inflate.findViewById(R.id.history_del);
        final TextView txt = inflate.findViewById(R.id.history_txt);
        txt.setText(s);
        //删除某一个历史记录
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyCallBack.getHistoryName(0,txt.getText().toString());
            }
        });
        //点击某一个历史记录
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyCallBack.getHistoryName(1,txt.getText().toString());
            }
        });
        return inflate;
    }

}
