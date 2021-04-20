package com.example.simpletv.Activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.os.EnvironmentCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.simpletv.MAdapter.EditRecyAdapter;
import com.example.simpletv.Interface.RecyclerCallBack;
import com.example.simpletv.R;
import com.example.simpletv.SearchHistoryDataBase.MyApp;
import com.example.simpletv.Tools.ActionSheet;
import com.example.simpletv.Tools.StatusBarUtil;
import com.example.simpletv.UsersDataBase.UsersDao;
import com.example.simpletv.UsersDataBase.Users_person;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class EditPersonalInfoActivity extends AppCompatActivity implements View.OnClickListener, RecyclerCallBack {

    private ImageView mExitImg;
    private TextView mMyTitle;
    private RecyclerView edit_recycler;
    private List<Map<String, String>> editListMap = new ArrayList<>();
    private UsersDao usersDao;
    private Map<String, String> map;
    private ActionSheet actionSheet;
    private List<String> editList = new ArrayList<>();
    private List<Object> sexList = new ArrayList<>();
    private static final String SIGNATURE = "signature";
    private static final String NICKNAME = "nickname";
    private static final String ADDRESS = "address";
    private static final String SEX = "sex";
    private static final String DATEOFBIRTH = "birth";
    private static final String EMAIL = "email";
    private String shpf;
    private EditRecyAdapter editRecyAdapter;
    private Users_person users_person = new Users_person();
    private static final int PERMISSION_CAMERA_REQUEST_CODE = 0x00000012;
    //用来标记用户选择的是头像还是背景墙
    private static int FLAG = 0;
    //用于保存拍照图片的uri
    private Uri mCameraUri;

    // 用于保存图片的文件路径，Android 10以下使用图片路径访问图片
    private String mCameraImagePath;

    // 是否是Android 10以上手机
    private boolean isAndroidQ = Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpersonalinfo);
        initView();
        setData();
        readDatabase();
        initAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        RefreshData();
        initAdapter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        RefreshData();
        initAdapter();
    }

    /***
     *创建时间：2021/3/15 10:12 PM
     *作者：xyd
     *描述：刷新数据，用于返回到该界面刷新该界面的数据
     *参数：
     *返回值(Y/N):
     */
    private void RefreshData() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginSuccess", Context.MODE_PRIVATE);
        Users_person users_person = usersDao.QueryAll(sharedPreferences.getString("Username", ""));
        if (users_person.getSignature() == null) {
            map.put(SIGNATURE, "快来设置属于自己的个性签名吧！");
        } else {
            map.put(SIGNATURE, users_person.getSignature());
        }
        if (users_person.getNickname() == null) {
            map.put(NICKNAME, users_person.getAccountNumber());
        } else {
            map.put(NICKNAME, users_person.getNickname());
        }
        if (users_person.getAddress() == null) {
            map.put(ADDRESS, getResources().getString(R.string.default_address));
        } else {
            map.put(ADDRESS, users_person.getAddress());
        }
        if (users_person.getSex() == null) {
            map.put(SEX, getResources().getString(R.string.default_sex));
        } else {
            map.put(SEX, users_person.getSex());
        }
        if (users_person.getDateOfBirth() == null) {
            map.put(DATEOFBIRTH, getResources().getString(R.string.default_birth));
        } else {
            map.put(DATEOFBIRTH, users_person.getDateOfBirth());
        }
        if (users_person.getEmail() == null) {
            map.put(EMAIL, getString(R.string.input_email));
        } else {
            map.put(EMAIL, users_person.getEmail());
        }
        editListMap.add(map);
    }


    private void setData() {
        editList.add(getString(R.string.head));
        editList.add(getString(R.string.signature));
        editList.add(getString(R.string.nickname));
        editList.add(getString(R.string.sex));
        editList.add(getString(R.string.birth));
        editList.add(getString(R.string.address));
        editList.add(getString(R.string.email));
        editList.add(getString(R.string.bg_pic));

        sexList.add(getString(R.string.male));
        sexList.add(getString(R.string.female));
    }

    /***
     *创建时间：2021/3/15 7:22 PM
     *作者：xyd
     *描述：读取数据库，将数据放到集合中
     *参数：
     *返回值(Y/N):
     */
    private void readDatabase() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginSuccess", Context.MODE_PRIVATE);
        shpf = sharedPreferences.getString("Username", "");
        if (sharedPreferences.getBoolean("islogin", false)) {
            map = new HashMap<>();
            Users_person users_person = usersDao.QueryAll(shpf);
            if (users_person.getSignature() == null) {
                map.put(SIGNATURE, "快来设置属于自己的个性签名吧！");
            } else {
                map.put(SIGNATURE, users_person.getSignature());
            }
            if (users_person.getNickname() == null) {
                map.put(NICKNAME, users_person.getAccountNumber());
            } else {
                map.put(NICKNAME, users_person.getNickname());
            }
            if (users_person.getAddress() == null) {
                map.put(ADDRESS, getResources().getString(R.string.default_address));
            } else {
                map.put(ADDRESS, users_person.getAddress());
            }
            if (users_person.getSex() == null) {
                map.put(SEX, getResources().getString(R.string.default_sex));
            } else {
                map.put(SEX, users_person.getSex());
            }
            if (users_person.getDateOfBirth() == null) {
                map.put(DATEOFBIRTH, getResources().getString(R.string.default_birth));
            } else {
                map.put(DATEOFBIRTH, users_person.getDateOfBirth());
            }
            if (users_person.getEmail() == null) {
                map.put(EMAIL, getString(R.string.input_email));
            } else {
                map.put(EMAIL, users_person.getEmail());
            }
            editListMap.add(map);
        }
    }

    /***
     *创建时间：2021/3/15 7:23 PM
     *作者：xyd
     *描述：设置Recyclerview的适配器
     *参数：
     *返回值(Y/N):
     */
    private void initAdapter() {
        editRecyAdapter = new EditRecyAdapter(editListMap, this, editList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        edit_recycler.setLayoutManager(manager);
        edit_recycler.setAdapter(editRecyAdapter);
        editRecyAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mExitImg = findViewById(R.id.exit_img);
        mMyTitle = findViewById(R.id.my_title);
        mMyTitle.setText(R.string.edit_myinfo);
        usersDao = MyApp.getmInstance().getU_db().usersDao();
        mExitImg.setOnClickListener(this);
        edit_recycler = findViewById(R.id.edit_info_recycler);
        users_person = getUsersPerson();

        //将屏幕状态栏设置成透明，并讲字体颜色设置成黑色
        StatusBarUtil.setTranslucentStatus(EditPersonalInfoActivity.this);
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
        }
    }

    /***
     *创建时间：2021/3/15 7:23 PM
     *作者：xyd
     *描述：个人资料点击事件
     *参数：
     *返回值(Y/N):
     */
    @Override
    public void callback(int flag, String movieName) {
        switch (flag) {
            case 0:
                //头像
                showSheet_Head();
                break;
            case 1:
                //签名
            case 2:
                //昵称
            case 6:
                //邮箱
                Intent intent = new Intent(EditPersonalInfoActivity.this, EditContentActivity.class);
                intent.putExtra("str", movieName);
                intent.putExtra("position", flag);
                startActivity(intent);
                overridePendingTransition(R.anim.zoomin_right, R.anim.zoomout_left);
                break;
            case 3:
                //性别
                SelectSex();
                break;
            case 4:
                //出生日期
                TimeSelector();
                break;
            case 5:
                //地址
                CityPickerView cityPickerView = new CityPickerView();
                cityPickerView.init(this);
                selectCity(cityPickerView);

                break;
            case 7:
                //背景墙
                showSheet_Background();
                break;

        }
    }

    /***
     *创建时间：2021/3/16 4:46 PM
     *作者：xyd
     *描述：时间选择器
     *参数：
     *返回值(Y/N):
     */
    private void TimeSelector() {
        TimePickerView pvTime = new TimePickerBuilder(EditPersonalInfoActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                users_person.setDateOfBirth(parseTimeZone(date.toString()).substring(0, 10));
                usersDao.Update(users_person);
                RefreshData();
                initAdapter();
            }
        }).build();
        pvTime.show();
    }

    public static String parseTimeZone(String dateString) {
        String FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
        String FORMAT_STRING2 = "EEE MMM dd HH:mm:ss z yyyy";
        String[] REPLACE_STRING = new String[]{"GMT+0800", "GMT+08:00"};
        String SPLIT_STRING = "(中国标准时间)";
        try {
            dateString = dateString.split(Pattern.quote(SPLIT_STRING))[0].replace(REPLACE_STRING[0], REPLACE_STRING[1]);
            //转换为date
            SimpleDateFormat sf1 = new SimpleDateFormat(FORMAT_STRING2, Locale.ENGLISH);
            Date date = sf1.parse(dateString);
            return new SimpleDateFormat(FORMAT_STRING).format(date);
        } catch (Exception e) {
            throw new RuntimeException("时间转化格式错误" + "[dateString=" + dateString + "]" + "[FORMAT_STRING=" + FORMAT_STRING + "]");
        }
    }


    /***
     *创建时间：2021/3/16 4:28 PM
     *作者：xyd
     *描述：获取当前账号的对象
     *参数：
     *返回值(Y/N):
     */
    private Users_person getUsersPerson() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginSuccess", Context.MODE_PRIVATE);
        Users_person users_person = usersDao.getUserByName(sharedPreferences.getString("Username", ""));
        return users_person;
    }

    /***
     *创建时间：2021/3/16 4:28 PM
     *作者：xyd
     *描述：地址选择器
     *参数：
     *返回值(Y/N):
     */
    private void selectCity(CityPickerView mPicker) {
        //添加默认的配置，不需要自己定义
        CityConfig cityConfig = new CityConfig.Builder().build();
        mPicker.setConfig(cityConfig);

        //监听选择点击事件及返回结果
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                users_person.setAddress(province.getName() + "-" + city.getName() + "-" + district.getName());
                usersDao.Update(users_person);
                RefreshData();
                initAdapter();
            }

            @Override
            public void onCancel() {
            }
        });
        //显示
        mPicker.showCityPicker();
    }

    /***
     *创建时间：2021/3/16 4:27 PM
     *作者：xyd
     *描述：日期选择器
     *参数：
     *返回值(Y/N):
     */
    private void SelectSex() {
        OnOptionsSelectListener onOptionsSelectListener = new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                Toast.makeText(EditPersonalInfoActivity.this, "" + sexList.get(options1), Toast.LENGTH_SHORT).show();
                users_person.setSex(sexList.get(options1).toString());
                usersDao.Update(users_person);
                RefreshData();
                initAdapter();
            }
        };
        OptionsPickerBuilder optionsPickerBuilder = new OptionsPickerBuilder(this, onOptionsSelectListener);
        final OptionsPickerView<Object> build = optionsPickerBuilder.build();
        optionsPickerBuilder.setLayoutRes(R.layout.condition_selector, new CustomListener() {
            @Override
            public void customLayout(View v) {
                //自定义布局中的控件初始化及事件处理
                final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                tvSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        build.dismiss();
                    }
                });
                ivCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        build.dismiss();
                    }
                });

            }
        });
        build.setPicker(sexList);
        build.show();
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
            finish();
            overridePendingTransition(R.anim.zoomin_left, R.anim.zoomout_right);
        }
        return super.onKeyDown(keyCode, event);
    }
    /***
     *创建时间：2021/3/16 9:49 PM
     *作者：xyd
     *描述：背景墙选择列表
     *参数：
     *返回值(Y/N):
     */
    private void showSheet_Background() {
        actionSheet = new ActionSheet.DialogBuilder(this).addSheet("查看图片", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditPersonalInfoActivity.this, SetPictureActivity.class);
                intent.setAction("two");
                startActivity(intent);
                actionSheet.dismiss();
            }
        })
                .addSheet("从相册选择照片", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FLAG = 1;
                        selectPicture();
                        actionSheet.dismiss();
                    }
                })
                .addCancelListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        actionSheet.dismiss();
                    }
                }).create();
    }

    /***
     *创建时间：2021/3/16 9:50 PM
     *作者：xyd
     *描述：头像选择列表
     *参数：
     *返回值(Y/N):
     */
    private void showSheet_Head() {
        actionSheet = new ActionSheet.DialogBuilder(this).addSheet("查看头像", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditPersonalInfoActivity.this, SetPictureActivity.class);
                intent.setAction("one");
                startActivity(intent);
                actionSheet.dismiss();
            }
        }).addSheet("从相册选择照片", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPicture();
                actionSheet.dismiss();
            }
        })
                .addSheet("拍照", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkPermissionAndCamera();
                        actionSheet.dismiss();
                    }
                })
                .addCancelListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        actionSheet.dismiss();
                    }
                }).create();
    }

    /***
     *创建时间：2021/3/16 7:58 PM
     *作者：xyd
     *描述：打开本地相册，选取照片
     *参数：
     *返回值(Y/N):
     */
    private void selectPicture() {
        //intent可以应用于广播和发起意图，其中属性有：ComponentName,action,data等
        Intent intent = new Intent();
        intent.setType("image/*");
        //action表示intent的类型，可以是查看、删除、发布或其他情况；我们选择ACTION_GET_CONTENT，系统可以根据Type类型来调用系统程序选择Type
        //类型的内容给你选择
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //如果第二个参数大于或等于0，那么当用户操作完成后会返回到本程序的onActivityResult方法
        startActivityForResult(intent, 2);
    }

    /**
     * 检查权限并拍照。
     * 调用相机前先检查权限。
     */
    private void checkPermissionAndCamera() {
        int hasCameraPermission = ContextCompat.checkSelfPermission(getApplication(),
                Manifest.permission.CAMERA);
        if (hasCameraPermission == PackageManager.PERMISSION_GRANTED) {
            //有调起相机拍照。
            openCamera();
        } else {
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    PERMISSION_CAMERA_REQUEST_CODE);
        }
    }

    /**
     * 调起相机拍照
     */
    private void openCamera() {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断是否有相机
        if (captureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            Uri photoUri = null;

            if (isAndroidQ) {
                // 适配android 10
                photoUri = createImageUri();
            } else {
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (photoFile != null) {
                    mCameraImagePath = photoFile.getAbsolutePath();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //适配Android 7.0文件权限，通过FileProvider创建一个content类型的Uri
                        photoUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", photoFile);
                    } else {
                        photoUri = Uri.fromFile(photoFile);
                    }
                }
            }

            mCameraUri = photoUri;
            if (photoUri != null) {
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(captureIntent, 1);
            }
        }
    }

    /**
     * 创建图片地址uri,用于保存拍照后的照片 Android 10以后使用这种方法
     */
    private Uri createImageUri() {
        String status = Environment.getExternalStorageState();
        // 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        } else {
            return getContentResolver().insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, new ContentValues());
        }
    }

    /**
     * 创建保存图片的文件
     */
    private File createImageFile() throws IOException {
        String imageName = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!storageDir.exists()) {
            storageDir.mkdir();
        }
        File tempFile = new File(storageDir, imageName);
        if (!Environment.MEDIA_MOUNTED.equals(EnvironmentCompat.getStorageState(tempFile))) {
            return null;
        }
        return tempFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (isAndroidQ) {
                    // Android 10 使用图片uri加载
//                    image.setImageURI(mCameraUri);
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mCameraUri);
                        users_person.setHeadPortrait(saveBitmap(bitmap));
                        usersDao.Update(users_person);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // 使用图片路径加载
//                    image.setImageBitmap(BitmapFactory.decodeFile(mCameraImagePath));
                    users_person.setHeadPortrait(Base64.decode(mCameraImagePath, 0));
                    usersDao.Update(users_person);
                }
            } else {
                Toast.makeText(this, "拍照失败", Toast.LENGTH_LONG).show();
            }
        } else {
            //用户操作完成，结果码返回是-1，即RESULT_OK
            if (resultCode == RESULT_OK) {
                //获取选中文件的定位符
                Uri uri = data.getData();
                //使用content的接口
                ContentResolver cr = this.getContentResolver();
                try {
                    //获取图片
                    Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
//                    image.setImageBitmap(bitmap);
                    if (FLAG == 0) {
                        users_person.setHeadPortrait(saveBitmap(bitmap));
                        usersDao.Update(users_person);
                    } else {
                        users_person.setBackground_pic(saveBitmap(bitmap));
                        usersDao.Update(users_person);
                    }

                } catch (FileNotFoundException e) {
                    Log.e("Exception", e.getMessage(), e);
                }
            } else {
                //操作错误或没有选择图片
//                Log.i("MainActivtiy", "operation error");
            }
        }
    }

    /***
     *创建时间：2021/3/16 8:55 PM
     *作者：xyd
     *描述：将String类型转换成bitmap
     *参数：
     *返回值(Y/N):
     */
    public static Bitmap convertStringToIcon(String st) {
        // OutputStream out;
        Bitmap bitmap = null;
        try {
            // out = new FileOutputStream("/sdcard/aa.jpg");
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap =
                    BitmapFactory.decodeByteArray(bitmapArray, 0,
                            bitmapArray.length);
            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    /***
     * 将Bitmap数据类型转换成 byte[] 数组方法
     * @param bitmap
     * @return
     */
    public static byte[] saveBitmap(Bitmap bitmap) {

        int size = bitmap.getWidth() * bitmap.getHeight() * 4;
        //创建一个字节数组输出流,流的大小为size
        ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
        //设置位图的压缩格式，质量为100%，并放入字节数组输出流中
        int options = 100;
        /* options表示 如果不压缩是100，表示压缩率为0。如果是70，就表示压缩率是70，表示压缩30%; */
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        while (baos.toByteArray().length / 1024 > 500) {
            // 循环判断如果压缩后图片是否大于500kb继续压缩
            baos.reset();
            options -= 10;
            // 这里压缩options%，把压缩后的数据存放到baos中
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        //将字节数组输出流转化为字节数组byte[]

        byte[] imagedata = baos.toByteArray();
        return imagedata;

    }

}
