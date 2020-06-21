package com.sabayosja.fordcambodia.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.adapter.AdapterYourBooking;
import com.sabayosja.fordcambodia.android.listener.LoadDataListener;
import com.sabayosja.fordcambodia.android.listener.VolleyCallback;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;

import org.json.JSONArray;

import java.util.HashMap;

public class ActivityYourBooking extends ActivityController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_booking);
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(ActivityYourBooking.this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        initView();
    }

    private void initView() {
        initToolbar();
        initYourBooking();
        initNewBook();
    }

    private void initNewBook() {
        findViewById(R.id.cardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyFunction.getInstance().openActivity(ActivityYourBooking.this,ActivitySelectCar.class);
            }
        });
    }

    private String phone() {
        return MyFunction.getInstance().getText(this, Global.INFO_FILE);
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
        tv_title.setText(getString(R.string.your_booking));
    }

    private void initRecyclerBooking(final JSONArray arrBooking){
        final LinearLayoutManager manager = new LinearLayoutManager(ActivityYourBooking.this, RecyclerView.VERTICAL,false);
        final RecyclerView recyclerBooking = findViewById(R.id.recycleBook);
        final AdapterYourBooking adapterYourBooking = new AdapterYourBooking(arrBooking,ActivityYourBooking.this);
        recyclerBooking.setLayoutManager(manager);
        recyclerBooking.setAdapter(adapterYourBooking);
    }

    public void initCancel(final HashMap<String,String> param) {
        final String url = Global.arData[0] + Global.arData[1] + Global.arData[65];
        loadDataServer(param, url, new LoadDataListener() {
            @Override
            public void onSuccess(String response) {
                try{
                    if(response.equals("1")){
                        MyFunction.getInstance().alertMessage(ActivityYourBooking.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.cancel_booking), 1);
                        initYourBooking();
                    }else{
                        MyFunction.getInstance().alertMessage(ActivityYourBooking.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                    }
                }catch (Exception e){
                    Log.e("Err",e.getMessage()+"");
                }
            }
        });
    }

    private void initYourBooking() {
        final String url = Global.arData[0] + Global.arData[1] + Global.arData[5];
        final String lang = MyFunction.getInstance().getText(ActivityYourBooking.this, Global.arData[6]);
        final char isZero = phone().charAt(0);
        String phoneNumber = phone();
        if(isZero == '0'){
            phoneNumber = phone().substring(1,phone().length());
        }
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[6], lang);
        param.put(Global.arData[7], Global.arData[55]+phoneNumber);
        loadDataServer(param, url, new LoadDataListener() {
            @Override
            public void onSuccess(String response) {
                try{
                    initRecyclerBooking(new JSONArray(response));
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
                MyFunction.getInstance().alertMessage(ActivityYourBooking.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                hideDialog();
            }
        });
    }
}
