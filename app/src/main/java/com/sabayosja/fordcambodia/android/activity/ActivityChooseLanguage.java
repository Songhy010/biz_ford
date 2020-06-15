package com.sabayosja.fordcambodia.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;

import java.util.HashMap;

public class ActivityChooseLanguage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);
        MyFont.getInstance().setFont(ActivityChooseLanguage.this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        initView();
    }

    private void initView() {
        Click();
    }

    private void Click() {
        findViewById(R.id.btnKm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFunction.getInstance().openActivity(ActivityChooseLanguage.this, ActivityInstruction.class,getIntentData());
            }
        });
        findViewById(R.id.btnEn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFunction.getInstance().openActivity(ActivityChooseLanguage.this, ActivityInstruction.class,getIntentData());
            }
        });
    }
    private HashMap<String,String> getIntentData(){
        try{
            return MyFunction.getInstance().getIntentHashMap(getIntent());
        }catch (Exception e){
            Log.e("Err",e.getMessage()+"");
            return null;
        }
    }
}

