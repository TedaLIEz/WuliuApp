package com.aicre.wuliuapp.app.activity;



import android.support.v7.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aicre.wuliuapp.app.R;

import com.aicre.wuliuapp.app.util.Globles;

import com.aicre.wuliuapp.app.util.String2Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;


import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends BaseActivity{

    private Button loginBtn,registbtn;
    private EditText maccount;
    private EditText mpassword;
    private RequestQueue mqueue;
    private String result = "";
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar bar = getSupportActionBar();
        bar.setTitle("登录");
        bar.setIcon(R.drawable.icon);
        pd = new ProgressDialog(LoginActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //pd.setTitle("加载中...");
        pd.setMessage("提交信息中...");
        loginBtn = (Button)findViewById(R.id.loginBtn);
        maccount = (EditText)findViewById(R.id.account);
        mpassword = (EditText)findViewById(R.id.password);
        registbtn = (Button)findViewById(R.id.register_Btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(maccount.getText().toString().equals("")&&mpassword.getText().toString().equals(""))) {
                    executeRequest(new String2Request(Globles.LOGIN_URL, "utf-8", responseListener(),
                            errorListener()) {
                        protected Map<String, String> getParams() {
                            Map<String, String> m = new HashMap<String, String>();
                            m.put("username", maccount.getText().toString());
                            m.put("password", mpassword.getText().toString());
                            return m;
                        }

                    });
                }

            }
        });

        registbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegistActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    private Response.Listener<String> responseListener(){
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //result = s;
                pd.dismiss();
               if(!s.trim().equals("fail")){
                   SharedPreferences sharedPreferences = getSharedPreferences("56huowu", Context.MODE_PRIVATE);
                   SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                   editor.putString("id",s.trim());
                   editor.commit();
                   LoginActivity.this.finish();
               }else{
                   Toast.makeText(getApplicationContext(),"用户名或者密码错误",Toast.LENGTH_LONG).show();

               }
            }
        };
    }

    @Override
    public ActionBar getSupportActionBar() {
        return super.getSupportActionBar();
    }
}
