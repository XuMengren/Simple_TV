package com.example.simpletv.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.simpletv.R;
import com.example.simpletv.SearchHistoryDataBase.MyApp;
import com.example.simpletv.Tools.StatusBarUtil;
import com.example.simpletv.Tools.mToast;
import com.example.simpletv.UsersDataBase.UsersDao;
import com.example.simpletv.UsersDataBase.Users_person;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private FloatingActionButton fab;
    private CardView cvAdd;
    private Spinner et_security_question;
    private List<String> question = new ArrayList<>();
    private EditText mEtUsername;
    private EditText mEtPassword;
    private EditText mEtRepeatpassword;
    private EditText mEtAnswer;
    private Button mBtGo;
    private CoordinatorLayout coordinator;
    private String question_itemClick;
    private UsersDao usersDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ShowEnterAnimation();
        initView();
        myadapter();
    }

    private void initView() {
        fab = findViewById(R.id.fab);
        cvAdd = findViewById(R.id.cv_add);
        et_security_question = findViewById(R.id.et_security_question);
        mEtUsername = findViewById(R.id.et_username);
        mEtPassword = findViewById(R.id.et_password);
        mEtRepeatpassword = findViewById(R.id.et_repeatpassword);
        mEtAnswer = findViewById(R.id.et_answer);
        mBtGo = findViewById(R.id.bt_go);
        coordinator = findViewById(R.id.coordinator);
        usersDao = MyApp.getmInstance().getU_db().usersDao();
        et_security_question.setOnItemSelectedListener(this);
        mBtGo.setOnClickListener(this);
        fab.setOnClickListener(this);

        StatusBarUtil.setTranslucentStatus(RegistActivity.this);
        StatusBarUtil.setStatusBarColor(this, Color.parseColor("#FAFAFA"));
        StatusBarUtil.setImmersiveStatusBar(this, false);
        StatusBarUtil.setStatusBarFontIconDark(this, StatusBarUtil.TYPE_M);
    }

    public static boolean checkformat(String str, int flag) {
        Pattern p = null;
        String uname = "([a-zA-Z0-9]{6,12})";
        String pwd = "^(?![0-9])(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        switch (flag) {
            case 0:
                p = Pattern.compile(uname);
                break;
            case 1:
                p = Pattern.compile(pwd);
                break;
        }
        Matcher m = p.matcher(str);
        return m.matches();
    }

    private void ShowEnterAnimation() {
        Transition transition = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(transition);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            transition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {
                    cvAdd.setVisibility(View.GONE);
                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    transition.removeListener(this);
                    animateRevealShow();
                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }


            });
        }
    }

    private void animateRevealShow() {
        Animator mAnimator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, fab.getWidth() / 2, cvAdd.getHeight());
        }
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                animateRevealClose();
                break;
            case R.id.bt_go:
                //注册
                if (!checkformat(mEtUsername.getText().toString(), 0)) {//检查账号格式是否正确
                    SnackbarPop("用户名由大小写和数字组成且长度大于6小于12");
                } else if (!checkformat(mEtPassword.getText().toString(), 1)) {//检查密码格式是否正确
                    SnackbarPop("密码由6-16位数字字母混合,不能全为数字,不能全为字母,首位不能为数字");
                } else if (!mEtPassword.getText().toString().equals(mEtRepeatpassword.getText().toString())) {//检查确认密码是否正确
                    SnackbarPop("两次密码不一致");
                } else if (question_itemClick.equals(question.get(4))) {//检查是否选择密保问题
                    SnackbarPop("请选择密保问题");
                } else if (mEtAnswer.getText().toString().equals("")) {//检查密保答案是否正确
                    SnackbarPop("请输入密保答案");
                } else if (usersDao.CheckName(mEtUsername.getText().toString())) {//检查用户名是否被注册
                    SnackbarPop("该用户名已被注册");
                } else {
                    //注册成功，将数据保存到数据库
                    String  i1="";
                    Users_person users_person = new Users_person();
                    Random random = new Random();
                    for (int i = 0; i < 5; i++) {
                          i1 = i1+random.nextInt(9) + 1;
                    }
                    users_person.setNickname("kankan" + i1);
                    users_person.setAccountNumber(mEtUsername.getText().toString());
                    users_person.setPassword(mEtPassword.getText().toString());
                    users_person.setSecurityQuestion(question_itemClick);
                    users_person.setAnswer(mEtAnswer.getText().toString());
                    usersDao.InsertAll(users_person);
                    mToast.single("注册成功", this);
                    animateRevealClose();
                }
                break;
        }
    }

    /***
        *创建时间：2021/3/15 8:21 PM
        *作者：xyd
        *描述：自定义SnackBar
        *参数：
        *返回值(Y/N):
    */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void SnackbarPop(String title) {
        Snackbar snackbar = Snackbar.make(coordinator, title, Snackbar.LENGTH_LONG)
                .setAction(R.string.confirm, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击确认啥都不做
                    }
                });
        snackbar.getView().setBackground(getResources().getDrawable(R.color.hint_bg));
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.show();

    }

    private void animateRevealClose() {
        Animator mAnimator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, cvAdd.getHeight(), fab.getWidth() / 2);
        }
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.mipmap.plus);
                RegistActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @Override
    public void onBackPressed() {
        animateRevealClose();
    }

    /***
     *创建时间：2021/3/8 3:09 PM
     *作者：xyd
     *描述：下拉框设置适配器
     *参数：
     *返回值(Y/N):
     */
    public void myadapter() {
        question.add("我的出生年月日是？");
        question.add("毕业小学的名称是？");
        question.add("我父母的姓名是？");
        question.add("我的恋人叫什么？");
        question.add("请选择密保问题");
        Myadapter myadapter = new Myadapter(this, R.layout.spinner_item_text, question);
        et_security_question.setAdapter(myadapter);
        //默认选中最后一项
        et_security_question.setSelection(question.size() - 1, true);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        question_itemClick = question.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * 定义一个Myadapter类继承ArrayAdapter
     * 重写以下两个方法
     */
    class Myadapter<T> extends ArrayAdapter {
        public Myadapter(@NonNull Context context, int resource, @NonNull List<T> objects) {
            super(context, resource, objects);
        }

        @Override
        public int getCount() {
            //返回数据的统计数量，大于0项则减去1项，从而不显示最后一项
            int i = super.getCount();
            return i > 0 ? i - 1 : i;
        }
    }


}
