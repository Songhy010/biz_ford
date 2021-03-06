package com.sabayosja.fordcambodia.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.adapter.AdapterNotification;
import com.sabayosja.fordcambodia.android.listener.LoadDataListener;
import com.sabayosja.fordcambodia.android.listener.VolleyCallback;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;

import org.json.JSONArray;

import java.util.HashMap;

public class ActivityNotification extends ActivityController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(ActivityNotification.this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        initView();
    }

    private void initView() {
        initToolbar();
        loadNotification();
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
        tv_title.setText(getString(R.string.notification));
    }

    private String getPhone() {
        final String phone = MyFunction.getInstance().getText(this, Global.INFO_FILE);
        final char isZero = phone.charAt(0);
        String phoneNumber = phone;
        if (isZero == '0') {
            phoneNumber = phone.substring(1, phone.length());
        }
        return phoneNumber;
    }

    private void initRecycler(final JSONArray arr){
        final LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        final AdapterNotification adapterNotification = new AdapterNotification(this,arr);
        final RecyclerView recyclerNotification = findViewById(R.id.recycleNotification);
        recyclerNotification.setLayoutManager(manager);
        recyclerNotification.setAdapter(adapterNotification);
    }

    private void loadNotification() {
        final String url = Global.arData[0] + Global.arData[1] + Global.arData[5];
        final String lang = MyFunction.getInstance().getText(ActivityNotification.this, Global.arData[6]);
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[99], "0");
        param.put(Global.arData[51], String.format("855%s", getPhone()));
        param.put(Global.arData[6], lang);
        loadDataServer(param, url, new LoadDataListener() {
            @Override
            public void onSuccess(String response) {
                Log.e("Err", response);
                try{
                    if (MyFunction.getInstance().isValidJSON(response)) {
                        initRecycler(new JSONArray(response));
                    } else {
                        MyFunction.getInstance().alertMessage(ActivityNotification.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                    }
                }catch (Exception e){
                    Log.e("Err",e.getMessage()+"");
                }
            }
        });
    }

    private void loadDataServer(final HashMap<String, String> param, final String url, final LoadDataListener loadDataListener) {
        showDialog();
        MyFunction.getInstance().requestString(this, Request.Method.POST, url, param, new VolleyCallback() {
            @Override
            public void onResponse(String response) {
                try {
                    loadDataListener.onSuccess(response);
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
                hideDialog();
            }

            @Override
            public void onErrorResponse(VolleyError e) {
                Log.e("Err", e.getMessage() + "");
                MyFunction.getInstance().alertMessage(ActivityNotification.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                hideDialog();
            }
        });
    }

}
