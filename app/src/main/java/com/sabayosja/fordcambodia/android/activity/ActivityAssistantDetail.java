package com.sabayosja.fordcambodia.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.adapter.AdapterAssistant;
import com.sabayosja.fordcambodia.android.adapter.AdapterAssistantDetail;
import com.sabayosja.fordcambodia.android.listener.VolleyCallback;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class ActivityAssistantDetail extends ActivityController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant_detail);
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        initView();
    }

    private void initView() {
        initToolbar();
        initData();
    }

    private void initData() {
        try{
            final JSONObject data = new JSONObject(getDataIntent().get(Global.arData[12]));
            final TextView tvWorkTime = findViewById(R.id.tvWorkTime);
            tvWorkTime.setText(String.format("+ %s",data.getString(Global.arData[109])));
            initRecycler(data.getJSONArray(Global.arData[110]));
        }catch (Exception e){
            Log.e("Err",e.getMessage()+"");
        }
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
        tv_title.setText(getDataIntent().get(Global.arData[18]));
    }

    private void initRecycler(JSONArray array) {
        final LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        final AdapterAssistantDetail assistantDetail = new AdapterAssistantDetail(this, array);
        final RecyclerView recycleService = findViewById(R.id.recycleService);
        recycleService.setLayoutManager(manager);
        recycleService.setAdapter(assistantDetail);
    }

    private HashMap<String, String> getDataIntent() {
        return MyFunction.getInstance().getIntentHashMap(getIntent());
    }
}
