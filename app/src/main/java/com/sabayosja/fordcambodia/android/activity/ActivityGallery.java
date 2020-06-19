package com.sabayosja.fordcambodia.android.activity;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.adapter.AdapterPager;
import com.sabayosja.fordcambodia.android.fragment.FragmentExterior;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class ActivityGallery extends ActivityController {

    private TabLayout tabLayout;
    private AdapterPager adapterGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Tools.setSystemBarColor(this,R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(ActivityGallery.this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        initView();
    }

    private void initView(){
        initToolbar();
        initPager();
    }

    private JSONArray arrFromIntent(String objName){
        try {
            final HashMap<String,String> map = MyFunction.getInstance().getIntentHashMap(getIntent());
            final JSONObject object = new JSONObject(map.get(Global.arData[12]));
            return object.getJSONArray(objName);
        }catch (Exception e){
            Log.e("Err",e.getMessage()+"");
            return  null;
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
        tv_title.setText(getString(R.string.gallery));
    }

    private void highLightCurrentTab() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            assert tab != null;
            tab.setCustomView(null);
            tab.setCustomView(adapterGallery.getTabView(i));
        }
    }

    private void initPager() {
        try {
            tabLayout = findViewById(R.id.tabLayout);
            final String[] titles = {getString(R.string.exterior), getString(R.string.interior)};
            adapterGallery = new AdapterPager(getSupportFragmentManager(),this,titles);
            adapterGallery.addFrag(FragmentExterior.newInstance(arrFromIntent(Global.arData[22])));
            adapterGallery.addFrag(FragmentExterior.newInstance(arrFromIntent(Global.arData[23])));
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
            view_pager.setAdapter(adapterGallery);
            tabLayout.setupWithViewPager(view_pager);
            highLightCurrentTab();
        } catch (Exception e) {
            Log.e("Err", e.getMessage());
        }
    }
}