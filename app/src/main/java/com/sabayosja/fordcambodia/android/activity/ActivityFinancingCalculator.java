package com.sabayosja.fordcambodia.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;

import java.util.HashMap;

public class ActivityFinancingCalculator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financing_calculator);

        Tools.setSystemBarColor(this,R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(ActivityFinancingCalculator.this, getWindow().getDecorView().findViewById(android.R.id.content), 1);

        initView();
    }

    private void initView() {
        initToolbar();
        getDataIntent();
    }

    private void getDataIntent(){
        final EditText edtPrice = findViewById(R.id.edtPrice);
        final HashMap<String,String> map = MyFunction.getInstance().getIntentHashMap(getIntent());
        edtPrice.setText(map.get(Global.arData[32]));
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
        tv_title.setText(getString(R.string.financing));
    }
}
