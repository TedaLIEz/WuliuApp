package com.aicre.wuliuapp.app.activity;

import android.app.ActionBar;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aicre.wuliuapp.app.R;
import com.aicre.wuliuapp.app.dao.Contacts;
import com.aicre.wuliuapp.app.dao.ContactsDB;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

public class HisActivity extends BaseActivity {
    private List<Contacts> list;
    private MyHisAdapter myHisAdapter;
    private ListView mlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_his);
        ActionBar bar = getActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle("联系记录");

        ContactsDB mDB = new ContactsDB(this);

        list = mDB.getAll();
        if(list.isEmpty()){

        }

        myHisAdapter = new MyHisAdapter(this);
        mlist = (ListView)findViewById(R.id.mlist);
        mlist.setAdapter(myHisAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_his, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("HisFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("HisFragment");
    }


    class MyHisAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public MyHisAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return list.size();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_his,null);
                holder = new ViewHolder();
                holder.mHisName = (TextView)convertView.findViewById(R.id.his_name);
                holder.mHisPhone = (TextView)convertView.findViewById(R.id.his_phone);
                holder.mHisTime = (TextView)convertView.findViewById(R.id.his_time);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            Contacts contacts = list.get(getCount()-position-1) ;
            holder.mHisName.setText(contacts.getName());
            holder.mHisPhone.setText(contacts.getPhone());
            holder.mHisTime.setText(contacts.getTime());
            return convertView;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Contacts getItem(int position) {
//            Log.v("position",String.valueOf(getCount()-position-1));
            return list.get(position);
        }
    }

    public final class ViewHolder{
        public TextView mHisName;
        public TextView mHisPhone;
        public TextView mHisTime;
    }
}
