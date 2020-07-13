package com.sabayosja.fordcambodia.android.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.adapter.AdapterAssistant;
import com.sabayosja.fordcambodia.android.listener.VolleyCallback;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class ActivityAssistant extends ActivityController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assitant);
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        initView();
    }

    private void initView() {
        initToolbar();
        loadAssistant();
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
        tv_title.setText(getString(R.string.assistant));
    }

    private void initRecycler(JSONArray array) {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        final AdapterAssistant assistant = new AdapterAssistant(this, array);
        final RecyclerView recyclerView = findViewById(R.id.recycleAssistant);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(assistant);
    }

    private void loadAssistant() {
        final String lang = MyFunction.getInstance().getText(ActivityAssistant.this, Global.arData[6]);
        final String url = Global.arData[0] + Global.arData[1] + Global.arData[5];
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[6], lang);
        param.put(Global.arData[7], Global.arData[104]);
        showDialog();
        MyFunction.getInstance().requestString(this, Request.Method.POST, url, param, new VolleyCallback() {
            @Override
            public void onResponse(String response) {
                try {
                    if (MyFunction.getInstance().isValidJSON(response)) {
                        Log.e("response", response);
                        final JSONObject object = new JSONObject(response);
                        final JSONArray array = new JSONArray();
                        array.put(object.getJSONObject(Global.arData[106]));
                        array.put(object.getJSONObject(Global.arData[107]));
                        array.put(object.getJSONObject(Global.arData[108]));

                        initRecycler(array);
                    } else {
                        MyFunction.getInstance().alertMessage(ActivityAssistant.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                    }
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
                hideDialog();
            }

            @Override
            public void onErrorResponse(VolleyError e) {
                Log.e("Err", e.getMessage() + "");
                loadAssistant();
                hideDialog();
            }
        });
    }
}