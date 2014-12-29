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
import android.widget.ImageSwitcher;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.aicre.wuliuapp.app.R;
import com.aicre.wuliuapp.app.util.Globles;
import com.aicre.wuliuapp.app.util.String2Request;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

public class RegistActivity extends BaseActivity {

    private ViewFlipper mViewFlipper;
    private int page_num;
//    private EditText account = null;
//    private EditText psw = null;
//    private EditText number = null;
//    private EditText name = null;
//    private Button registBtn;
    private ProgressDialog pd;


    private EditText regist_phone;
    private EditText regist_code;
    private EditText regist_psw;
    private EditText regist_repsw;
    private Button verifyBtn;


    private EditText regist_name;
    private EditText regist_card_3;
    private Spinner regist_card_1;
    private Spinner regist_card_2;
    private Spinner regist_type_1;
    private Spinner regist_type_2;
    private ImageSwitcher license_photo;


    private EditText regist_long;
    private EditText regist_weight;
    private EditText regist_volume;
    private Spinner regist_brand;
    private EditText regist_model;
    private EditText regist_remarks;

    private String phone;
    private String code;
    private String psw;
    private String name;
    private String card;
    private String type;
    private int length;
    private int weight;
    private int volume;
    private String brand;
    private String model;
    private String remarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        ActionBar bar = getSupportActionBar();
        bar.setTitle("注册");
        bar.setIcon(R.drawable.icon);

        bar.setDisplayHomeAsUpEnabled(true);

        mViewFlipper = (ViewFlipper) findViewById(R.id.MyView);

        pd = new ProgressDialog(RegistActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("提交信息中...");

//        account = (EditText) findViewById(R.id.edit_account);
//        psw = (EditText) findViewById(R.id.edit_psw);
//        number = (EditText) findViewById(R.id.edit_number);
//        name = (EditText) findViewById(R.id.edit_name);
//        registBtn = (Button) findViewById(R.id.registerBtn);


        regist_phone = (EditText) findViewById(R.id.regist_phone);
        regist_code = (EditText) findViewById(R.id.regist_code);
        regist_psw = (EditText) findViewById(R.id.regist_psw);
        regist_repsw = (EditText) findViewById(R.id.regist_repsw);
        verifyBtn = (Button) findViewById(R.id.verifybtn);

        regist_name = (EditText) findViewById(R.id.regist_name);
        regist_card_3 = (EditText) findViewById(R.id.regist_card_3);
        regist_card_1 = (Spinner) findViewById(R.id.regist_card_1);
        regist_card_2 = (Spinner) findViewById(R.id.regist_card_2);
        regist_type_1 = (Spinner) findViewById(R.id.regist_type_1);
        regist_type_2 = (Spinner) findViewById(R.id.regist_type_2);
        license_photo = (ImageSwitcher) findViewById(R.id.license_photo);

        regist_long = (EditText) findViewById(R.id.regist_long);
        regist_weight = (EditText) findViewById(R.id.regist_weight);
        regist_volume = (EditText) findViewById(R.id.regist_volume);
        regist_brand = (Spinner) findViewById(R.id.regist_brand);
        regist_model = (EditText) findViewById(R.id.regist_model);
        regist_remarks = (EditText) findViewById(R.id.remarks);

        page_num = 0;
//        regist_card_1.setPrompt("湘");
//        regist_card_2.setPrompt("A");
//        regist_type_1.setPrompt("选择类型");
//        regist_type_2.setPrompt("选择型号");
//        regist_brand.setPrompt("选择品牌");
        /*registBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(account.getText().toString().equals("")||psw.getText().toString().equals("")||number.getText().toString().equals("")||name.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"以上内容不能有空",Toast.LENGTH_LONG).show();
                }else{
                    pd.show();
                    executeRequest(new String2Request(Globles.REGISTER_URL,"utf-8" , responseListener(),
                            errorListener()) {
                        protected Map<String, String> getParams(){
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
        });*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add, menu);
        MenuItem next_item = menu.findItem(R.id.next1);
        MenuItem previous_item = menu.findItem(R.id.previous1);
        MenuItem complete_item = menu.findItem(R.id.regist_complete);
        if(page_num==0) {
            complete_item.setVisible(false);
            previous_item.setVisible(false);
        }
        else if(page_num==2) {
            next_item.setVisible(false);
            complete_item.setVisible(true);
        }
        else {
            previous_item.setVisible(true);
            next_item.setVisible(true);
            complete_item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.next1:
                page_num++;
                mViewFlipper.showNext();
                break;
            case R.id.previous1:
                page_num--;
                mViewFlipper.showPrevious();
                break;
            case R.id.regist_complete:
                uploadData();
                break;
            default:
                break;
        }
        supportInvalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }



    void uploadData(){


    }


    private Response.Listener<String> responseListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //result = s;
                pd.dismiss();

                if (s.trim().equals("succeed")) {

                    Toast.makeText(getApplicationContext(), "注册成功，请重新登录", Toast.LENGTH_LONG).show();
                    RegistActivity.this.finish();
                } else {
                    Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_LONG).show();

                }
            }
        };
    }
}
