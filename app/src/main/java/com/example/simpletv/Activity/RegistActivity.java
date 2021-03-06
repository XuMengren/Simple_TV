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
                //??????
                if (!checkformat(mEtUsername.getText().toString(), 0)) {//??????????????????????????????
                    SnackbarPop("???????????????????????????????????????????????????6??????12");
                } else if (!checkformat(mEtPassword.getText().toString(), 1)) {//??????????????????????????????
                    SnackbarPop("?????????6-16?????????????????????,??????????????????,??????????????????,?????????????????????");
                } else if (!mEtPassword.getText().toString().equals(mEtRepeatpassword.getText().toString())) {//??????????????????????????????
                    SnackbarPop("?????????????????????");
                } else if (question_itemClick.equals(question.get(4))) {//??????????????????????????????
                    SnackbarPop("?????????????????????");
                } else if (mEtAnswer.getText().toString().equals("")) {//??????????????????????????????
                    SnackbarPop("?????????????????????");
                } else if (usersDao.CheckName(mEtUsername.getText().toString())) {//??????????????????????????????
                    SnackbarPop("????????????????????????");
                } else {
                    //??????????????????????????????????????????
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
                    mToast.single("????????????", this);
                    animateRevealClose();
                }
                break;
        }
    }

    /***
        *???????????????2021/3/15 8:21 PM
        *?????????xyd
        *??????????????????SnackBar
        *?????????
        *?????????(Y/N):
    */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void SnackbarPop(String title) {
        Snackbar snackbar = Snackbar.make(coordinator, title, Snackbar.LENGTH_LONG)
                .setAction(R.string.confirm, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //????????????????????????
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
     *???????????????2021/3/8 3:09 PM
     *?????????xyd
     *?????????????????????????????????
     *?????????
     *?????????(Y/N):
     */
    public void myadapter() {
        question.add("???????????????????????????");
        question.add("???????????????????????????");
        question.add("????????????????????????");
        question.add("????????????????????????");
        question.add("?????????????????????");
        Myadapter myadapter = new Myadapter(this, R.layout.spinner_item_text, question);
        et_security_question.setAdapter(myadapter);
        //????????????????????????
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
     * ????????????Myadapter?????????ArrayAdapter
     * ????????????????????????
     */
    class Myadapter<T> extends ArrayAdapter {
        public Myadapter(@NonNull Context context, int resource, @NonNull List<T> objects) {
            super(context, resource, objects);
        }

        @Override
        public int getCount() {
            //????????????????????????????????????0????????????1?????????????????????????????????
            int i = super.getCount();
            return i > 0 ? i - 1 : i;
        }
    }


}
