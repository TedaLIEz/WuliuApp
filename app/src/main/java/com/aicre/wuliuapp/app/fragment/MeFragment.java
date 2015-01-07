package com.aicre.wuliuapp.app.fragment;


import android.app.ActionBar;
import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aicre.wuliuapp.app.MainActivity;
import com.aicre.wuliuapp.app.R;
import com.aicre.wuliuapp.app.activity.LoginActivity;
import com.aicre.wuliuapp.app.util.Globles;
import com.aicre.wuliuapp.app.util.String2Request;
import com.android.volley.Response;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wei on 14-7-5.
 */
public class MeFragment extends BaseFragment {

    private SharedPreferences sp;
    private ProgressDialog pd;
    private String id;
    private MeFragment_no meFragment_no;
    static private Activity mActivity;
    static private FragmentManager mFragmentManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();

        ActionBar bar = getActivity().getActionBar();
        bar.setTitle("个人信息");
        pd = new ProgressDialog(this.getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("加载中...");
        sp = this.getActivity().getSharedPreferences("56huowu", Activity.MODE_WORLD_READABLE);
        id = sp.getString("id", "-1").trim();
        if (!id.equals("-1")) {

            pd.show();
            id = id.substring(1);
            executeRequest(new String2Request(Globles.USERINFO_URL, "utf-8", responseListener(),
                    errorListener()) {
                protected Map<String, String> getParams() {
                    //return new ApiParams().with("param1", "02").with("param2", "14");
                    Map<String, String> m = new HashMap<String, String>();
                    m.put("id", id);
                    return m;
                }

            });


        }
        else{
            //跳转到未登录状态
            if(meFragment_no==null) {
                meFragment_no = new MeFragment_no();
            }
            mFragmentManager.beginTransaction().replace(R.id.fragment_container, meFragment_no).commit();

        }



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_me, container, false);


        //v1 = this.getActivity().getLayoutInflater().inflate(R.layout.fragment_me_no, null);
        //v2 = this.getActivity().getLayoutInflater().inflate(R.layout.fragment_me,null);

//        call_name = (TextView) view.findViewById(R.id.call_name);
//        name = (TextView) view.findViewById(R.id.name);
//        phone = (TextView) view.findViewById(R.id.phone);
//        mail = (TextView) view.findViewById(R.id.mail);
//        exitBtn = (Button)view.findViewById(R.id.exit_login);

//        exitBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferences.Editor editor = sp.edit();
//                editor.remove("id");
//                editor.commit();
//                if(sp.getString("id","-1").trim().equals("-1")){
//                    Toast.makeText(getActivity(),"成功退出账号",Toast.LENGTH_LONG).show();
//                    //切换到未登录状态
//                    if(meFragment_no==null) {
//                        meFragment_no = new MeFragment_no();
//                    }
//                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, meFragment_no).commit();
//                }
//            }
//        });


        return view;
    }


    private Response.Listener<String> responseListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //result = s;
                pd.dismiss();
                /*if (!s.isEmpty()) {
                    try {
                        JSONTokener jsonParser = new JSONTokener(s);
                        JSONObject obj = (JSONObject) jsonParser.nextValue();
                        call_name.setText(obj.getString("name").substring(0, 1) + "先生");
                        name.setText(obj.getString("name"));
                        phone.setText(obj.getString("phone"));
                        if(obj.getString("mail").equals("")) {
                            mail.setText(obj.getString("mail"));
                        }
                    } catch (JSONException e) {
                        e.toString();
                    }
                    //切换到登录状态
                }*/
            }
        };
    }

    @Override
    public void onResume() {

        SharedPreferences share = this.getActivity().getSharedPreferences("56huowu", Activity.MODE_WORLD_READABLE);
        id = sp.getString("id","-1").trim();
        if (!id.equals("-1")) {

            pd.show();
            id = id.substring(1);
            executeRequest(new String2Request(Globles.USERINFO_URL, "utf-8", responseListener(),
                    errorListener()) {
                protected Map<String, String> getParams() {
                    //return new ApiParams().with("param1", "02").with("param2", "14");
                    Map<String, String> m = new HashMap<String, String>();
                    m.put("id",id);
                    return m;
                }

            });
        }
        else{
            //跳转到未登录状态
            if(meFragment_no==null) {
                meFragment_no = new MeFragment_no();
            }

            mFragmentManager.beginTransaction().replace(R.id.fragment_container, meFragment_no).commit();
        }
        super.onResume();
        MobclickAgent.onPageStart("MeFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MeFragment");
    }
}
