package com.sabayosja.fordcambodia.android.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.android.material.navigation.NavigationView;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.adapter.AdapterBannerHome;
import com.sabayosja.fordcambodia.android.listener.LoadDataListener;
import com.sabayosja.fordcambodia.android.listener.VolleyCallback;
import com.sabayosja.fordcambodia.android.model.ModelBooking;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityHome extends ActivityController implements NavigationView.OnNavigationItemSelectedListener {

    private AdapterBannerHome adapter;
    private ViewPager viewPager;
    private Runnable runnable = null;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(ActivityHome.this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        initView();
    }

    private void initView() {
        try {
            initNavigation();
            initToolbar();
            initPagerBanner(getIntentData().equals("") ? null : new JSONArray(getIntentData()));
            initTab();
            initNotification();
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    private void initNotification() {
        findViewById(R.id.iv_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFunction.getInstance().openActivity(ActivityHome.this, ActivityNotification.class);
            }
        });
    }

    private void initTab() {
        if (MyFunction.getInstance().isTablet(this)) {
            final int height = MyFunction.getInstance().getHeight_95(this);
            final ConstraintLayout tab = findViewById(R.id.tab);
            tab.getLayoutParams().height = height;
        }
        findViewById(R.id.ivBook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HashMap<String, String> map = new HashMap<>();
                map.put(Global.arData[12], Global.ActivitySelectCar + "");
                MyFunction.getInstance().openActivityForResult(ActivityHome.this, ActivityLogin.class, map, Global.ActivityLogin);
            }
        });
    }

    private String getIntentData() {
        try {
            final HashMap<String, String> map = MyFunction.getInstance().getIntentHashMap(getIntent());
            final JSONObject object = new JSONObject(map.get(Global.arData[12]));
            return object.getString(Global.arData[11]);
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
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
        for (int i = 0; i < m.size(); i++) {
            final MenuItem mi = m.getItem(i);
            MyFont.getInstance().applyFontToMenuItem(ActivityHome.this, mi);
            if (i == 6) {
                Drawable drawable = m.findItem(R.id.assistant).getIcon();
                drawable.mutate();
                drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.red_500), PorterDuff.Mode.SRC_IN));
                mi.setIcon(drawable);
            }
            if (i == 7) {
                final View view = m.findItem(R.id.social).getActionView();
                loadSocial(view);
            }
        }
    }

    private void openWebView(final String endPoint, final String title) {
        final String lang = MyFunction.getInstance().getText(ActivityHome.this, Global.arData[6]);
        final String urlFeed = String.format("%s%s/%s", Global.arData[0], lang, endPoint);
        final HashMap<String, String> map = new HashMap<>();
        map.put(Global.arData[18], title);
        map.put(Global.arData[7], urlFeed);
        MyFunction.getInstance().openActivity(ActivityHome.this, ActivityWebviewDetail.class, map);
    }

    private void initPagerBanner(JSONArray array) {
        final LinearLayout layout_dots = findViewById(R.id.layout_dots);
        try {
            adapter = new AdapterBannerHome(this, array);
            viewPager = findViewById(R.id.pager);
            int height = MyFunction.getInstance().getHeight_650(ActivityHome.this);

            if (MyFunction.getInstance().isTablet(ActivityHome.this)) {
                height = MyFunction.getInstance().getHeight_450(ActivityHome.this);
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
                handler.postDelayed(runnable, 4 * 1000);
            }
        };
        handler.postDelayed(runnable, 4 * 1000);

    }

    public void centerMenuOnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_product:
                MyFunction.getInstance().openActivity(ActivityHome.this, ActivityProduct.class);
                break;
            case R.id.btn_services:
                MyFunction.getInstance().openActivity(ActivityHome.this, ActivityService.class);
                break;
            case R.id.btn_news:
                MyFunction.getInstance().openActivity(ActivityHome.this, ActivityPromotion.class);
                break;
            case R.id.btn_chat:
                initMessenger();
                break;
        }
    }

    private void initMessenger() {
        try {
            Uri uri = Uri.parse(Global.URI_MESSENGER + "295959650502207");
            Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(sendIntent);
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Global.URI_STORE + Global.PACKAGE_MG)));
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.power_by:
                initOpenSocial(Global.URL_POWER_BY);
                break;
            case R.id.vehecle:
                final HashMap<String, String> map = new HashMap<>();
                map.put(Global.arData[12], Global.ActivityVehicle + "");
                MyFunction.getInstance().openActivityForResult(ActivityHome.this, ActivityLogin.class, map, Global.ActivityLogin);
                break;
            case R.id.connect:
                MyFunction.getInstance().openActivity(ActivityHome.this, ActivityConnectCar.class);
                break;
            case R.id.comment:
                openWebView(Global.arData[102], getString(R.string.comment));
                break;
            case R.id.survey:
                openWebView(Global.arData[103], getString(R.string.survey));
                break;
            case R.id.setting:
                MyFunction.getInstance().openActivity(ActivityHome.this, ActivitySetting.class);
                break;
            case R.id.assistant:
                MyFunction.getInstance().openActivity(ActivityHome.this, ActivityAssistant.class);
                break;
        }
        return true;
    }

    private void initSocial(final JSONArray arr, final View view) {
        try {
            final ImageView[] iv = {view.findViewById(R.id.fb), view.findViewById(R.id.yt), view.findViewById(R.id.in), view.findViewById(R.id.ig)};
            for (int i = 0; i < iv.length; i++) {
                final JSONObject obj = arr.getJSONObject(i);
                ModelBooking.getInstance().setAccessoryContact(obj.getJSONArray(Global.arData[118]));
                ModelBooking.getInstance().setProductContact(obj.getJSONArray(Global.arData[119]));
                Picasso.get().load(obj.getString(Global.arData[9])).error(R.drawable.img_loading).placeholder(R.drawable.img_loading).into(iv[i]);
                iv[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            initOpenSocial(obj.getString(Global.arData[24]));
                        } catch (Exception e) {
                            Log.e("Err",e.getMessage()+"");
                        }
                    }
                });
            }
            
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }


    private void initOpenSocial(final String url) {
        Uri uri = Uri.parse(url);
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(likeIng);
    }

    public void loadSocial(final View view) {
        final String url = Global.arData[0] + Global.arData[1] + Global.arData[5];
        final String lang = MyFunction.getInstance().getText(ActivityHome.this, Global.arData[6]);
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[6], lang);
        param.put(Global.arData[7], Global.arData[117]);
        loadDataServer(param, url, new LoadDataListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    if (MyFunction.getInstance().isValidJSON(response)) {
                        final JSONArray arr = new JSONArray(response);
                        initSocial(arr, view);
                    }
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
            }
        });
    }

    private void loadDataServer(HashMap<String, String> param, final String url, final LoadDataListener loadData) {
        showDialog();
        MyFunction.getInstance().requestString(this, Request.Method.POST, url, param, new VolleyCallback() {
            @Override
            public void onResponse(String response) {
                try {
                    loadData.onSuccess(response);
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
                hideDialog();
            }

            @Override
            public void onErrorResponse(VolleyError e) {
                Log.e("Err", e.getMessage() + "");
                MyFunction.getInstance().alertMessage(ActivityHome.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                hideDialog();
            }
        });
    }
}