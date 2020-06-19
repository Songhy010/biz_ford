package com.sabayosja.fordcambodia.android.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.adapter.AdapterBannerHome;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AdapterBannerHome adapter;
    private ViewPager viewPager;
    private Runnable runnable = null;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Tools.setSystemBarColor(this,R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(ActivityHome.this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        initView();
    }

    private void initView() {
        try{
            initNavigation();
            initToolbar();
            initPagerBanner(getIntentData().equals("") ? null : new JSONArray(getIntentData()));
            initTab();
        }catch (Exception e){
            Log.e("Err",e.getMessage()+"");
        }
    }

    private void initTab() {
        if(MyFunction.getInstance().isTablet(this)){
            final int height = MyFunction.getInstance().getHeightTab(this);
            final ConstraintLayout tab = findViewById(R.id.tab);
            tab.getLayoutParams().height = height;
        }
        findViewById(R.id.ivBook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFunction.getInstance().openActivity(ActivityHome.this,ActivityLogin.class);
            }
        });
    }

    private String getIntentData(){
        try{
            final HashMap<String,String> map = MyFunction.getInstance().getIntentHashMap(getIntent());
            final JSONObject object = new JSONObject(map.get(Global.arData[12]));
            return object.getString(Global.arData[11]);
        }catch (Exception e){
            Log.e("Err",e.getMessage()+"");
            return "";
        }
    }

    private void initToolbar() {
        final DrawerLayout drawer = findViewById(R.id.drawer);
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RtlHardcoded")
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
    }

    private void initNavigation() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu m = navigationView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);
            MyFont.getInstance().applyFontToMenuItem(ActivityHome.this,mi);
        }
    }

    private void initPagerBanner(JSONArray array) {
        final LinearLayout layout_dots = findViewById(R.id.layout_dots);
        try {
            adapter = new AdapterBannerHome(this, array);
            viewPager = findViewById(R.id.pager);
            int height  = MyFunction.getInstance().getBannerHeight(ActivityHome.this);

            if(MyFunction.getInstance().isTablet(ActivityHome.this)){
                height = MyFunction.getInstance().getBannerHeightTab(ActivityHome.this);
            }
            viewPager.getLayoutParams().height = height;
            viewPager.setAdapter(adapter);
            addBottomDots(layout_dots, adapter.getCount(), 0);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    addBottomDots(layout_dots, adapter.getCount(), position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            startAutoSlider(adapter.getCount());
        } catch (Exception e) {
            Log.e("Err ", e.getMessage() + "");
        }
    }

    private void addBottomDots(LinearLayout layout_dots, int size, int current) {
        ImageView[] dots = new ImageView[size];
        layout_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            int width_height = 40;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(5, 0, 5, 0);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle_outline);
            dots[i].setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
            layout_dots.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current].setImageResource(R.drawable.shape_circle);
            dots[current].setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void startAutoSlider(final int count) {
        handler.removeCallbacks(runnable);
        runnable = new Runnable() {
            @Override
            public void run() {
                int pos = viewPager.getCurrentItem();
                pos = pos + 1;
                if (pos >= count) pos = 0;
                viewPager.setCurrentItem(pos);
                handler.postDelayed(runnable, 4*1000);
            }
        };
        handler.postDelayed(runnable, 4*1000);

    }

    public void centerMenuOnClick(View view){
        switch (view.getId()){
            case R.id.btn_product:
                MyFunction.getInstance().openActivity(ActivityHome.this,ActivityProduct.class);
                break;
            case R.id.btn_services:
                MyFunction.getInstance().openActivity(ActivityHome.this,ActivityService.class);
                break;
            case R.id.btn_news:
                MyFunction.getInstance().openActivity(ActivityHome.this,ActivityPromotion.class);
                break;
            case R.id.btn_chat:
                initMessenger();
                break;
        }
    }
    private void initMessenger(){
        try{
            Uri uri = Uri.parse(Global.URI_MESSENGER + "295959650502207");
            Intent sendIntent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(sendIntent);
        }catch (Exception e){
            Log.e("Err",e.getMessage()+"");
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Global.URI_STORE+ Global.PACKAGE_MG)));
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.social:
                CircleImageView img = menuItem.getActionView().findViewById(R.id.fb);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ActivityHome.this, "Hello", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.power_by:
                Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
