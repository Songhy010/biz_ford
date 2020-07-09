package com.sabayosja.fordcambodia.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.listener.LoadDataListener;
import com.sabayosja.fordcambodia.android.listener.VolleyCallback;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;

import java.util.HashMap;

public class ActivityOtp extends ActivityController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(ActivityOtp.this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        Global.activityOtp = this;
        initView();
    }


    private void initView() {
        initToolbar();
        initNext();
        initCount();
        reSendClick();
    }


    private HashMap<String, String> getDataIntent() {
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
        tv_title.setText(getString(R.string.verify_code));
    }

    private void initCount() {
        final TextView tvCount = findViewById(R.id.tvCount);
        final LinearLayout linearResend = findViewById(R.id.linearResend);
        tvCount.setVisibility(View.VISIBLE);
        linearResend.setVisibility(View.GONE);
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvCount.setText(String.format("%s: %s",getString(R.string.second_remain),( millisUntilFinished / 1000)));
            }

            public void onFinish() {
                linearResend.setVisibility(View.VISIBLE);
                tvCount.setVisibility(View.GONE);

            }

        }.start();
    }

    private void initNext() {
        final TextView tvSendTo = findViewById(R.id.tvSendTo);
        tvSendTo.setText(String.format("%s %s", getString(R.string.send_to), getDataIntent().get(Global.arData[51])));
        findViewById(R.id.cardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initVerifyOTP();
            }
        });
    }

    private void reSendClick() {
        findViewById(R.id.tvReSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initResend();
            }
        });
    }

    private void initResend() {
        final String url = Global.arData[0] + Global.arData[1] + Global.arData[52];
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[51], getDataIntent().get(Global.arData[51]));
        loadDataServer(param, url, new LoadDataListener() {
            @Override
            public void onSuccess(String response) {
                if (response.equals("0")) {//is verified
                    initCount();
                } else {
                    MyFunction.getInstance().alertMessage(ActivityOtp.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                }
            }
        });
    }

    private void initVerifyOTP() {
        final String url = Global.arData[0] + Global.arData[1] + Global.arData[53];
        final EditText edtCode = findViewById(R.id.edtCode);
        final String code = edtCode.getText().toString();
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[51], getDataIntent().get(Global.arData[51]));
        param.put(Global.arData[54], code);
        loadDataServer(param, url, new LoadDataListener() {
            @Override
            public void onSuccess(String response) {
                if (response.equals("1")) {
                    finish();
                    if (Global.activityLogin != null)
                        MyFunction.getInstance().finishActivity(Global.activityLogin);
                    MyFunction.getInstance().saveText(ActivityOtp.this, Global.INFO_FILE, getDataIntent().get(Global.arData[51]));
                    MyFunction.getInstance().openActivity(ActivityOtp.this, ActivityYourBooking.class);
                } else if (response.equals("-5")) {
                    MyFunction.getInstance().alertMessage(ActivityOtp.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.wrong_code), 1);
                } else {
                    MyFunction.getInstance().alertMessage(ActivityOtp.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                }
            }
        });
    }

    private void loadDataServer(final HashMap<String, String> param, final String url, final LoadDataListener loadDataListener) {
        showDialog();
        MyFunction.getInstance().requestString(this, Request.Method.POST, url, param, new VolleyCallback() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Response", response);
                    loadDataListener.onSuccess(response);
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
                hideDialog();
            }

            @Override
            public void onErrorResponse(VolleyError e) {
                Log.e("Err", e.getMessage() + "");
                MyFunction.getInstance().alertMessage(ActivityOtp.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                hideDialog();
            }
        });
    }
}
