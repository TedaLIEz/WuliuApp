package com.aicre.wuliuapp.app.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.aicre.wuliuapp.app.R;
import com.aicre.wuliuapp.app.util.Globles;
import com.aicre.wuliuapp.app.util.String2Request;
import com.android.volley.Response;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class RegistActivity extends BaseActivity {


    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果


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
    private Spinner regist_type;
    private ImageSwitcher type_photo;


    private EditText regist_long;
    private EditText regist_weight;
    private EditText regist_volume;
    private Spinner regist_brand;
    private EditText regist_model;
    private EditText regist_remarks;

    private ImageButton licenseBtn;

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

    private Bitmap bitmap;


    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;
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
        regist_type = (Spinner) findViewById(R.id.regist_type);
        type_photo = (ImageSwitcher) findViewById(R.id.type_photo);

        regist_long = (EditText) findViewById(R.id.regist_long);
        regist_weight = (EditText) findViewById(R.id.regist_weight);
        regist_volume = (EditText) findViewById(R.id.regist_volume);
        regist_brand = (Spinner) findViewById(R.id.regist_brand);
        regist_model = (EditText) findViewById(R.id.regist_model);
        regist_remarks = (EditText) findViewById(R.id.remarks);
        licenseBtn = (ImageButton)findViewById(R.id.license_photo);


        licenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog =new AlertDialog.Builder(RegistActivity.this);
                LayoutInflater inflater = (LayoutInflater)RegistActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.dialog_choose,null);
                dialog.setView(layout);
                dialog.show();
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                crop(uri);
            }

        } else if (requestCode == PHOTO_REQUEST_CAMERA) {
            if (hasSdcard()) {
                tempFile = new File(Environment.getExternalStorageDirectory(),
                        PHOTO_FILE_NAME);
                crop(Uri.fromFile(tempFile));
            } else {
                Toast.makeText(RegistActivity.this, "未找到存储卡，无法存储照片！",Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == PHOTO_REQUEST_CUT) {
            try {
                bitmap = data.getParcelableExtra("data");
                this.licenseBtn.setImageBitmap(bitmap);
                boolean delete = tempFile.delete();
                System.out.println("delete = " + delete);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    void uploadData(){
        phone = regist_phone.getText().toString();
        code = regist_code.getText().toString();
        psw = regist_psw.getText().toString();
        name = regist_name.getText().toString();
        card = regist_card_1.getSelectedItem().toString()+regist_card_2.getSelectedItem().toString()+regist_card_3.getText().toString();
        type = regist_type.getSelectedItem().toString();
        length = Integer.getInteger(regist_long.getText().toString());
        weight = Integer.getInteger(regist_weight.getText().toString());
        volume = Integer.getInteger(regist_weight.getText().toString());
        brand = regist_brand.getSelectedItem().toString();
        remarks = regist_remarks.getText().toString();




    }

    void uploadImg(){

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            byte[] buffer = out.toByteArray();

            byte[] encode = Base64.encode(buffer, Base64.DEFAULT);
            String photo = new String(encode);

            RequestParams params = new RequestParams();
            params.put("pic", photo);
            String url = Globles.UPLOAD_PIC;

            AsyncHttpClient client = new AsyncHttpClient();
            client.post(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers,
                                      byte[] responseBody) {
                    try {
                        if (statusCode == 200) {

                            Toast.makeText(RegistActivity.this, "头像上传成功!", Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            Toast.makeText(RegistActivity.this,
                                    "网络访问异常，错误码：" + statusCode, Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers,
                                      byte[] responseBody, Throwable error) {
                    Toast.makeText(RegistActivity.this,
                            "网络访问异常，错误码  > " + statusCode, Toast.LENGTH_SHORT).show();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    /*
 * 从相册获取
 */
    public void gallery(View view) {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    /*
     * 从相机获取
     */
    public void camera(View view) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory(), PHOTO_FILE_NAME)));
        }
        startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
    }

    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }
    private boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
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
