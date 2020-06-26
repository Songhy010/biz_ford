package com.sabayosja.fordcambodia.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.adapter.AdapterTime;
import com.sabayosja.fordcambodia.android.listener.LoadDataListener;
import com.sabayosja.fordcambodia.android.listener.VolleyCallback;
import com.sabayosja.fordcambodia.android.model.ModelBooking;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;

import org.json.JSONArray;

import java.util.HashMap;

public class ActivitySelectTime extends ActivityController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_time);
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        initView();
    }

    private void initView() {
        initToolbar();
        initTime();
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
        tv_title.setText(getString(R.string.select_time));
    }

    private void initListTime(final RecyclerView recycler){
        final GridLayoutManager manager = new GridLayoutManager(this,4);
        final AdapterTime adapterTime = new AdapterTime(this,null);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapterTime);
    }

    private void initTime() {
        final String url = Global.arData[0] + Global.arData[1] + Global.arData[5];
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[67], ModelBooking.getInstance().getStationID());
        param.put(Global.arData[68], ModelBooking.getInstance().getDate());
        loadDataServer(param, url, new LoadDataListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    Log.e("response", response);
                    if (!response.isEmpty()) {
                        if (MyFunction.getInstance().isValidJSON(response)) {
                            final RecyclerView recyclerMorning = findViewById(R.id.recycleMorning);
                            initListTime(recyclerMorning);
                            final RecyclerView recyclerAfternoon = findViewById(R.id.recycleAfternoon);
                            initListTime(recyclerAfternoon);
                        } else {
                            MyFunction.getInstance().alertMessage(ActivitySelectTime.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                        }
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
                MyFunction.getInstance().alertMessage(ActivitySelectTime.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                hideDialog();
            }
        });
    }
}
