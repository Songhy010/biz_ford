package com.sabayosja.fordcambodia.android.activity;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.adapter.AdapterMaintenanceType;
import com.sabayosja.fordcambodia.android.listener.SelectedListener;
import com.sabayosja.fordcambodia.android.listener.VolleyCallback;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class ActivityMaintenanceTip extends ActivityController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_tip);
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(ActivityMaintenanceTip.this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        initView();
    }

    private void initView() {
        initToolbar();
        loadMaintenance();
        loadMaintenanceList("");
    }

    private void initToolbar() {
        final TextView tv_title = findViewById(R.id.tv_title);
        final ImageView iv_ford = findViewById(R.id.iv_ford);
        final ImageView iv_search = findViewById(R.id.iv_search);
        final ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setImageDrawable(getResources().getDrawable(R.drawable.img_arrow));
        iv_back.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.MULTIPLY);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        iv_ford.setVisibility(View.GONE);
        iv_search.setVisibility(View.GONE);
        tv_title.setText(getString(R.string.maintenance_tip));
    }

    private void initSelectType(final JSONArray arrType) {
        try {
            final String[] type = new String[arrType.length() + 1];
            type[0] = getString(R.string.all_type);
            for (int i = 0; i < arrType.length(); i++) {
                final JSONObject object = arrType.getJSONObject(i);
                type[i + 1] = object.getString(Global.arData[35]);
            }
            final RelativeLayout view = findViewById(R.id.view);
            final TextView tv_type = findViewById(R.id.tv_type);
            MyFunction.getInstance().initSelectItem(ActivityMaintenanceTip.this, view, tv_type, type, 1, new SelectedListener() {
                @Override
                public void onSelected(int str) {
                    try{
                        if(str == 0){
                            loadMaintenanceList("");
                        }else {
                            final JSONObject object = arrType.getJSONObject(str-1);
                            loadMaintenanceList("_"+object.getString(Global.arData[7]));
                        }

                    }catch (Exception e){
                        Log.e("Er",e.getMessage()+"");
                    }
                }
            });
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    private void initMaintenanceList(final JSONArray arr){
        final RecyclerView recycleMaintenance = findViewById(R.id.recycleMaintenance);
        final LinearLayoutManager manager = new LinearLayoutManager(ActivityMaintenanceTip.this,RecyclerView.VERTICAL,false);
        final AdapterMaintenanceType adapterMaintenanceType = new AdapterMaintenanceType(arr,ActivityMaintenanceTip.this);
        recycleMaintenance.setLayoutManager(manager);
        recycleMaintenance.setAdapter(adapterMaintenanceType);
    }

    private void loadMaintenanceList(final String id){
        final String lang = MyFunction.getInstance().getText(ActivityMaintenanceTip.this, Global.arData[6]);
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[6], lang);
        param.put(Global.arData[7], Global.arData[36]+id);
        loadDataServer(param, new LoadData() {
            @Override
            public void onSuccess(String response) {
                try {
                    final JSONArray arr = new JSONArray(response);
                    initMaintenanceList(arr);
                } catch (Exception e) {
                    Log.e("Err",e.getMessage()+"");
                }
            }
        });
    }

    private void loadMaintenance() {
        final String lang = MyFunction.getInstance().getText(ActivityMaintenanceTip.this, Global.arData[6]);
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[6], lang);
        param.put(Global.arData[7], Global.arData[34]);
        loadDataServer(param, new LoadData() {
            @Override
            public void onSuccess(String response) {
                try {
                    final JSONArray arrType = new JSONArray(response);
                    initSelectType(arrType);
                } catch (Exception e) {
                    Log.e("err", e.getMessage()+"");
                }
            }
        });
    }

    private void loadDataServer(HashMap<String, String> param, final LoadData loadData) {
        final String url = Global.arData[0] + Global.arData[1] + Global.arData[5];
        showDialog();
        MyFunction.getInstance().requestString(this, Request.Method.POST, url, param, new VolleyCallback() {
            @Override
            public void onResponse(String response) {
                try {
                    if (MyFunction.getInstance().isValidJSON(response)) {
                        loadData.onSuccess(response);
                    } else {
                        MyFunction.getInstance().alertMessage(ActivityMaintenanceTip.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                    }
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
                hideDialog();
            }
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.e("Err", e.getMessage() + "");
                MyFunction.getInstance().alertMessage(ActivityMaintenanceTip.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                hideDialog();
            }
        });
    }

    interface LoadData {
        void onSuccess(String response);
    }
}