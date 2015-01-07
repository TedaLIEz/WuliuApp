package com.aicre.wuliuapp.app.fragment;


import android.animation.AnimatorSet;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.aicre.wuliuapp.app.R;
import com.aicre.wuliuapp.app.activity.DetailActivity;
import com.aicre.wuliuapp.app.dao.Contacts;
import com.aicre.wuliuapp.app.dao.ContactsDB;
import com.aicre.wuliuapp.app.util.Globles;
import com.aicre.wuliuapp.app.util.String2Request;
import com.aicre.wuliuapp.app.view.XListView;
import com.android.volley.Response;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wei on 14-7-5.
 */
public class HomeFragment extends BaseFragment implements XListView.IXListViewListener{
    private ArrayList<HashMap<String, String>> list;
    private MyBaseAdapter mMyBaseAdapter;
    private XListView mLIstView;
    private SimpleDateFormat format;
    private ProgressDialog pd;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        ActionBar bar = getActivity().getActionBar();
        setHasOptionsMenu(true);
        bar.setTitle("首页");
        //bar.setIcon(R.drawable.icon);
        //bar.setIcon(R.drawable.icon);
        setHasOptionsMenu(true);
        format=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        // executeRequest(new JsonRequest<>()
        pd = new ProgressDialog(this.getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //pd.setTitle("加载中...");
        pd.setMessage("加载中...");
        pd.show();
        executeRequest(new String2Request(Globles.INFOLIST_URL, "utf-8", responseListener(),
                errorListener()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container,savedInstanceState);
        list = new ArrayList<HashMap<String, String>>();
        View view = inflater.inflate(R.layout.fragment_home, null, false);

        forceShowOverflowMenu();

        mLIstView = (XListView) view.findViewById(R.id.lei_x_list_view);
        mMyBaseAdapter = new MyBaseAdapter(getActivity().getBaseContext());
        mLIstView.setAdapter(mMyBaseAdapter);
        mLIstView.setXListViewListener(this);
        mLIstView.setPullRefreshEnable(true);
        mLIstView.setPullLoadEnable(false);
        mLIstView.setRefreshTime(""+format.format(new Date(System.currentTimeMillis())));
        mLIstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position < 1)
                    return;
                HashMap<String,String> map = list.get(position-1) ;
                Intent i = new Intent(getActivity(), DetailActivity.class);
                i.putExtra("id",map);
                startActivity(i);

            }
        });
        return view;
    }

    private Response.Listener<String> responseListener(){
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                JsontoArrayList(s);
                Log.d("Lei","refresh succeed!");
                mMyBaseAdapter.notifyDataSetInvalidated();
                mLIstView.stopRefresh();
                pd.dismiss();
            }
        };
    }

    @Override
    public void onRefresh() {
        mLIstView.setRefreshTime(""+format.format(new Date(System.currentTimeMillis())));

        executeRequest(new String2Request(Globles.INFOLIST_URL,"UTF-8" , responseListener(),
                errorListener()));
    }

    @Override
    public void onLoadMore() {
        mLIstView.stopLoadMore();
    }

    public ArrayList<HashMap<String,String>> JsontoArrayList(String Json){
        int num = -1;
        JSONObject obj;
        HashMap<String,String> map;
        list.clear();
        try {
            obj = new JSONObject(Json);
            num = Integer.parseInt(obj.getString("num"));
            JSONArray objArray = obj.getJSONArray("array");
            for (int i = 0;i<num ; i++){
                obj = objArray.getJSONObject(i);
                map = new HashMap<String, String>();
                map.put("id",obj.getString("id"));
                map.put("fp",obj.getString("fp"));
                map.put("fc",obj.getString("fc"));
                map.put("fd",obj.getString("fd"));
                map.put("tp",obj.getString("tp"));
                map.put("tc",obj.getString("tc"));
                map.put("gn",obj.getString("gn"));
                map.put("ct",obj.getString("ct"));
                //map.put("gt",obj.getString("gt"));
                map.put("gm",obj.getString("gm"));
                map.put("gw",obj.getString("gw"));
                map.put("cs",obj.getString("cs"));
                map.put("name",obj.getString("name"));
                map.put("phone",obj.getString("phone"));
                map.put("date",obj.getString("date"));
                map.put("deadline",obj.getString("deadline"));
                list.add(map);
            }
        }catch (JSONException e){
            e.toString();
        }
        return list;
    }

    class MyBaseAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        public MyBaseAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_infor,null);
                holder = new ViewHolder();
                holder.mStart = (TextView)convertView.findViewById(R.id.start);
                holder.mDestination = (TextView)convertView.findViewById(R.id.destination);
                holder.mKindCargo = (TextView) convertView.findViewById(R.id.kind_cargo);
                holder.mKindCar = (TextView) convertView.findViewById(R.id.kind_car);
                holder.mWeight = (TextView) convertView.findViewById(R.id.weight);
                holder.mLength = (TextView) convertView.findViewById(R.id.length);
                holder.mContectMan = (TextView) convertView.findViewById(R.id.contact_man);
                holder.mNumber = (TextView) convertView.findViewById(R.id.number);
                holder.mTime = (TextView) convertView.findViewById(R.id.time);
                holder.mBtn = (Button)convertView.findViewById(R.id.mbtn);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            final HashMap<String,String> map = list.get(position) ;

            if((map.get("fp").length()+map.get("fc").length())>6){
                int size=map.get("fp").length()+map.get("fc").length();
                StringBuffer sb=new StringBuffer(map.get("fp")+map.get("fc"));
                sb.delete(5,size);
                sb.insert(5,"...");
                holder.mStart.setText(sb.toString());
            }else{
                holder.mStart.setText(map.get("fp")+map.get("fc"));
            }
            if((map.get("tp").length()+map.get("tc").length())>6){
                int size=map.get("tp").length()+map.get("tc").length();
                StringBuffer sb=new StringBuffer(map.get("tp")+map.get("tc"));
                sb.delete(5,size);
                sb.insert(5,"...");
                holder.mDestination.setText(sb.toString());
            }else{
                holder.mDestination.setText(map.get("tp")+map.get("tc"));
            }

            holder.mKindCargo.setText(map.get("gn"));
            holder.mKindCar.setText(map.get("ct"));

            String dan[] = map.get("gm").split("/");
            if(!map.get("gw").equals("")) {
                holder.mWeight.setText(map.get("gw") + dan[1]);
            }
            holder.mLength.setText(map.get("cs"));
            holder.mContectMan.setText(map.get("name"));
            holder.mNumber.setText(map.get("phone"));
            if(!map.get("date").equals("")) {
                String a[] = map.get("date").split("/");
                String date = a[2] + "-" + a[0] + "-" + a[1];
                holder.mTime.setText(date);
            }
            holder.mBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String str = "tel:"+map.get("phone");

                    /*
                    添加数据库
                     */
                    ContactsDB mDB = new ContactsDB(getActivity());
                    SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss");
                    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                    String time = formatter.format(curDate);
                    mDB.add(new Contacts(Integer.parseInt(map.get("id")),map.get("name"),map.get("phone"),time ));

                    Intent i = new Intent("android.intent.action.DIAL", Uri.parse(str));
                    startActivity(i);
                }
            });
            return convertView;
        }
    }

    /**存放控件*/
    public final class ViewHolder{
        public TextView mStart;
        public TextView mDestination;
        public TextView mKindCargo;
        public TextView mWeight;
        public TextView mKindCar;
        public TextView mLength;
        public TextView mContectMan;
        public TextView mNumber;
        public TextView mTime;
        public Button mBtn;
    }

    public void search(){
        AlertDialog.Builder dialog =new AlertDialog.Builder(this.getActivity());
        LayoutInflater inflater = (LayoutInflater)this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.dialog_search,null);
        dialog.setView(layout);
        final EditText start_edit = (EditText)layout.findViewById(R.id.start_address);
        final EditText finish_edit = (EditText)layout.findViewById(R.id.finish_address);
        dialog.setPositiveButton("搜索",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String start = start_edit.getText().toString();
                final String finish = finish_edit.getText().toString();
                executeRequest(new String2Request(Globles.INFOLIST_URL,"utf-8" , responseListener(),
                        errorListener()){
                    protected Map<String,String> getParams(){
                        Map<String,String> m = new HashMap<String, String>();
                        m.put("from",start);
                        m.put("to",finish);
                        return m;
                    }
                });
            }
        });

        dialog.show();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
        MenuItemCompat.setShowAsAction(menu.findItem(R.id.action_search),MenuItemCompat.SHOW_AS_ACTION_ALWAYS);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                search();
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("HomeFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("HomeFragment");
    }


    private void forceShowOverflowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this.getActivity());
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
