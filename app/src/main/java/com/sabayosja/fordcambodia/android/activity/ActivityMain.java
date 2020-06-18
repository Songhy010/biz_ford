package com.sabayosja.fordcambodia.android.activity;


import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.listener.VolleyCallback;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class ActivityMain extends ActivityController {

    private String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Tools.setSystemBarColor(this, R.color.colorPrimary);
        initView();
    }

    private void initView() {
        initLocal();
        initSplash();
        loadHome();
    }

    private void initSplash() {
        final String img_url = MyFunction.getInstance().getText(this,Global.SPLASH_SCREEN);
        final ImageView iv_splash = findViewById(R.id.iv_splash);
        if(img_url.isEmpty()){
            iv_splash.setImageDrawable(getResources().getDrawable(R.drawable.img_splash));
        }else{
            Picasso.get().load(img_url).into(iv_splash);
        }
    }

    private void initLocal() {
        lang = MyFunction.getInstance().getText(ActivityMain.this, Global.arData[6]);
        if (lang.equals("")) {
            MyFunction.getInstance().saveText(ActivityMain.this, Global.arData[6], Global.EN);
        }
    }

    private void loadHome(){
        final String url = Global.arData[0]+Global.arData[1]+Global.arData[5];
        final HashMap<String,String> param = new HashMap<>();
        param.put(Global.arData[6],lang);
        param.put(Global.arData[7],Global.arData[13]);
        MyFunction.getInstance().requestString(this, Request.Method.POST, url, param, new VolleyCallback() {
            @Override
            public void onResponse(String response) {
                try {
                    if(MyFunction.getInstance().isValidJSON(response)){
                        final ImageView iv_splash = findViewById(R.id.iv_splash);
                        final JSONObject object = new JSONObject(response);
                        final String img_url = object.getJSONObject(Global.arData[8]).getJSONObject(Global.arData[9]).getString(Global.arData[10]);
                        MyFunction.getInstance().saveText(ActivityMain.this,Global.SPLASH_SCREEN,img_url);
                        Picasso.get().load(img_url).placeholder(R.drawable.img_splash).error(R.drawable.img_splash).into(iv_splash);
                        final String isFirst = MyFunction.getInstance().getText(ActivityMain.this, Global.FIRST_TIME);
                        final HashMap<String,String> map = new HashMap<>();
                        map.put(Global.arData[12],response);
                        if (isFirst.equals("0")) {
                            MyFunction.getInstance().openActivity(ActivityMain.this, ActivityHome.class,map);
                            finish();
                        } else {
                            MyFunction.getInstance().openActivity(ActivityMain.this, ActivityChooseLanguage.class, map);
                            finish();
                        }
                    }else {
                        MyFunction.getInstance().alertMessage(ActivityMain.this,getString(R.string.warning),getString(R.string.ok),getString(R.string.server_error),1);
                    }
                }catch (Exception e){
                    Log.e("Err",e.getMessage()+"");
                }
            }

            @Override
            public void onErrorResponse(VolleyError e) {
                Log.e("Err",e.getMessage()+"");
                loadHome();
            }
        });
    }
}