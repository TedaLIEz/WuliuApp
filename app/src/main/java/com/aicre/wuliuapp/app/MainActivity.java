package com.aicre.wuliuapp.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.AttributeSet;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aicre.wuliuapp.app.activity.HisActivity;
import com.aicre.wuliuapp.app.fragment.HisFragment;
import com.aicre.wuliuapp.app.fragment.HomeFragment;
import com.aicre.wuliuapp.app.fragment.MeFragment_no;
import com.aicre.wuliuapp.app.fragment.RecommendFragment;
import com.aicre.wuliuapp.app.fragment.SettingFragment;
import com.aicre.wuliuapp.app.util.Globles;
import com.aicre.wuliuapp.app.util.String2Request;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends FragmentActivity {

    static private HomeFragment mFragmentFirst;
    static private RecommendFragment mFragmentSecond;
    static private MeFragment_no mFragmentThird;
    //static private HisFragment mFragmentForth;
    static private SettingFragment mFragmentForth;

    static private android.support.v4.app.FragmentManager mFragmentManager;

    static private MyRelativeButton mRelativeLayoutFirst;
    static private MyRelativeButton mRelativeLayoutSecond;
    static private MyRelativeButton mRelativeLayoutThird;
    static private MyRelativeButton mRelativeLayoutForth;

    static private ImageView mImageViewFirst;
    static private ImageView mImageViewSecond;
    static private ImageView mImageViewThird;
    static private ImageView mImageViewForth;

    static private TextView mTextViewFirst;
    static private TextView mTextViewSecond;
    static private TextView mTextViewThird;
    static private TextView mTextViewForth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_main) ;
        //ActionBar bar = this.getActionBar();

        MobclickAgent.openActivityDurationTrack(false);



        mFragmentManager = getSupportFragmentManager() ;
        findRelativeLayoutView() ;
        mFragmentFirst = new HomeFragment() ;
        mFragmentSecond = new RecommendFragment() ;
        mFragmentThird = new MeFragment_no() ;
        //mFragmentForth = new HisFragment() ;
        mFragmentForth = new SettingFragment();
        this.getActionBar().setDisplayHomeAsUpEnabled(false);
        this.getActionBar().setDisplayUseLogoEnabled(false);
        this.getActionBar().setDisplayShowHomeEnabled(true);
        this.getActionBar().setDisplayShowTitleEnabled(true);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,mFragmentSecond).commit() ;
    }

    void findRelativeLayoutView(){
        mRelativeLayoutFirst = (MyRelativeButton) findViewById(R.id.lei_button_relative_first) ;
        mRelativeLayoutSecond = (MyRelativeButton) findViewById(R.id.lei_button_relative_second) ;
        mRelativeLayoutThird = (MyRelativeButton) findViewById(R.id.lei_button_relative_third) ;
        mRelativeLayoutForth = (MyRelativeButton) findViewById(R.id.lei_button_relative_forth) ;

        mImageViewFirst = (ImageView) findViewById(R.id.lei_button_first_left) ;
        mImageViewSecond = (ImageView) findViewById(R.id.lei_button_second) ;
        mImageViewThird = (ImageView) findViewById(R.id.lei_button_third) ;
        mImageViewForth = (ImageView) findViewById(R.id.lei_button_forth) ;

        mTextViewFirst = (TextView) findViewById(R.id.lei_button_textview_home) ;
        mTextViewSecond = (TextView) findViewById(R.id.lei_button_textview_recommend) ;
        mTextViewThird = (TextView) findViewById(R.id.lei_button_textview_me) ;
        mTextViewForth = (TextView) findViewById(R.id.lei_button_textview_setting) ;

    }


    static public class MyRelativeButton extends RelativeLayout {

        public MyRelativeButton(Context context){
            super(context);
        }

        public MyRelativeButton(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }
        public MyRelativeButton(Context context, AttributeSet attrs){
            super(context, attrs);
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            super.onInterceptTouchEvent(ev);
            return true;
        }


        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (mImageViewFirst != null && mImageViewSecond != null &&
                    mImageViewThird != null && mImageViewForth != null &&
                    mTextViewFirst != null && mTextViewSecond != null &&
                    mTextViewThird != null && mTextViewForth != null){
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        switch (this.getId()) {
                            case R.id.lei_button_relative_first :
                                mTextViewFirst.setTextColor(getResources().getColor(R.color.button_green));
                                mImageViewFirst.setImageDrawable(getResources().getDrawable(R.drawable.home_press));
                                break ;
                            case R.id.lei_button_relative_second :
                                mTextViewSecond.setTextColor(getResources().getColor(R.color.button_green));
                                mImageViewSecond.setImageDrawable(getResources().getDrawable(R.drawable.recommend_press));
                                break ;
                            case R.id.lei_button_relative_third :
                                mTextViewThird.setTextColor(getResources().getColor(R.color.button_green));
                                mImageViewThird.setImageDrawable(getResources().getDrawable(R.drawable.me_press));
                                break ;
                            case R.id.lei_button_relative_forth :
                                mTextViewForth.setTextColor(getResources().getColor(R.color.button_green));
                                mImageViewForth.setImageDrawable(getResources().getDrawable(R.drawable.setting_press));
                                break ;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        switch (this.getId()) {
                            case R.id.lei_button_relative_first :
                                mTextViewFirst.setTextColor(getResources().getColor(R.color.button_green));
                                mTextViewSecond.setTextColor(getResources().getColor(R.color.button_gray));
                                mTextViewThird.setTextColor(getResources().getColor(R.color.button_gray));
                                mTextViewForth.setTextColor(getResources().getColor(R.color.button_gray));
                                mImageViewFirst.setImageDrawable(getResources().getDrawable(R.drawable.home_press));
                                mImageViewSecond.setImageDrawable(getResources().getDrawable(R.drawable.recommend));
                                mImageViewThird.setImageDrawable(getResources().getDrawable(R.drawable.me));
                                mImageViewForth.setImageDrawable(getResources().getDrawable(R.drawable.setting));
                                if(mFragmentFirst ==null) {
                                    mFragmentFirst = new HomeFragment();
                                }
                                mFragmentManager.beginTransaction().replace(R.id.fragment_container ,mFragmentFirst ).commit();
                                break ;
                            case R.id.lei_button_relative_second :
                                mTextViewFirst.setTextColor(getResources().getColor(R.color.button_gray));
                                mTextViewSecond.setTextColor(getResources().getColor(R.color.button_green));
                                mTextViewThird.setTextColor(getResources().getColor(R.color.button_gray));
                                mTextViewForth.setTextColor(getResources().getColor(R.color.button_gray));
                                mImageViewFirst.setImageDrawable(getResources().getDrawable(R.drawable.home));
                                mImageViewSecond.setImageDrawable(getResources().getDrawable(R.drawable.recommend_press));
                                mImageViewThird.setImageDrawable(getResources().getDrawable(R.drawable.me));
                                mImageViewForth.setImageDrawable(getResources().getDrawable(R.drawable.setting));
                                if(mFragmentSecond ==null) {
                                    mFragmentSecond = new RecommendFragment() ;
                                }
                                mFragmentManager.beginTransaction().replace(R.id.fragment_container ,mFragmentSecond ).commit();
                                break ;
                            case R.id.lei_button_relative_third :
                                mTextViewFirst.setTextColor(getResources().getColor(R.color.button_gray));
                                mTextViewSecond.setTextColor(getResources().getColor(R.color.button_gray));
                                mTextViewThird.setTextColor(getResources().getColor(R.color.button_green));
                                mTextViewForth.setTextColor(getResources().getColor(R.color.button_gray));
                                mImageViewFirst.setImageDrawable( getResources().getDrawable(R.drawable.home));
                                mImageViewSecond.setImageDrawable(getResources().getDrawable(R.drawable.recommend));
                                mImageViewThird.setImageDrawable(getResources().getDrawable(R.drawable.me_press));
                                mImageViewForth.setImageDrawable(getResources().getDrawable(R.drawable.setting));
                                if(mFragmentThird ==null) {
                                    mFragmentThird = new MeFragment_no();
                                }
                                mFragmentManager.beginTransaction().replace(R.id.fragment_container ,mFragmentThird ).commit();
                                break ;
                            case R.id.lei_button_relative_forth :
                                mTextViewFirst.setTextColor(getResources().getColor(R.color.button_gray))  ;
                                mTextViewSecond.setTextColor(getResources().getColor(R.color.button_gray)) ;
                                mTextViewThird.setTextColor(getResources().getColor(R.color.button_gray))  ;
                                mTextViewForth.setTextColor(getResources().getColor(R.color.button_green)) ;
                                mImageViewFirst.setImageDrawable(getResources().getDrawable(R.drawable.home))    ;
                                mImageViewSecond.setImageDrawable(getResources().getDrawable(R.drawable.recommend)) ;
                                mImageViewThird.setImageDrawable(getResources().getDrawable(R.drawable.me))         ;
                                mImageViewForth.setImageDrawable(getResources().getDrawable(R.drawable.setting_press)) ;
                                if(mFragmentForth ==null) {
                                    //mFragmentForth = new HisFragment() ;
                                    mFragmentForth = new SettingFragment();
                                }
                                mFragmentManager.beginTransaction().replace(R.id.fragment_container ,mFragmentForth ).commit() ;
                                break ;
                        }
                        break;
                }
            }
            return true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    public void checkVersion(View view){
        UmengUpdateAgent.forceUpdate(this);
    }

    public void feedBack(View view){
        FeedbackAgent agent = new FeedbackAgent(this);
        agent.startFeedbackActivity();
    }

    public void myRecord(View view){
        Intent intent=new Intent(MainActivity.this, HisActivity.class);
        startActivity(intent);
    }

    /*
    账户安全
     */
    public void accountSafety(View view){
        AlertDialog.Builder dialog =new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.dialog_passwd,null);
        dialog.setView(layout);
        final EditText new_passwd = (EditText)layout.findViewById(R.id.new_passwd);
        final EditText old_passwd = (EditText)layout.findViewById(R.id.old_passwd);
        dialog.setPositiveButton("确定",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    /*
    关于我们
     */
    public void aboutUs(View view){

    }

    /*
    帮助
     */
    public void Help(View view){

    }

}