package com.sabayosja.fordcambodia.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;

public class ActivityAccessoryDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessory_detail);
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(ActivityAccessoryDetail.this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        initView();
    }

    private void initView() {
        try{
            initToolbar();
            initDetail(new JSONObject(getDataIntent().get(Global.arData[12])));
        }catch (Exception e){
            Log.e("Err",e.getMessage()+"");
        }

    }

    private void initDetail(JSONObject jsonObject) {
        try {
            final ImageView ivAccessory = findViewById(R.id.ivAccessory);
            final TextView tvEnName = findViewById(R.id.tvEnName);
            final TextView tvKmName = findViewById(R.id.tvKmName);
            final TextView tvPrice = findViewById(R.id.tvPrice);
            final TextView tvCode = findViewById(R.id.tvCode);
            tvCode.setText(jsonObject.getString(Global.arData[44]));
            tvPrice.setText(jsonObject.getString(Global.arData[45]));
            final String url_img = jsonObject.getJSONObject(Global.arData[9]).getString(Global.arData[10]);
            Picasso.get().load(url_img).placeholder(R.drawable.img_loading).error(R.drawable.img_loading).into(ivAccessory);
            final String enName = jsonObject.getJSONObject(Global.arData[18]).getString(Global.EN);
            final String kmName = jsonObject.getJSONObject(Global.arData[18]).getString(Global.KM);
            tvEnName.setText(enName);
            tvKmName.setText(kmName);
            findViewById(R.id.card).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phone = "017439909";
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    startActivity(intent);
                }
            });
        }catch (Exception e){
            Log.e("Err",e.getMessage()+"");
        }
    }

    private HashMap<String,String> getDataIntent(){
        return MyFunction.getInstance().getIntentHashMap(getIntent());
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
        tv_title.setText(getDataIntent().get(Global.arData[18]));
    }
}