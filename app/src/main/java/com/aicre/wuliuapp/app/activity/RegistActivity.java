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
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Gallery.LayoutParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ViewSwitcher;

public class RegistActivity extends BaseActivity implements OnItemSelectedListener, ViewSwitcher.ViewFactory {


    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果


    private String jsonstr;
    private Handler mHandle;
    private List<String> pro;
    private ArrayAdapter<String> adapter;

    private ViewFlipper mViewFlipper;
    private int page_num;
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
    private Gallery mGallery;


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
    private String length;
    private String weight;
    private String volume;
    private String brand;
    private String model;
    private String remarks;

    private Bitmap bitmap;


    private ArrayAdapter<String> cardoneAdapter;
    private ArrayAdapter<String> cardtwoAdapter;

    private ArrayAdapter<String> brandAdapter;
    private ArrayList<String> brandArray;


    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;

    private Integer[] mThumbIds;
    private Integer[] mImageIds;
    private ImageAdapter mAdapter;
    private Integer[] mThumbIds1 = {R.drawable.car_1, R.drawable.car_2,
            R.drawable.car_3,
    };

    private Integer[] mThumbIds2 = {R.drawable.car_4, R.drawable.car_5,
            R.drawable.car_6, R.drawable.car_7, R.drawable.car_8, R.drawable.car_9, R.drawable.car_10, R.drawable.car_11, R.drawable.car_12,
    };
    private Integer[] mThumbIds3 = {R.drawable.car_1, R.drawable.car_2,
            R.drawable.car_3,
    };
    private Integer[] mThumbIds4 = {R.drawable.car_1, R.drawable.car_2,
            R.drawable.car_3,
    };

    private Integer[] mImageIds1 = {R.drawable.car_1, R.drawable.car_2,
            R.drawable.car_3,};
    private Integer[] mImageIds2 = {R.drawable.car_4, R.drawable.car_5,
            R.drawable.car_6, R.drawable.car_7, R.drawable.car_8, R.drawable.car_9, R.drawable.car_10, R.drawable.car_11, R.drawable.car_12,};
    private Integer[] mImageIds3 = {R.drawable.car_1, R.drawable.car_2,
            R.drawable.car_3,};
    private Integer[] mImageIds4 = {R.drawable.car_1, R.drawable.car_2,
            R.drawable.car_3,};


    private int is_upload;
    private String session_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        brandArray = new ArrayList<String>();
        ActionBar bar = getSupportActionBar();
        bar.setTitle("注册");
        bar.setIcon(R.drawable.icon);

        is_upload = 0;
        session_code = "";
        bar.setDisplayHomeAsUpEnabled(true);

        mViewFlipper = (ViewFlipper) findViewById(R.id.MyView);

        pd = new ProgressDialog(RegistActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("提交信息中...");

        regist_phone = (EditText) findViewById(R.id.regist_phone);
        regist_code = (EditText) findViewById(R.id.regist_code);
        regist_psw = (EditText) findViewById(R.id.regist_psw);
        regist_repsw = (EditText) findViewById(R.id.regist_repsw);
        verifyBtn = (Button) findViewById(R.id.verifybtn);
        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!regist_phone.getText().toString().equals("")) {
                    executeRequest(new String2Request(Globles.GET_CODE, "utf-8", coderesponseListener(),
                            errorListener()) {
                        protected Map<String, String> getParams() {
                            Map<String, String> m = new HashMap<String, String>();
                            m.put("phone", regist_phone.getText().toString());
                            return m;
                        }

                    });
                } else {
                    Toast.makeText(RegistActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        regist_name = (EditText) findViewById(R.id.regist_name);
        regist_card_3 = (EditText) findViewById(R.id.regist_card_3);
        regist_card_1 = (Spinner) findViewById(R.id.regist_card_1);
        regist_card_1.setAdapter(new ArrayAdapter<String>(RegistActivity.this, android.R.layout.simple_spinner_dropdown_item, new String[]{"皖", "澳", "京", "闽", "甘", "粤", "桂", "贵", "琼", "冀", "豫", "黑", "鄂", "湘", "吉", "苏", "赣", "辽", "蒙", "宁", "青", "鲁", "晋", "陕", "沪", "川", "台", "津", "藏", "港", "新", "云", "浙", "渝"}));
        regist_card_2 = (Spinner) findViewById(R.id.regist_card_2);
        regist_card_2.setAdapter(new ArrayAdapter<String>(RegistActivity.this, android.R.layout.simple_spinner_dropdown_item, new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"}));
        regist_type = (Spinner) findViewById(R.id.regist_type);
        regist_type.setAdapter(new ArrayAdapter<String>(RegistActivity.this, android.R.layout.simple_spinner_dropdown_item, new String[]{"载货车", "挂车", "微面及全封闭式货车", "特种车辆"}));

        mThumbIds = new Integer[]{};
        mImageIds = new Integer[]{};
        regist_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                pd.show();
                switch (position) {
                    case 0:
                        mThumbIds = mThumbIds1;
                        mImageIds = mImageIds1;
                        break;
                    case 1:
                        mThumbIds = mThumbIds2;
                        mImageIds = mImageIds2;
                        break;
                    case 2:
                        mThumbIds = mThumbIds3;
                        mImageIds = mImageIds3;
                        break;
                    case 3:
                        mThumbIds = mThumbIds4;
                        mImageIds = mImageIds4;
                        break;
                    default:
                }
                mAdapter.notifyDataSetChanged();
//                type_photo.setImageResource(mImageIds[position]);
                executeRequest(new String2Request(Globles.GET_MODEL, "utf-8", modelresponseListener(),
                        errorListener()) {
                    protected Map<String, String> getParams() {
                        Map<String, String> m = new HashMap<String, String>();
                        m.put("type", String.valueOf(position + 1));
                        return m;
                    }

                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        type_photo = (ImageSwitcher) findViewById(R.id.type_photo);
        type_photo.setFactory(this);
        type_photo.setInAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in));
        type_photo.setOutAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out));
        mGallery = (Gallery) findViewById(R.id.gallery);
        mAdapter = new ImageAdapter(this);
        mGallery.setAdapter(mAdapter);
        mGallery.setOnItemSelectedListener(this);

        regist_long = (EditText) findViewById(R.id.regist_long);
        regist_weight = (EditText) findViewById(R.id.regist_weight);
        regist_volume = (EditText) findViewById(R.id.regist_volume);
        regist_brand = (Spinner) findViewById(R.id.regist_brand);
        regist_model = (EditText) findViewById(R.id.regist_model);
        regist_remarks = (EditText) findViewById(R.id.remarks);
        licenseBtn = (ImageButton) findViewById(R.id.license_photo);


        licenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(RegistActivity.this);
                LayoutInflater inflater = (LayoutInflater) RegistActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.dialog_choose, null);
                dialog.setView(layout);
                dialog.show();
            }
        });

        page_num = 0;
        pro = new ArrayList<String>();

        brandAdapter = new ArrayAdapter<String>(RegistActivity.this, android.R.layout.simple_spinner_dropdown_item, brandArray);
        regist_brand.setAdapter(brandAdapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add, menu);
        MenuItem next_item = menu.findItem(R.id.next1);
        MenuItem previous_item = menu.findItem(R.id.previous1);
        MenuItem complete_item = menu.findItem(R.id.regist_complete);
        if (page_num == 0) {
            complete_item.setVisible(false);
            previous_item.setVisible(false);
        } else if (page_num == 2) {
            next_item.setVisible(false);
            complete_item.setVisible(true);
        } else {
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
                if (page_num == 0) {
                    if (regist_phone.getText().toString().equals("")) {
                        Toast.makeText(RegistActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                    } else if (regist_code.getText().toString().equals("")) {
                        Toast.makeText(RegistActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                    } else if (regist_psw.getText().toString().equals("")) {
                        Toast.makeText(RegistActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    } else if (regist_repsw.getText().toString().equals("")) {
                        Toast.makeText(RegistActivity.this, "请填入重复密码", Toast.LENGTH_SHORT).show();
                    } else if (regist_psw.getText().toString()==regist_repsw.getText().toString()) {
                        Toast.makeText(RegistActivity.this, "两次填入的密码不同", Toast.LENGTH_SHORT).show();
                    } else {
                        page_num++;
                        mViewFlipper.showNext();
                    }
                } else if (page_num == 1) {
                    if (regist_name.getText().toString().equals("")) {
                        Toast.makeText(RegistActivity.this, "姓名不能为空", Toast.LENGTH_SHORT).show();
                    } else if (regist_card_3.getText().toString().equals("")) {
                        Toast.makeText(RegistActivity.this, "车牌号不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        page_num++;
                        mViewFlipper.showNext();
                    }
                }
                break;
            case R.id.previous1:
                page_num--;
                mViewFlipper.showPrevious();
                break;
            case R.id.regist_complete:
                if(is_upload!=0&&session_code!="") {
                    uploadData();
                }
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
                Toast.makeText(RegistActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
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
        uploadImg();
        super.onActivityResult(requestCode, resultCode, data);

    }

    void uploadData() {
        phone = regist_phone.getText().toString();
        code = regist_code.getText().toString();
        psw = regist_psw.getText().toString();
        name = regist_name.getText().toString();
        card = regist_card_1.getSelectedItem().toString() + regist_card_2.getSelectedItem().toString() + regist_card_3.getText().toString();

        type = regist_type.getSelectedItem().toString();
        length = regist_long.getText().toString();
        weight = regist_weight.getText().toString();
        volume = regist_volume.getText().toString();
        brand = regist_brand.getSelectedItem().toString();
        remarks = regist_remarks.getText().toString();


        executeRequest(new String2Request(Globles.REGISTER_URL, "utf-8", responseListener(),
                errorListener()) {
            protected Map<String, String> getParams() {
                Map<String, String> m = new HashMap<String, String>();
                m.put("session",session_code);
                m.put("username", phone);
                m.put("password", psw);
                m.put("type", type);
                m.put("name", name);
                m.put("cfword", code);
                m.put("number", card);
                m.put("ct", phone);
                m.put("cs", length);
                m.put("cw", weight);
                m.put("cv", volume);
                m.put("grand", brand);
                m.put("info", remarks);
                return m;
            }

        });

    }

    void uploadImg() {

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
            }
        };

    }

    private Response.Listener<String> coderesponseListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s.equals("0")) {
                    Toast.makeText(RegistActivity.this, "验证码失败，60秒后才能再次发送", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    session_code = s;
                    Toast.makeText(RegistActivity.this, "成功产生验证码，请检查短信", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        };

    }

    private Response.Listener<String> modelresponseListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                pd.hide();
                brandArray.clear();
                JSONObject obj;
                String str;
                try {
                    obj = new JSONObject(s);
                    int num;
                    if (obj.getInt("brandnum") != 0) {
                        num = obj.getInt("brandnum");
                        JSONArray objArray = obj.getJSONArray("brand");
                        for (int i = 0; i < num; i++) {
                            obj = objArray.getJSONObject(i);
                            brandArray.add(obj.getString("brand"));
                            brandAdapter.notifyDataSetChanged();
                        }
                    } else {
                        brandArray.add(" ");
                        brandAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        type_photo.setImageResource(mImageIds[position]);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub

    }

    @Override
    public View makeView() {
        ImageView i = new ImageView(this);
        i.setBackgroundColor(0xFF000000);
        i.setScaleType(ImageView.ScaleType.FIT_CENTER);
        i.setLayoutParams(new ImageSwitcher.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        return i;
    }

    public class ImageAdapter extends BaseAdapter {
        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView i = new ImageView(mContext);

            i.setImageResource(mThumbIds[position]);
            i.setAdjustViewBounds(true);
            i.setLayoutParams(new Gallery.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            return i;
        }

        private Context mContext;

    }
}
