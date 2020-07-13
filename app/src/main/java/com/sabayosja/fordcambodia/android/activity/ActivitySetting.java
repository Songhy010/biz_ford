package com.sabayosja.fordcambodia.android.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.Tools;

public class ActivitySetting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        initView();
    }
    private void initView() {
        initToolbar();
        initSwitchChecked();
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
        tv_title.setText(getString(R.string.setting));
    }

    private void initSwitchChecked(){
       final RadioButton rdbEnglish, rdbKhmer;

       rdbEnglish = findViewById(R.id.rdb_english);
       rdbKhmer = findViewById(R.id.rdb_khmer);

       if(rdbEnglish.isChecked()){
          rdbKhmer.setChecked(false);
       }else{
           rdbEnglish.setChecked(true);
       }
    }
}