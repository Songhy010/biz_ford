package com.sabayosja.fordcambodia.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.Tools;

public class ActivitySelectIssue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_issue);
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
    }
}
