package com.sabayosja.fordcambodia.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.adapter.AdapterTime;
import com.sabayosja.fordcambodia.android.listener.LoadDataListener;
import com.sabayosja.fordcambodia.android.listener.VolleyCallback;
import com.sabayosja.fordcambodia.android.model.ModelBooking;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import static com.sabayosja.fordcambodia.android.util.Global.activitySelectDate;
import static com.sabayosja.fordcambodia.android.util.Global.activitySelectIssue;
import static com.sabayosja.fordcambodia.android.util.Global.activitySelectStation;
import static com.sabayosja.fordcambodia.android.util.Global.activitySelectTime;

public class ActivitySelectTime extends ActivityController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_time);
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        Global.activitySelectTime = this;
        initView();
    }

    private void initView() {
        initToolbar();
        initTime();
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
        tv_title.setText(getString(R.string.select_time));
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

    private void initListTime(final RecyclerView recycler, final JSONArray array) {
        final GridLayoutManager manager = new GridLayoutManager(this, 4);
        final AdapterTime adapterTime = new AdapterTime(this, array);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapterTime);
    }

    private JSONArray readAssetData(final String fileName, final JSONArray arrBlock) {
        try {
            final String strArr = MyFunction.getInstance().readFileAsset(ActivitySelectTime.this, fileName);
            final JSONArray arrTime = new JSONArray(strArr);
            for (int i = 0; i < arrTime.length(); i++) {
                final JSONObject objTime = arrTime.getJSONObject(i);
                for (int j = 0 ; j < arrBlock.length() ; j++){
                    final JSONObject objBlock = arrBlock.getJSONObject(j);
                    if(objTime.get(Global.arData[66]).equals(objBlock.getString(Global.arData[84]))){
                        objTime.put(Global.arData[79],"0");
                    }
                }
                arrTime.put(i,objTime);
            }
            return arrTime;
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
        return null;
    }


    private void initTime() {
        final String url = Global.arData[0] + Global.arData[1] + Global.arData[5];
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[67], ModelBooking.getInstance().getStationID());
        param.put(Global.arData[68], ModelBooking.getInstance().getDate());
        loadDataServer(param, url, new LoadDataListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    Log.e("response", response);
                    if (!response.isEmpty()) {
                        if (MyFunction.getInstance().isValidJSON(response)) {
                            final RecyclerView recyclerMorning = findViewById(R.id.recycleMorning);
                            initListTime(recyclerMorning, readAssetData(Global.MORNING, new JSONArray(response)));
                            final RecyclerView recyclerAfternoon = findViewById(R.id.recycleAfternoon);
                            initListTime(recyclerAfternoon, readAssetData(Global.AFTERNOON, new JSONArray(response)));
                        } else {
                            MyFunction.getInstance().alertMessage(ActivitySelectTime.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                        }
                    }
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
            }
        });
    }

    public void initCheckAvailable(final String time) {
        final String url = Global.arData[0] + Global.arData[1] + Global.arData[83];
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[68], ModelBooking.getInstance().getDate());
        param.put(Global.arData[66], time);
        param.put(Global.arData[63], ModelBooking.getInstance().getStationID());
        loadDataServer(param, url, new LoadDataListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    Log.e("response", response);
                    if (!response.isEmpty()) {
                        if (!response.equals(Global.FAIL)) {
                            initAddTempBook();
                        } else {
                            MyFunction.getInstance().alertMessage(ActivitySelectTime.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.unavailable_time), 1);
                        }
                    } else {
                        MyFunction.getInstance().alertMessage(ActivitySelectTime.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                    }
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
            }
        });
    }

    public void initAddTempBook() {
        final String url = Global.arData[0] + Global.arData[1] + Global.arData[5];
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[81], ModelBooking.getInstance().getDate());
        param.put(Global.arData[82], ModelBooking.getInstance().getTime());
        param.put(Global.arData[67], ModelBooking.getInstance().getStationID());
        param.put(Global.arData[79], "1");
        param.put(Global.arData[51], String.format("855%s", getPhone()));
        loadDataServer(param, url, new LoadDataListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    Log.e("response", response);
                    if (!response.isEmpty()) {
                        if (response.equals(Global.FAIL)) {
                            MyFunction.getInstance().openActivity(ActivitySelectTime.this, ActivityViewBooking.class);
                        } else {
                            MyFunction.getInstance().alertMessage(ActivitySelectTime.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.unavailable_time), 1);
                        }
                    }
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
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
                MyFunction.getInstance().alertMessage(ActivitySelectTime.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                hideDialog();
            }
        });
    }
}
