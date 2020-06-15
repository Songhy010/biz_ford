package com.sabayosja.fordcambodia.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.adapter.AdapterBanner;
import com.sabayosja.fordcambodia.android.adapter.AdapterInstruction;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActivityInstruction extends AppCompatActivity {

    private AdapterInstruction adapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(ActivityInstruction.this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        initView();
    }

    private void initView() {
        initPagerBanner(initData());
        nextClick();
    }

    private HashMap<String,String> getIntentData(){
        try{
            return MyFunction.getInstance().getIntentHashMap(getIntent());
        }catch (Exception e){
            Log.e("Err",e.getMessage()+"");
            return null;
        }
    }

    private void nextClick() {
        findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int po = viewPager.getCurrentItem();
                if(po != 2){// check last index
                    viewPager.setCurrentItem(po+1);
                }else{
                    MyFunction.getInstance().saveText(ActivityInstruction.this, Global.FIRST_TIME,"0");
                    MyFunction.getInstance().openActivity(ActivityInstruction.this,ActivityHome.class,getIntentData());
                    MyFunction.getInstance().finishActivity(Global.activityChooseLanguage);
                    finish();
                }
            }
        });
    }

    private JSONArray initData() {
        try{
            final String global = MyFunction.getInstance().readFileAsset(this, getFilename());
            return new JSONArray(global);
        }catch (Exception e){
            Log.e("Err",e.getMessage()+"");
            return null;
        }
    }
    private String getFilename() {
        StringBuilder result = new StringBuilder();
        final int[] st = {105, 110, 115, 116, 114, 117, 99, 116, 105, 111, 110, 46, 106, 115, 111, 110};
        for (int value : st) {
            result.append((char) value);
        }
        return result.toString();
    }

    private void initPagerBanner(JSONArray array) {
        final LinearLayout layout_dots = findViewById(R.id.layout_dots);
        final TextView tv_skip = findViewById(R.id.tv_skip);
        final TextView tv_next = findViewById(R.id.tv_next);
        try {
            adapter = new AdapterInstruction(this, array);
            viewPager = findViewById(R.id.pager);
            viewPager.setAdapter(adapter);
            MyFunction.getInstance().addBottomDots(this,layout_dots, adapter.getCount(), 0);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    MyFunction.getInstance().addBottomDots(ActivityInstruction.this,layout_dots, adapter.getCount(), position);
                    tv_skip.setText("");
                    tv_next.setText(getString(R.string.next));
                    if(position==2){//last position
                        tv_skip.setText(getString(R.string.skip));
                        tv_next.setText(getString(R.string.start_use));
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } catch (Exception e) {
            Log.e("Err ", e.getMessage() + "");
        }
    }
}