package com.example.simpletv.MAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletv.Interface.RecyclerCallBack;
import com.example.simpletv.R;

import java.util.List;
import java.util.Map;

public class EditRecyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Map<String, String>> editListMap;
    private RecyclerCallBack recyclerCallBack;
    private List<String> editList;
    private static final String SIGNATURE = "signature";
    private static final String NICKNAME = "nickname";
    private static final String ADDRESS = "address";
    private static final String SEX = "sex";
    private static final String DATEOFBIRTH = "birth";
    private static final String EMAIL = "email";

    public EditRecyAdapter(List<Map<String, String>> editListMap, RecyclerCallBack recyclerCallBack, List<String> editList) {
        this.editListMap = editListMap;
        this.recyclerCallBack = recyclerCallBack;
        this.editList = editList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_recycler_layout, null);
        return new EditRecy(view);
    }

    /*
    * editList.add("头像");
            editList.add("签名");
            editList.add("昵称");
            editList.add("性别");
            editList.add("生日");
            editList.add("地址");
            editList.add("邮箱");
            editList.add("背景墙");
    * */
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        ((EditRecy) holder).mSetItemText.setText(editList.get(position));
        switch (position) {
            case 1:
                ((EditRecy) holder).context_click.setText(editListMap.get(0).get(SIGNATURE));
                break;
            case 2:
                ((EditRecy) holder).context_click.setText(editListMap.get(0).get(NICKNAME));
                break;
            case 3:
                ((EditRecy) holder).context_click.setText(editListMap.get(0).get(SEX));
                break;
            case 4:
                ((EditRecy) holder).context_click.setText(editListMap.get(0).get(DATEOFBIRTH));
                break;
            case 5:
                ((EditRecy) holder).context_click.setText(editListMap.get(0).get(ADDRESS));
                break;
            case 6:
                ((EditRecy) holder).context_click.setText(editListMap.get(0).get(EMAIL));
                break;
        }
        ((EditRecy) holder).mItemClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerCallBack.callback(position,((EditRecy) holder).context_click.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return editList == null ? 0 : editList.size();
    }

    private class EditRecy extends RecyclerView.ViewHolder {
        private RelativeLayout mItemClick;
        private TextView mSetItemText;
        private ImageView mSetItemImg;
        private final TextView context_click;

        public EditRecy(View view) {
            super(view);

            context_click = view.findViewById(R.id.context_click);
            mItemClick = view.findViewById(R.id.item_click);
            mSetItemText = view.findViewById(R.id.set_item_text);
            mSetItemImg = view.findViewById(R.id.set_item_img);

        }
    }
}
