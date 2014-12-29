package com.aicre.wuliuapp.app.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aicre.wuliuapp.app.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

public class DetailActivity extends BaseActivity {
    private TextView start_detail;
    private TextView finish_detail;
    private TextView name_detail;
    private TextView phone_detail;
    private TextView kind_cargo_detail;
    private TextView type_detail;
    private TextView length_detail;
    private TextView weight_detail;
    //private TextView means_detail;
    private TextView type_car_detail;
    //private TextView price_detail;
    private TextView deadline_detail;
    private Button dialBtn;
    private HashMap<String,String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar bar = getSupportActionBar();
        bar.setTitle("详细信息");
        bar.setDisplayHomeAsUpEnabled(true);
        start_detail = (TextView) findViewById(R.id.start_detail);
        finish_detail = (TextView) findViewById(R.id.finish_detail);
        name_detail = (TextView) findViewById(R.id.name_detail);
        phone_detail = (TextView) findViewById(R.id.phone_detail);
        kind_cargo_detail = (TextView) findViewById(R.id.kind_cargo_detail);
        type_detail = (TextView) findViewById(R.id.type_detail);
        length_detail = (TextView) findViewById(R.id.length_detail);
        weight_detail = (TextView) findViewById(R.id.weight_detail);
        //means_detail = (TextView) findViewById(R.id.means_detail);
        type_car_detail = (TextView) findViewById(R.id.type_car_detail);
        //price_detail = (TextView) findViewById(R.id.price_detail);
        deadline_detail = (TextView) findViewById(R.id.deadline_detail);
        dialBtn = (Button)findViewById(R.id.dialBtn);
        Intent i = getIntent();
        map = (HashMap)i.getSerializableExtra("id");

        start_detail.setText(map.get("fp")+" "+map.get("fc")+" "+map.get("fd"));
        finish_detail.setText(map.get("tp")+" "+map.get("tc")+" "+map.get("fd"));
        name_detail.setText(map.get("name"));
        phone_detail.setText(map.get("phone"));
        if(!map.get("gn").equals("")) {
            kind_cargo_detail.setText(map.get("gn"));
        }
        type_detail.setText(map.get("gn"));
        if(!map.get("cs").equals("")) {
            length_detail.setText(map.get("cs"));
        }
        String dan[] = map.get("gm").split("/");
        if(!map.get("gw").equals("")) {
            weight_detail.setText(map.get("gw")+dan[1]);
        }
        //if(!map.get("trant").equals("")) {
         //   means_detail.setText(map.get("trant"));
        //}
        if(!map.get("ct").equals("")) {
            type_car_detail.setText(map.get("ct"));
        }
        //if(!map.get("price").equals("")) {
            //price_detail.setText(map.get("price"));
        //}
        if(!map.get("deadline").equals("")) {
            deadline_detail.setText(map.get("deadline"));
        }

        dialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!map.get("phone").equals("")) {
                    String str = "tel:" + map.get("phone");
                    Intent i = new Intent("android.intent.action.DIAL", Uri.parse(str));
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),"号码为空",Toast.LENGTH_LONG);
                }
            }
        });
    }



    public void Jsontomap(String Json) {

        JSONObject obj;
        try {
            obj = new JSONObject(Json);
            map.put("fp", obj.getString("fp"));
            map.put("fc", obj.getString("fc"));
            map.put("fd", obj.getString("fd"));
            map.put("tp", obj.getString("tp"));
            map.put("tc", obj.getString("tc"));
            map.put("td", obj.getString("td"));
            map.put("gn", obj.getString("gn"));
            //map.put("gt", obj.getString("gt"));
            map.put("ct", obj.getString("ct"));
            map.put("gm", obj.getString("gm"));
            map.put("trant", obj.getString("trant"));
            map.put("price", obj.getString("price"));
            map.put("cs", obj.getString("cs"));
            map.put("name", obj.getString("name"));
            map.put("phone", obj.getString("phone"));
            map.put("date", obj.getString("date"));
            map.put("deadline", obj.getString("deadline"));
            map.put("einfo", obj.getString("einfo"));
        } catch (JSONException e)
        {
            e.toString();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

}
