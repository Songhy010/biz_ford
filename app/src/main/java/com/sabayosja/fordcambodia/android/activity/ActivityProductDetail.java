package com.sabayosja.fordcambodia.android.activity;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;


import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.adapter.AdapterBanner;
import com.sabayosja.fordcambodia.android.adapter.AdapterFeature;
import com.sabayosja.fordcambodia.android.adapter.AdapterGallery;
import com.sabayosja.fordcambodia.android.listener.VolleyCallback;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class ActivityProductDetail extends ActivityController {

    private AdapterBanner adapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Tools.setSystemBarColor(this,R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(ActivityProductDetail.this, getWindow().getDecorView().findViewById(android.R.id.content), 3);
        initView();

    }

    private void initView() {
        initToolbar();
        loadProductDetail();
    }

    private void initToolbar() {

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
    }

    private String getIntentData(){
        final HashMap<String,String> map = MyFunction.getInstance().getIntentHashMap(getIntent());
        return map.get(Global.arData[7]);
    }

    private void initBanner(final JSONArray array){

        try {
            adapter = new AdapterBanner(this, array);
            viewPager = findViewById(R.id.pager);
            int height  = MyFunction.getInstance().getProductBannerHeight(ActivityProductDetail.this);

            if(MyFunction.getInstance().isTablet(ActivityProductDetail.this)){
                height = MyFunction.getInstance().getBannerHeightTab(ActivityProductDetail.this);
            }
            viewPager.getLayoutParams().height = height;
            viewPager.setAdapter(adapter);

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } catch (Exception e) {
            Log.e("Err ", e.getMessage() + "");
        }
    }
    private void initGallery(final JSONObject object){
        try{
            final JSONArray arrInter = object.getJSONArray(Global.arData[22]);
            final JSONArray arrExter = object.getJSONArray(Global.arData[23]);
            final JSONArray arrThumbnail = new JSONArray();
            arrThumbnail.put(arrExter.getJSONObject(0));
            arrThumbnail.put(arrInter.getJSONObject(0));
            final RecyclerView recyclerGallery = findViewById(R.id.recycleGallery);
            final LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
            final AdapterGallery adapter = new AdapterGallery(this,arrThumbnail);
            recyclerGallery.setLayoutManager(manager);
            recyclerGallery.setAdapter(adapter);
        }catch (Exception e){
            Log.e("Err",e.getMessage()+"");
        }

    }
    private void initFeature(final JSONArray array){
        final RecyclerView recycleKey = findViewById(R.id.recycleKey);
        final LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        final AdapterFeature adapter = new AdapterFeature(this,array);
        recycleKey.setLayoutManager(manager);
        recycleKey.setAdapter(adapter);
    }

    private void loadProductDetail() {
        final String lang  = MyFunction.getInstance().getText(ActivityProductDetail.this, Global.arData[6]);
        final String url = Global.arData[0]+Global.arData[1]+Global.arData[5];
        final HashMap<String,String> param = new HashMap<>();
        param.put(Global.arData[6],lang);
        param.put(Global.arData[7],Global.arData[19]+getIntentData());
        showDialog();
        MyFunction.getInstance().requestString(this, Request.Method.POST, url, param, new VolleyCallback() {
            @Override
            public void onResponse(String response) {
                try {
                    if(MyFunction.getInstance().isValidJSON(response)){
                        Log.e("response",response);
                        final JSONObject object = new JSONObject(response);
                        final TextView tv_tilte = findViewById(R.id.tv_title);
                        tv_tilte.setText(object.getString(Global.arData[18]));
                        initBanner(object.getJSONArray(Global.arData[20]));
                        initGallery(object.getJSONObject(Global.arData[21]));
                        initFeature(object.getJSONArray("features"));
                        //initRecycle(array);
                    }
                }catch (Exception e){
                    Log.e("Err",e.getMessage()+"");
                }
                hideDialog();
            }

            @Override
            public void onErrorResponse(VolleyError e) {
                Log.e("Err",e.getMessage()+"");
                loadProductDetail();
                hideDialog();
            }
        });
    }
}
