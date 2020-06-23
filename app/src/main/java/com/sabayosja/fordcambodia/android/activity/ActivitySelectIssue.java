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
import com.sabayosja.fordcambodia.android.adapter.AdapterIssue;
import com.sabayosja.fordcambodia.android.listener.LoadDataListener;
import com.sabayosja.fordcambodia.android.listener.VolleyCallback;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;

import org.json.JSONArray;

import java.util.HashMap;

public class ActivitySelectIssue extends ActivityController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_issue);
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        initView();
    }

    private void initView() {
        initToolbar();
        initIssue();
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
        tv_title.setText(getString(R.string.select_issue));
    }

    private void initListIssue(final JSONArray array){
        final LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        final RecyclerView recycler = findViewById(R.id.recycle);
        final AdapterIssue adapterIssue = new AdapterIssue(array,this);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapterIssue);
    }

    private void initIssue(){
        final String lang = MyFunction.getInstance().getText(ActivitySelectIssue.this, Global.arData[6]);
        final String url = Global.arData[0] + Global.arData[1] + Global.arData[5];
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[6],lang);
        param.put(Global.arData[7],Global.arData[60]);
        loadDataServer(param, url, new LoadDataListener() {
            @Override
            public void onSuccess(String response) {
                try{
                    Log.e("response",response);
                    if(MyFunction.getInstance().isValidJSON(response)){
                        initListIssue(new JSONArray(response));
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
                MyFunction.getInstance().alertMessage(ActivitySelectIssue.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                hideDialog();
            }
        });
    }

}
