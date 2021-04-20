package com.example.simpletv.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simpletv.Interface.ChasingCheckCallBack;
import com.example.simpletv.Interface.MovieCallBack;
import com.example.simpletv.MAdapter.ChasingDramaAdapter;
import com.example.simpletv.R;
import com.example.simpletv.SearchHistoryDataBase.MyApp;
import com.example.simpletv.Tools.DialogUtil;
import com.example.simpletv.Tools.StatusBarUtil;
import com.example.simpletv.Tools.mToast;
import com.example.simpletv.UsersDataBase.FavoriteDao;
import com.example.simpletv.UsersDataBase.FavoriteVideo;

import java.util.ArrayList;
import java.util.List;

public class ChasingDramaActivity extends AppCompatActivity implements View.OnClickListener, ChasingCheckCallBack, MovieCallBack {
    private ImageView mExitImg;
    private TextView mMyTitle;
    private TextView mEdit, text_hint;
    private RecyclerView mChasingRecycler;
    private boolean flag = true;
    private ChasingDramaAdapter adapter;
    private PopupWindow popupWindow;
    private CheckBox mSelectAll;
    private Button mDelete;
    private FavoriteDao favoriteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chasing_drama);
        initView();
        initAdapter();

    }

    private void initAdapter() {
        if (favoriteDao.QueryAll().size() == 0) {
            text_hint.setVisibility(View.VISIBLE);
            mChasingRecycler.setVisibility(View.GONE);
        } else {
            text_hint.setVisibility(View.GONE);
            mChasingRecycler.setVisibility(View.VISIBLE);
            adapter = new ChasingDramaAdapter(this, this);
            GridLayoutManager manager = new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false);
            mChasingRecycler.setLayoutManager(manager);
            adapter.setFavoriteVideos(favoriteDao.QueryAll());
            mChasingRecycler.setAdapter(adapter);
        }

    }

    private void initView() {
        mExitImg = findViewById(R.id.exit_img);
        mMyTitle = findViewById(R.id.my_title);
        text_hint = findViewById(R.id.text_hint);
        mEdit = findViewById(R.id.edit);
        mChasingRecycler = findViewById(R.id.chasing_recycler);
        mMyTitle.setText(R.string.zhuiju);
        favoriteDao = MyApp.getmInstance().getF_db().favoriteDao();
        mExitImg.setOnClickListener(this);
        mEdit.setOnClickListener(this);

        StatusBarUtil.setTranslucentStatus(ChasingDramaActivity.this);
        StatusBarUtil.setStatusBarColor(this, Color.parseColor("#FAFAFA"));
        StatusBarUtil.setImmersiveStatusBar(this, false);
        StatusBarUtil.setStatusBarFontIconDark(this, StatusBarUtil.TYPE_M);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit_img:
                finish();
                overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
                break;
            case R.id.edit:
                if (flag) {
                    adapter.setFlag(true);
                    adapter.notifyDataSetChanged();
                    flag = false;
                    mEdit.setText(R.string.finish);
                    OpenPopuWindow();
                } else {
                    popupWindow.dismiss();
                    adapter.setFlag(false);
                    adapter.setMark(false);
                    adapter.notifyDataSetChanged();
                    flag = true;
                    mEdit.setText(R.string.edit);
                }
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void OpenPopuWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.chasing_popu, null);
        mSelectAll = view.findViewById(R.id.select_all);
        mDelete = view.findViewById(R.id.delete);
        mDelete.setText(getString(R.string.delete) + "(" + 0 + ")");
        mSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                adapter.setMark(isChecked);
                adapter.notifyDataSetChanged();
                if (isChecked) {
                    mDelete.setText(getString(R.string.delete) + "(" + favoriteDao.QueryAll().size() + ")");

                } else {
                    mDelete.setText(getString(R.string.delete) + "(" + 0 + ")");
                }
            }
        });
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectAll.isChecked()) {
                    //删除所有
                    List<Integer>list=new ArrayList<>();
                    BulletFrame(0,"是否删除所有追剧?",list);
                }else if(mDelete.getText().equals("删除(0)")){
                    mToast.single("请勾选你要删除的电影",ChasingDramaActivity.this);
                }

            }
        });
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(false);
        popupWindow.setTouchable(true);
        popupWindow.setAnimationStyle(R.style.take_photo_anim);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    /***
     *创建时间：2021/4/1 4:09 PM
     *作者：xyd
     *描述：监听系统返回键
     *参数：
     *返回值(Y/N):
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//如果返回键按下
                popupWindow.dismiss();
                finish();
                overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
        }
        return super.onKeyDown(keyCode, event);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void CheckCallBack(int position, List<Integer> VidList) {
//        Log.i("xydxyd", "CheckCallBack: "+position+"----"+VidList);

        mDelete.setText(getString(R.string.delete) + "(" + VidList.size() + ")");

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(ChasingDramaActivity.this, ""+position, Toast.LENGTH_SHORT).show();
                if(VidList.size()==0){
                    mToast.single("请勾选你要删除的电影",ChasingDramaActivity.this);
                }else{
                    if (mSelectAll.isChecked()) {
                        //删除所有
                        BulletFrame(0,"是否删除所有追剧?",VidList);
                    } else {
                        //删除选中数据
                        BulletFrame(1,"是否删除选中的"+VidList.size()+"条数据?",VidList);
                    }
                }
            }
        });
    }

    //播放跳转
    @Override
    public void movieCallback(int flag, String s) {
        JumpVideo(flag, s);
    }

    private void JumpVideo(int position, String title) {
        Intent intent = new Intent(ChasingDramaActivity.this, PlayerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("vid", position);
        bundle.putString("title", title);
        intent.putExtra("vid", bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.zoomin_right, R.anim.zoomout_left);
    }

    /***
     *创建时间：2021/3/16 10:33 PM
     *作者：xyd
     *描述：删除追剧数据弹框
     *参数：
     *返回值(Y/N):
     */
    private void BulletFrame(int i,String s,List<Integer> list) {
        DialogUtil dialogUtil = new DialogUtil(ChasingDramaActivity.this, this);
        dialogUtil.dialog(s , getString(R.string.cancel), getString(R.string.confirm));
        dialogUtil.setOnItemClickListener(new DialogUtil.OnItemClickListener() {
            @Override
            public void onItemCancelClick() {
                //取消
            }

            @Override
            public void onItemConfirmClick() {
                //确定
                //删除所有，刷新适配器
                switch (i){
                    case 0:
                        favoriteDao.DeleteAll();
                        adapter.setFavoriteVideos(favoriteDao.QueryAll());
                        adapter.notifyDataSetChanged();
                        if (favoriteDao.QueryAll().size() == 0) {
                            text_hint.setVisibility(View.VISIBLE);
                            mChasingRecycler.setVisibility(View.GONE);
                            popupWindow.dismiss();
                        }
                        break;
                    case 1:
                        for (int i = 0; i < list.size(); i++) {
                            FavoriteVideo favoriteVideo = favoriteDao.SearchForID(list.get(i));
                            favoriteDao.DELETE(favoriteVideo);
                            adapter.setFavoriteVideos(favoriteDao.QueryAll());
                            adapter.notifyDataSetChanged();
                            if (favoriteDao.QueryAll().size() == 0) {
                                text_hint.setVisibility(View.VISIBLE);
                                mChasingRecycler.setVisibility(View.GONE);
                                popupWindow.dismiss();
                            }
                        }
                        break;
                }

            }
        });
    }
}