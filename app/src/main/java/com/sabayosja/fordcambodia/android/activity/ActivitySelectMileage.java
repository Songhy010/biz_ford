package com.sabayosja.fordcambodia.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.adapter.AdapterMileage;
import com.sabayosja.fordcambodia.android.listener.DialogCallBack;
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

public class ActivitySelectMileage extends ActivityController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mileage);
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(ActivitySelectMileage.this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        Global.activitySelectMileage = this;
        initView();
    }

    private void initView() {
        initToolbar();
        initMileage();
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
        tv_title.setText(getString(R.string.select_mileage));
    }

    private void initListMileage(final JSONArray array) {
        final LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        final RecyclerView recycler = findViewById(R.id.recycle);
        final AdapterMileage adapterMileage = new AdapterMileage(this, array);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapterMileage);
    }

    public void showOtherDialog(final String mile) {
        MyFunction.getInstance().showDialog(this, R.layout.dialog_other_mile, new DialogCallBack() {
            @Override
            public void listener(final Dialog dialog) {

                dialog.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            final EditText edtOther = dialog.findViewById(R.id.edtOther);
                            final int otherMile = Integer.parseInt(edtOther.getText().toString());
                            final int minMile = Integer.parseInt(mile);
                            if (otherMile < minMile) {
                                MyFunction.getInstance().alertMessage(ActivitySelectMileage.this, getString(R.string.information), getString(R.string.ok), getString(R.string.select_require), 1);
                            } else {
                                MyFunction.getInstance().openActivity(ActivitySelectMileage.this, ActivitySelectStation.class);
                            }
                        } catch (Exception e) {
                            Log.e("Err", e.getMessage() + "");
                        }
                        dialog.dismiss();
                    }
                });

                dialog.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


            }
        });
    }

    private void initMileage() {
        final String url = Global.arData[0] + Global.arData[1] + Global.arData[71];
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[70], ModelBooking.getInstance().getCarID());
        loadDataServer(param, url, new LoadDataListener() {
            @Override
            public void onSuccess(String response) {
                Log.e("Response", response);
                try {
                    if (MyFunction.getInstance().isValidJSON(response)) {
                        final JSONObject object = new JSONObject(response);
                        if (object.getString(Global.arData[72]).equals("1"))
                            initListMileage(object.getJSONArray(Global.arData[12]));
                        else
                            MyFunction.getInstance().alertMessage(ActivitySelectMileage.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                    } else {
                        MyFunction.getInstance().alertMessage(ActivitySelectMileage.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
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
                MyFunction.getInstance().alertMessage(ActivitySelectMileage.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                hideDialog();
            }
        });
    }
}
