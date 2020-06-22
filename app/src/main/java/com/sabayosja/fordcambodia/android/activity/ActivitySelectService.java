package com.sabayosja.fordcambodia.android.activity;

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
import com.sabayosja.fordcambodia.android.adapter.AdapterServiceType;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;

import org.json.JSONArray;

public class ActivitySelectService extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_service);
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(ActivitySelectService.this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        initView();
    }

    private void initView() {
        initToolbar();
        initListService();
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
        tv_title.setText(getString(R.string.select_service));
    }

    private void initListService(){
        final LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        final RecyclerView recycler = findViewById(R.id.recycle);
        final AdapterServiceType adapterServiceType = new AdapterServiceType(initData(),this);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapterServiceType);
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
        final int[] st = {115, 101, 114, 118, 105, 99, 101, 46, 106, 115, 111, 110};
        for (int value : st) {
            result.append((char) value);
        }
        return result.toString();
    }
}