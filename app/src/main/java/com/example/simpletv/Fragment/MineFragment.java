package com.example.simpletv.Fragment;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.simpletv.Activity.CacheActivity;
import com.example.simpletv.Activity.FavoritesActivity;
import com.example.simpletv.Activity.LoginActivity;
import com.example.simpletv.Activity.PersonalInformationActivity;
import com.example.simpletv.Activity.SearchActivity;
import com.example.simpletv.Activity.SetActivity;
import com.example.simpletv.Activity.WatchHistoryActivity;
import com.example.simpletv.Activity.ChasingDramaActivity;
import com.example.simpletv.NetworkRequestInterface.Api;
import com.example.simpletv.R;
import com.example.simpletv.SearchHistoryDataBase.MyApp;
import com.example.simpletv.Tools.DialogUtil;
import com.example.simpletv.Tools.mToast;
import com.example.simpletv.UsersDataBase.UsersDao;
import com.example.simpletv.UsersDataBase.Users_person;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.util.List;

import gdut.bsx.share2.FileUtil;
import gdut.bsx.share2.Share2;
import gdut.bsx.share2.ShareContentType;

import static android.app.Activity.RESULT_OK;

public class MineFragment extends Fragment implements View.OnClickListener {
    private ImageView mExitImg;
    private TextView mMyTitle;
    private LinearLayout mLogin;
    private RoundedImageView mTouxiang;
    private TextView mNickname;
    private BottomNavigationView mMineNavFunction;
    private BottomNavigationView mMineNavOther;
    private UsersDao usersDao;
    private String shpf;
    private Uri shareFileUrl;
    private static final int FILE_SELECT_CODE = 100;
    private static final int REQUEST_SHARE_FILE_CODE = 120;
    private static final int REQUEST_WRITE_STORAGE_PERMISSION = 121;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_mine, null);
        initView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ReadDatabase();
    }

    @Override
    public void onPause() {
        super.onPause();
//        ReadDatabase();
    }

    @Override
    public void onResume() {
        super.onResume();
        ReadDatabase();
    }

    @Override
    public void onStop() {
        super.onStop();
//        ReadDatabase();

    }

    private void ReadDatabase() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginSuccess", Context.MODE_PRIVATE);
        shpf = sharedPreferences.getString("Username", "");
        if (sharedPreferences.getBoolean("islogin", false)) {
            Users_person users_person = usersDao.QueryAll(shpf);
            if (users_person.getNickname() == null) {
                mNickname.setText(users_person.getAccountNumber());
            } else {
                mNickname.setText(users_person.getNickname());
            }
            if (users_person.getHeadPortrait() == null) {
                mTouxiang.setImageResource(R.drawable.nologin);
            } else {
                Glide.with(getContext()).load(users_person.getHeadPortrait()).into(mTouxiang);

            }
            if (users_person.getSignature() == null) {
                //个性签名

            } else {
                //个性签名
            }
        } else {
            mNickname.setText(R.string.login_regist);
            mTouxiang.setImageResource(R.drawable.nologin);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initView(View view) {
        mExitImg = view.findViewById(R.id.search_img);
        mMyTitle = view.findViewById(R.id.my_title);
        mLogin = view.findViewById(R.id.login);
        mTouxiang = view.findViewById(R.id.touxiang);
        mNickname = view.findViewById(R.id.nickname);
        mMineNavFunction = view.findViewById(R.id.mine_nav_Function);
        mMineNavOther = view.findViewById(R.id.mine_nav_other);
        usersDao = MyApp.getmInstance().getU_db().usersDao();
        //设置导航切换监听
        mMineNavFunction.setOnNavigationItemSelectedListener(function_nav);
        mMineNavOther.setOnNavigationItemSelectedListener(other_nav);

        //设置标题栏图片
        mExitImg.setBackground(getResources().getDrawable(R.drawable.set));
        //点击事件
        mExitImg.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        //设置标题栏内容
        mMyTitle.setText(R.string.personal_center);


    }

    /***
     *创建时间：2021/3/7 3:52 PM
     *作者：xyd
     *描述：功能选项nav
     *参数：
     *返回值(Y/N):
     */
    private BottomNavigationView.OnNavigationItemSelectedListener function_nav = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.loading:
                    startActivity(new Intent(getActivity(), CacheActivity.class));
                    getActivity().overridePendingTransition(R.anim.zoomin_right, R.anim.zoomout_left);
                    return true;
                case R.id.watch_history:
                    startActivity(new Intent(getActivity(), WatchHistoryActivity.class));
                    getActivity().overridePendingTransition(R.anim.zoomin_right, R.anim.zoomout_left);
                    return true;
                case R.id.zhuiju:
                    startActivity(new Intent(getActivity(), ChasingDramaActivity.class));
                    getActivity().overridePendingTransition(R.anim.zoomin_right, R.anim.zoomout_left);
                    return true;
                case R.id.shoucang:
                    startActivity(new Intent(getActivity(), FavoritesActivity.class));
                    getActivity().overridePendingTransition(R.anim.zoomin_right, R.anim.zoomout_left);
                    return true;
            }
            return false;
        }
    };
    /***
     *创建时间：2021/3/7 3:53 PM
     *作者：xyd
     *描述：其他选项nav
     *参数：
     *返回值(Y/N):
     */
    private BottomNavigationView.OnNavigationItemSelectedListener other_nav = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.fengxiang:
//                    Toast.makeText(getContext(), R.string.fenxiang, Toast.LENGTH_SHORT).show();
                    ShowShare();
                    return true;
                case R.id.contact:
                    // 从API11开始android推荐使用android.content.ClipboardManager
                    // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                    ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText("1826146131");
                    mToast.single("已将作者QQ复制到剪贴板",getActivity());
                    return true;
                case R.id.version:
//                    Toast.makeText(getContext(), R.string.versionInformation, Toast.LENGTH_SHORT).show();
                    ShowVersion();
                    return true;
                case R.id.setport:
//                    Toast.makeText(getContext(), R.string.setport, Toast.LENGTH_SHORT).show();
                    ShowPort();
                    return true;
            }
            return false;
        }
    };

    private void ShowPort() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int widths = size.x;
        int height = size.y;
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.mine_popu, null);
        TextView textView=inflate.findViewById(R.id.text_info);
        textView.setText(Api.getHOST());
        PopupWindow popupWindow = new PopupWindow(inflate,ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.take_photo_anim);
        popupWindow.showAtLocation(mMineNavOther, Gravity.CENTER,0,height/4);
    }

    private void ShowShare() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int widths = size.x;
        int height = size.y;
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.share_popu, null);

        RadioButton mMainQq = inflate.findViewById(R.id.main_qq);
        RadioButton mMainWchart = inflate.findViewById(R.id.main_wchart);

        mMainQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mToast.single("分享到QQ", getContext());
//                PackageManager packageManager=getActivity().getApplicationContext().getPackageManager();
////                ApplicationInfo applicationInfo=new ApplicationInfo();
////                List<PackageInfo> packageInfos=packageManager.getInstalledPackages(0);
////                packageManager.getApplicationLabel(applicationInfo);
////                PackageInfo packageInfo;
////                File apkFile=new File(packageInfo.applicationInfo.sourceDir);
////                Intent intent = new Intent();
////                intent.setAction(Intent.ACTION_SEND);
////                intent.setType("*/*");
////                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(apkFile));
////                startActivity(intent);
//                try {
//                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo("com.example.simpletv", 0);
//                } catch (PackageManager.NameNotFoundException e) {
//                    e.printStackTrace();
//                }
//                openFileChooser();
//                new Share2.Builder(getActivity())
//                        .setContentType(ShareContentType.FILE)
//                        .setShareFileUri(getShareFileUri())
//                        .setTitle("分享文件")
//                        .setOnActivityResult(REQUEST_SHARE_FILE_CODE)
//                        .build()
//                        .shareBySystem();
            }
        });
        mMainWchart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mToast.single("分享到微信", getContext());
            }
        });

        PopupWindow popupWindow = new PopupWindow(inflate, widths / 2, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.take_photo_anim);
        popupWindow.showAtLocation(mMineNavOther, Gravity.CENTER, 0, height/4);
    }
    public Uri getShareFileUri() {
        if (shareFileUrl == null) {
            mToast.single("请选择要共享的文件。",getContext());
        }
        return shareFileUrl;
    }

    private void ShowVersion() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int widths = size.x;
        int height = size.y;
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.mine_popu, null);
        PopupWindow popupWindow = new PopupWindow(inflate,widths/3, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.take_photo_anim);
        popupWindow.showAtLocation(mMineNavOther, Gravity.CENTER,0,height/4);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_img:
                //设置点击事件
                startActivity(new Intent(getActivity(), SetActivity.class));
                getActivity().overridePendingTransition(R.anim.zoomin_right, R.anim.zoomout_left);
                break;
            case R.id.login:
                if (shpf.equals("")) {
                    //登录注册点击事件
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().overridePendingTransition(R.anim.zoomin_right, R.anim.zoomout_left);
                } else {
                    //个人信息
                    startActivity(new Intent(getActivity(), PersonalInformationActivity.class));
                    getActivity().overridePendingTransition(R.anim.zoomin_right, R.anim.zoomout_left);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==FILE_SELECT_CODE && requestCode  ==RESULT_OK){
            shareFileUrl=data.getData();
        }else if(requestCode==REQUEST_SHARE_FILE_CODE){

        }
    }
    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "选择文件"), FILE_SELECT_CODE);
            getActivity().overridePendingTransition(0, 0);
        } catch (Exception ex) {
            // Potentially direct the user to the Market with OnProgressChangeListener Dialog
            Toast.makeText(getContext(), "请先选择文件管理器", Toast.LENGTH_SHORT).show();
        }
    }

}
