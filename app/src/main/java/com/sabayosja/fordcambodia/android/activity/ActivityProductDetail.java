package com.sabayosja.fordcambodia.android.activity;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.adapter.AdapterBanner;
import com.sabayosja.fordcambodia.android.adapter.AdapterColor;
import com.sabayosja.fordcambodia.android.adapter.AdapterFeature;
import com.sabayosja.fordcambodia.android.adapter.AdapterGallery;
import com.sabayosja.fordcambodia.android.adapter.AdapterVideo;
import com.sabayosja.fordcambodia.android.listener.VolleyCallback;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class ActivityProductDetail extends ActivityController {

    private AdapterBanner adapterBanner;
    private ViewPager pagerBanner;

    private AdapterVideo adapterVideo;
    private ViewPager pagerVideo;

    private AdapterColor adapterColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Tools.setSystemBarColor(this, R.color.white);
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

    private String getIntentData() {
        final HashMap<String, String> map = MyFunction.getInstance().getIntentHashMap(getIntent());
        return map.get(Global.arData[7]);
    }

    private void initBanner(ViewPager pager, PagerAdapter adapter, int height) {

        try {

            if (MyFunction.getInstance().isTablet(ActivityProductDetail.this)) {
                height = MyFunction.getInstance().getHeight_450(ActivityProductDetail.this);
            }
            pager.getLayoutParams().height = height;
            pager.setAdapter(adapter);

            pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

    private void initGallery(final JSONObject object) {
        try {
            final JSONArray arrInter = object.getJSONArray(Global.arData[22]);
            final JSONArray arrExter = object.getJSONArray(Global.arData[23]);
            final JSONArray arrThumbnail = new JSONArray();
            arrThumbnail.put(arrExter.getJSONObject(0));
            arrThumbnail.put(arrInter.getJSONObject(0));
            final RecyclerView recyclerGallery = findViewById(R.id.recycleGallery);
            final LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
            final AdapterGallery adapter = new AdapterGallery(this, arrThumbnail);
            recyclerGallery.setLayoutManager(manager);
            recyclerGallery.setAdapter(adapter);
            findViewById(R.id.iv_moreGallery).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final HashMap<String,String> map = new HashMap<>();
                    map.put(Global.arData[12],object.toString());
                    MyFunction.getInstance().openActivity(ActivityProductDetail.this, ActivityGallery.class,map);
                }
            });
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }

    }

    private void initFeature(final JSONArray array) {
        final RecyclerView recycleKey = findViewById(R.id.recycleKey);
        final LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        final AdapterFeature adapter = new AdapterFeature(this, array);
        recycleKey.setLayoutManager(manager);
        recycleKey.setAdapter(adapter);
    }

    private void setProductBanner(final JSONArray arrBanner) {
        pagerBanner = findViewById(R.id.pager);
        if (arrBanner.length() > 0) {
            int height = MyFunction.getInstance().getHeight_350(ActivityProductDetail.this);
            adapterBanner = new AdapterBanner(ActivityProductDetail.this, arrBanner);
            initBanner(pagerBanner, adapterBanner, height);
        } else {
            pagerBanner.setVisibility(View.GONE);
        }
    }

    private void setVideo(final JSONArray arrVideo) {
        pagerVideo = findViewById(R.id.viewPager);
        if (arrVideo.length() > 0) {
            int height = MyFunction.getInstance().getHeight_400(ActivityProductDetail.this);
            adapterVideo = new AdapterVideo(ActivityProductDetail.this, arrVideo);
            initBanner(pagerVideo, adapterVideo, height);
        } else {
            final TextView tv_video = findViewById(R.id.tv_video);
            tv_video.setVisibility(View.GONE);
            pagerVideo.setVisibility(View.GONE);
        }
    }

    public void initColorlizer(final String url) {
        try {
            final ImageView iv_colorizer = findViewById(R.id.iv_colorizer);
            int height = MyFunction.getInstance().getHeight_400(ActivityProductDetail.this);
            iv_colorizer.getLayoutParams().height = height;
            Picasso.get().load(url).error(R.drawable.img_loading).error(R.drawable.img_loading).into(iv_colorizer);
            adapterColor.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    private void initColor(JSONArray arrColor) {
        try {
            final RecyclerView recycleColor = findViewById(R.id.recycleColor);
            final LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
            adapterColor = new AdapterColor(arrColor, this);
            recycleColor.setLayoutManager(manager);
            recycleColor.setAdapter(adapterColor);
            final String img_url = arrColor.getJSONObject(0).getJSONObject(Global.arData[9]).getString(Global.arData[10]);
            initColorlizer(img_url);

        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }

    }

    private void initPrice(final String price){
        final TextView tv_price = findViewById(R.id.tv_price);
        tv_price.setText(String.format("$"+price));
        findViewById(R.id.btn_phone1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = "012333975";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_phone2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = "063766661";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });
    }

    private void initCalculator(final String price){
        findViewById(R.id.btnFinance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HashMap<String,String> map = new HashMap<>();
                map.put(Global.arData[32],price);
                MyFunction.getInstance().openActivity(ActivityProductDetail.this,ActivityFinancingCalculator.class,map);
            }
        });
    }

    private void loadProductDetail() {
        final String lang = MyFunction.getInstance().getText(ActivityProductDetail.this, Global.arData[6]);
        final String url = Global.arData[0] + Global.arData[1] + Global.arData[5];
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[6], lang);
        param.put(Global.arData[7], Global.arData[19] + getIntentData());
        showDialog();
        MyFunction.getInstance().requestString(this, Request.Method.POST, url, param, new VolleyCallback() {
            @Override
            public void onResponse(String response) {
                try {
                    if (MyFunction.getInstance().isValidJSON(response)) {
                        Log.e("response", response);
                        final NestedScrollView view = findViewById(R.id.view);
                        view.setVisibility(View.VISIBLE);
                        final JSONObject object = new JSONObject(response);
                        final TextView tv_tilte = findViewById(R.id.tv_title);
                        tv_tilte.setText(object.getString(Global.arData[18]));

                        setProductBanner(object.getJSONArray(Global.arData[20]));
                        initGallery(object.getJSONObject(Global.arData[21]));
                        initFeature(object.getJSONArray(Global.arData[27]));
                        setVideo(object.getJSONArray(Global.arData[28]));
                        initColor(object.getJSONArray(Global.arData[30]));
                        initPrice(object.getString(Global.arData[32]));
                        initCalculator(object.getString(Global.arData[32]));
                    }else {
                        MyFunction.getInstance().alertMessage(ActivityProductDetail.this,getString(R.string.warning),getString(R.string.ok),getString(R.string.server_error),1);
                    }
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
                hideDialog();
            }

            @Override
            public void onErrorResponse(VolleyError e) {
                Log.e("Err", e.getMessage() + "");
                loadProductDetail();
                hideDialog();
            }
        });
    }
}
