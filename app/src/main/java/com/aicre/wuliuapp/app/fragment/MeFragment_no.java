package com.aicre.wuliuapp.app.fragment;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aicre.wuliuapp.app.R;
import com.aicre.wuliuapp.app.activity.LoginActivity;
import com.aicre.wuliuapp.app.util.Globles;
import com.aicre.wuliuapp.app.util.String2Request;
import com.android.volley.Response;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wei on 14-9-5.
 */
public class MeFragment_no extends BaseFragment{

    private Button accountBtn;
    private String id;
    private Activity mActivity;
    private SharedPreferences sp;
    private MeFragment meFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar = getActivity().getActionBar();
        bar.setTitle("个人信息");
        mActivity = this.getActivity();
        sp = this.getActivity().getSharedPreferences("56huowu", Activity.MODE_WORLD_READABLE);
        id = sp.getString("id", "-1").trim();
        if (!id.equals("-1")) {
            //跳转到登录状态
            //跳转到未登录状态
            if(meFragment==null) {
                meFragment = new MeFragment();
            }

            this.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, meFragment).commit();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_me_no, null, false);
        accountBtn = (Button)view.findViewById(R.id.account_Btn);
        accountBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }


    @Override
    public void onResume() {
        SharedPreferences share = this.getActivity().getSharedPreferences("56huowu", Activity.MODE_WORLD_READABLE);
        id = sp.getString("id","-1").trim();
        if (!id.equals("-1")) {
            if(meFragment==null) {
                meFragment = new MeFragment();
            }

            this.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, meFragment).commit();

        }
        else{

            //切换到未登录状态

        }
        super.onResume();
        MobclickAgent.onPageStart("MeFragment_no");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MeFragment_no");
    }
}
