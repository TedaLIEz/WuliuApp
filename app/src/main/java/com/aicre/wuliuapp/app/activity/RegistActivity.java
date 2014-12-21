package com.aicre.wuliuapp.app.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
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
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

public class RegistActivity extends BaseActivity {

    private EditText account = null;
    private EditText psw = null;
    private EditText number = null;
    private EditText name = null;
    private Button registBtn;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("注册");
        bar.setIcon(R.drawable.icon);
        pd = new ProgressDialog(RegistActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //pd.setTitle("加载中...");
        pd.setMessage("提交信息中...");

        account = (EditText)findViewById(R.id.edit_account);
        psw = (EditText)findViewById(R.id.edit_psw);
        number = (EditText)findViewById(R.id.edit_number);
        name = (EditText)findViewById(R.id.edit_name);
        registBtn = (Button)findViewById(R.id.registerBtn);

        registBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(account.getText().toString().equals("")||psw.getText().toString().equals("")||number.getText().toString().equals("")||name.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"以上内容不能有空",Toast.LENGTH_LONG).show();
                }else{
                    pd.show();
                    executeRequest(new String2Request(Globles.REGISTER_URL,"utf-8" , responseListener(),
                            errorListener()) {
                        protected Map<String, String> getParams(){
                            //return new ApiParams().with("param1", "02").with("param2", "14");
                            Map<String,String> m = new HashMap<String, String>();
                            m.put("username",account.getText().toString());
                            m.put("password",psw.getText().toString());
                            m.put("phone",number.getText().toString());
                            m.put("name",name.getText().toString());
                            m.put("type","1");
                            return m;
                        }

                    });
                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private Response.Listener<String> responseListener(){
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //result = s;
                pd.dismiss();

                if(s.trim().equals("succeed")){

                    Toast.makeText(getApplicationContext(),"注册成功，请重新登录",Toast.LENGTH_LONG).show();
                    RegistActivity.this.finish();
                }else{
                    Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_LONG).show();

                }
            }
        };
    }
}
