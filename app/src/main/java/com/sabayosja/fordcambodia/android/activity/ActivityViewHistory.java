package com.sabayosja.fordcambodia.android.activity;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.adapter.AdapterListIssue;
import com.sabayosja.fordcambodia.android.model.ModelBooking;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ActivityViewHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        initView();
    }

    private void initView() {
        initToolbar();
        initDataHistory();
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
        tv_title.setText(getString(R.string.history_booking));
    }

    private JSONObject getIntentData() {
        try {
            final String data = MyFunction.getInstance().getIntentHashMap(getIntent()).get(Global.arData[12]);
            return new JSONObject(data);
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
        return null;
    }

    private void setText(final @IdRes int id, final String text) {
        try {
            final TextView tv = findViewById(id);
            tv.setText(text);
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    private String getPhone() {
        final String phone = MyFunction.getInstance().getText(this, Global.INFO_FILE);
        final char isZero = phone.charAt(0);
        String phoneNumber = phone;
        if (isZero == '0') {
            phoneNumber = phone.substring(1, phone.length());
        }
        return phoneNumber;
    }

    private void initDataHistory() {

        try{
            setText(R.id.tvName, getIntentData().getString(Global.arData[56]));
            setText(R.id.tvPhone, getPhone());
            setText(R.id.tvModel, getIntentData().getString(Global.arData[57]));
            setText(R.id.tvYear, getIntentData().getString(Global.arData[42]));
            setText(R.id.tvPlate, getIntentData().getString(Global.arData[58]));
            setText(R.id.tvMileage, getIntentData().getString(Global.arData[59]));
            setText(R.id.tvDate, getIntentData().getString(Global.arData[61]));
            setText(R.id.tvTime, getIntentData().getString(Global.arData[62]));
            setText(R.id.tvLocation, getIntentData().getString(Global.arData[63]));
            setText(R.id.tvBookId, getIntentData().getString(Global.arData[64]));
            initListIssue();
        }catch (Exception e){
            Log.e("Err",e.getMessage()+"");
        }
    }

    private void initListIssue() {
        try{
            final String issue = getIntentData().getString(Global.arData[60]);
            ArrayList<String> issues = new ArrayList<>(Arrays.asList(issue.split(",")));
            final RecyclerView recycleIssue = findViewById(R.id.recycleIssue);
            final LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            final AdapterListIssue adapterListIssue = new AdapterListIssue(issues, this);
            recycleIssue.setLayoutManager(manager);
            recycleIssue.setAdapter(adapterListIssue);
        }catch (Exception e){
            Log.e("Err",e.getMessage()+"");
        }

    }
}