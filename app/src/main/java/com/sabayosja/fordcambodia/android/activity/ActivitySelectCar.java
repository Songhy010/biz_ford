package com.sabayosja.fordcambodia.android.activity;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.adapter.AdapterCarList;
import com.sabayosja.fordcambodia.android.listener.LoadDataListener;
import com.sabayosja.fordcambodia.android.listener.VolleyCallback;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;

import org.json.JSONArray;

import java.util.HashMap;

public class ActivitySelectCar extends ActivityController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_car);
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(ActivitySelectCar.this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        initView();
        Global.activitySelectCar = this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Global.ActivityAddVehicle) {
                initSelectList();
            }
        }
    }

    private void initView() {
        initToolbar();
        getPhone();
        initSelectList();
        initAdd();
    }

    private void initAdd() {
        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFunction.getInstance().openActivityForResult(ActivitySelectCar.this, ActivityAddVehicle.class, Global.ActivityAddVehicle);
            }
        });
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
        tv_title.setText(getString(R.string.select_car));
    }

    private void initCarList(final JSONArray arrCar) {
        final LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        final RecyclerView recycler = findViewById(R.id.recycle);
        final AdapterCarList adapterCarList = new AdapterCarList(arrCar, this);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapterCarList);
    }

    private void initSelectList() {
        final String url = Global.arData[0] + Global.arData[1] + Global.arData[5];
        final String lang = MyFunction.getInstance().getText(ActivitySelectCar.this, Global.arData[6]);
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[6], lang);
        param.put(Global.arData[7], Global.arData[69] + getPhone());
        loadDataServer(param, url, new LoadDataListener() {
            @Override
            public void onSuccess(String response) {
                Log.e("Err", response);
                try {
                    if (MyFunction.getInstance().isValidJSON(response)) {
                        initCarList(new JSONArray(response));
                    } else {
                        if (!response.isEmpty())
                            MyFunction.getInstance().alertMessage(ActivitySelectCar.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                    }
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
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
                    Log.e("Response", response);
                    loadDataListener.onSuccess(response);
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
                hideDialog();
            }

            @Override
            public void onErrorResponse(VolleyError e) {
                Log.e("Err", e.getMessage() + "");
                MyFunction.getInstance().alertMessage(ActivitySelectCar.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                hideDialog();
            }
        });
    }
}