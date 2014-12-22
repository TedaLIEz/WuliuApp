package com.aicre.wuliuapp.app.fragment;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.aicre.wuliuapp.app.R;
import com.aicre.wuliuapp.app.dao.Contacts;
import com.aicre.wuliuapp.app.dao.ContactsDB;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wei on 14-7-5.
 */
public class HisFragment extends Fragment {

    private List<Contacts> list;
    private MyHisAdapter myHisAdapter;
    private ListView mlist;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar bar = getActivity().getActionBar();

        bar.setTitle("联系记录");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ContactsDB mDB = new ContactsDB(getActivity());

        list = mDB.getAll();
        Log.v("name",list.get(0).getName());
        View view = inflater.inflate(R.layout.fragment_his, container, false);
        myHisAdapter = new MyHisAdapter(getActivity());
        mlist = (ListView)view.findViewById(R.id.mlist);
        mlist.setAdapter(myHisAdapter);
        return view;
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


    class MyHisAdapter extends BaseAdapter{

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
