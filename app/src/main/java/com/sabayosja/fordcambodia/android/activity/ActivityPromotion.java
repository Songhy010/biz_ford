package com.sabayosja.fordcambodia.android.activity;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.android.material.tabs.TabLayout;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.adapter.AdapterPager;
import com.sabayosja.fordcambodia.android.fragment.FragmentExterior;
import com.sabayosja.fordcambodia.android.fragment.FragmentPromotion;
import com.sabayosja.fordcambodia.android.listener.VolleyCallback;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class ActivityPromotion extends ActivityController {

    private TabLayout tabLayout;
    private AdapterPager adapterPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);
        Tools.setSystemBarColor(this,R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(ActivityPromotion.this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        initView();
    }

    private void initView() {
        initToolbar();
        loadDataServer();
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
        tv_title.setText(getString(R.string.new_and_promotion));
    }

    private void initPager(final JSONArray array) {
        try {
            tabLayout = findViewById(R.id.tabLayout);
            final String[] titles = new String[array.length()];
            for (int i = 0 ; i<array.length(); i++){
                JSONObject object = array.getJSONObject(i);
                titles[i] = object.getString(Global.arData[15]);
            }
            adapterPager = new AdapterPager(getSupportFragmentManager(),this,titles);
            //adapterPager.addFrag(FragmentPromotion.newInstance(null));
            for (int i = 0 ; i<array.length(); i++){
                JSONObject object = array.getJSONObject(i);
                adapterPager.addFrag(FragmentPromotion.newInstance(object.getJSONArray(Global.arData[50])));
            }

            final ViewPager view_pager = findViewById(R.id.viewPager);
            view_pager.setOffscreenPageLimit(4);

            view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    highLightCurrentTab();
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            view_pager.setAdapter(adapterPager);
            tabLayout.setupWithViewPager(view_pager);
            highLightCurrentTab();
        } catch (Exception e) {
            Log.e("Err", e.getMessage()+"");
        }
    }
    private void highLightCurrentTab() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            assert tab != null;
            tab.setCustomView(null);
            tab.setCustomView(adapterPager.getTabView(i));
        }
    }

    private void loadDataServer() {
        final String lang = MyFunction.getInstance().getText(ActivityPromotion.this, Global.arData[6]);
        final String url = Global.arData[0] + Global.arData[1] + Global.arData[5];
        final HashMap<String,String> param = new HashMap<>();
        param.put(Global.arData[6], lang);
        param.put(Global.arData[48], Global.arData[49]);
        showDialog();
        MyFunction.getInstance().requestString(this, Request.Method.POST, url, param, new VolleyCallback() {
            @Override
            public void onResponse(String response) {
                try {
                    if (MyFunction.getInstance().isValidJSON(response)) {
                        Log.e("Response",response);
                        final JSONArray array = new JSONArray(response);
                        initPager(array);
                    } else {
                        MyFunction.getInstance().alertMessage(ActivityPromotion.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                    }
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
                hideDialog();
            }
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.e("Err", e.getMessage() + "");
                MyFunction.getInstance().alertMessage(ActivityPromotion.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                hideDialog();
            }
        });
    }
}
