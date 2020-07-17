package com.sabayosja.fordcambodia.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.listener.VolleyCallback;
import com.sabayosja.fordcambodia.android.model.ModelBooking;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;

import org.json.JSONArray;

import java.util.HashMap;

public class ActivityLogin extends ActivityController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(ActivityLogin.this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        Global.activityLogin = this;
        initView();
    }

    private void initView() {
        initToolbar();
        initNext();
        setPhone();
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
        tv_title.setText(getString(R.string.user_info));
    }

    private void setPhone() {
        final String phone = MyFunction.getInstance().getText(this, Global.INFO_FILE);
        if (!phone.isEmpty()) {
            final EditText edtPhone = findViewById(R.id.edtPhone);
            edtPhone.setText(phone);
        }
    }

    private HashMap<String, String> getDataIntent() {
        return MyFunction.getInstance().getIntentHashMap(getIntent());
    }


    private void initNext() {
        findViewById(R.id.cardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText edtName = findViewById(R.id.edtName);
                final EditText edtPhone = findViewById(R.id.edtPhone);
                ModelBooking.getInstance().setUserName(edtName.getText().toString());
                if (!edtPhone.getText().toString().isEmpty() && !edtName.getText().toString().isEmpty()) {
                    if (edtPhone.getText().toString().length() > 8 && edtPhone.getText().toString().length() < 11)
                        loadDataServer();
                    else
                        MyFunction.getInstance().alertMessage(ActivityLogin.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.wrong_phone), 1);
                } else {
                    MyFunction.getInstance().alertMessage(ActivityLogin.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.require_input), 1);
                }
            }
        });
    }

    private void loadDataServer() {
        final String url = Global.arData[0] + Global.arData[1] + Global.arData[52];
        final EditText edtPhone = findViewById(R.id.edtPhone);
        final String phone = edtPhone.getText().toString();
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[51], phone);
        showDialog();
        MyFunction.getInstance().requestString(this, Request.Method.POST, url, param, new VolleyCallback() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Response", response);
                    if (response.equals("1")) {//is verified
                        MyFunction.getInstance().saveText(ActivityLogin.this, Global.INFO_FILE, phone);
                        if (getDataIntent().get(Global.arData[12]).equals(Global.ActivityVehicle + ""))
                            MyFunction.getInstance().openActivity(ActivityLogin.this, ActivityVehicle.class);
                        else if (getDataIntent().get(Global.arData[12]).equals(Global.ActivitySelectCar + ""))
                            MyFunction.getInstance().openActivity(ActivityLogin.this, ActivityYourBooking.class);
                        MyFunction.getInstance().finishActivity(ActivityLogin.this);
                    } else if (response.equals("0")) {
                        final HashMap<String, String> map = new HashMap<>();
                        map.put(Global.arData[51], phone);
                        map.put(Global.arData[12], getDataIntent().get(Global.arData[12]));
                        MyFunction.getInstance().openActivity(ActivityLogin.this, ActivityOtp.class, map);
                    } else {
                        MyFunction.getInstance().alertMessage(ActivityLogin.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                    }
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
                hideDialog();
            }

            @Override
            public void onErrorResponse(VolleyError e) {
                Log.e("Err", e.getMessage() + "");
                MyFunction.getInstance().alertMessage(ActivityLogin.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                hideDialog();
            }
        });
    }
}
