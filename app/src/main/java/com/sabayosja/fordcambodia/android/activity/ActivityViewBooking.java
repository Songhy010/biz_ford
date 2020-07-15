package com.sabayosja.fordcambodia.android.activity;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.adapter.AdapterListIssue;
import com.sabayosja.fordcambodia.android.listener.LoadDataListener;
import com.sabayosja.fordcambodia.android.listener.VolleyCallback;
import com.sabayosja.fordcambodia.android.model.ModelBooking;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;


public class ActivityViewBooking extends ActivityController {

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking);
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Global.ActivityViewBooking) {
                initListIssue();
                setText(R.id.tvMileage, ModelBooking.getInstance().getMileage());
            }
        }
    }

    @Override
    public void onBackPressed() {

    }

    private void initView() {
        initToolbar();
        initBookingView();
        initEditIssue();
        initEditCancelBooking();
        initCount();
    }


    private void initCount() {
        final TextView tvCount = findViewById(R.id.tvCount);
        tvCount.setVisibility(View.VISIBLE);
        countDownTimer = new CountDownTimer(300000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvCount.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

            }

            public void onFinish() {
                finish();
                closeStackActivity(Global.activitySelectIssue);
                closeStackActivity(Global.activitySelectTime);
                closeStackActivity(Global.activitySelectStation);
                closeStackActivity(Global.activitySelectDate);
                closeStackActivity(Global.activityLogin);
                closeStackActivity(Global.activitySelectCar);
                closeStackActivity(Global.activityYourBooking);
                closeStackActivity(Global.activitySelectService);
                closeStackActivity(Global.activitySelectMileage);
            }

        }.start();
    }

    private void initToolbar() {
        final TextView tv_title = findViewById(R.id.tv_title);
        final ImageView iv_ford = findViewById(R.id.iv_ford);
        final ImageView iv_search = findViewById(R.id.iv_search);
        final ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setImageDrawable(getResources().getDrawable(R.drawable.img_arrow));
        iv_back.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.MULTIPLY);
        iv_back.setVisibility(View.GONE);
        iv_ford.setVisibility(View.GONE);
        iv_search.setVisibility(View.GONE);
        tv_title.setText(getString(R.string.confirm_booking));
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

    private void setText(final @IdRes int id, final String text) {
        try {
            final TextView tv = findViewById(id);
            tv.setText(text);
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    private void initBookingView() {
        setText(R.id.tvName, ModelBooking.getInstance().getUserName());
        setText(R.id.tvPhone, getPhone());
        setText(R.id.tvModel, ModelBooking.getInstance().getModel());
        setText(R.id.tvYear, ModelBooking.getInstance().getModelYear());
        setText(R.id.tvPlate, ModelBooking.getInstance().getPlateNumber());
        setText(R.id.tvMileage, ModelBooking.getInstance().getMileage());
        setText(R.id.tvDate, ModelBooking.getInstance().getDate());
        setText(R.id.tvTime, ModelBooking.getInstance().getTime());
        setText(R.id.tvLocation, ModelBooking.getInstance().getStation());
        initListIssue();
    }

    private void initListIssue() {
        final RecyclerView recycleIssue = findViewById(R.id.recycleIssue);
        final LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        final AdapterListIssue adapterListIssue = new AdapterListIssue(ModelBooking.getInstance().getArrRepairName(), this);
        recycleIssue.setLayoutManager(manager);
        recycleIssue.setAdapter(adapterListIssue);
    }

    private void initEditIssue() {
        try {
            final LinearLayout linear = findViewById(R.id.linear);
            if (ModelBooking.getInstance().getServiceTypeID().equals("1")) {
                linear.setVisibility(View.GONE);
            }
            linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final HashMap<String, String> map = new HashMap<>();
                    map.put(Global.IS_EDIT, "1");
                    MyFunction.getInstance().openActivityForResult(ActivityViewBooking.this, ActivitySelectIssue.class, map, Global.ActivityViewBooking);
                }
            });
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    private void initEditCancelBooking() {
        findViewById(R.id.btnEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCancelTempBook(false);
            }
        });
        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCancelTempBook(true);
            }
        });

        findViewById(R.id.btnConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmBooking();
            }
        });
    }

    private void closeStackActivity(Activity activity) {
        try {
            if (activity != null)
                MyFunction.getInstance().finishActivity(activity);
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    private void initCancelTempBook(final Boolean isCancel) {
        final String url = Global.arData[0] + Global.arData[1] + Global.arData[5];
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[81], ModelBooking.getInstance().getDate());
        param.put(Global.arData[82], ModelBooking.getInstance().getTime());
        param.put(Global.arData[67], ModelBooking.getInstance().getStationID());
        param.put(Global.arData[79], "0");
        param.put(Global.arData[51], String.format("855%s", getPhone()));
        loadDataServer(param, url, new LoadDataListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    Log.e("response", response);
                    if (!response.isEmpty()) {
                        if (response.equals(Global.FAIL)) {
                            closeStackActivity(Global.activitySelectIssue);
                            closeStackActivity(Global.activitySelectTime);
                            closeStackActivity(Global.activitySelectStation);
                            closeStackActivity(Global.activitySelectDate);
                            if (isCancel) {
                                closeStackActivity(Global.activityLogin);
                                closeStackActivity(Global.activitySelectCar);
                                closeStackActivity(Global.activityYourBooking);
                                closeStackActivity(Global.activitySelectService);
                            }
                            MyFunction.getInstance().finishActivity(ActivityViewBooking.this);
                        } else {
                            MyFunction.getInstance().alertMessage(ActivityViewBooking.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                        }
                    } else {
                        MyFunction.getInstance().alertMessage(ActivityViewBooking.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                    }
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
            }
        });
    }

    private String initIssue() {
        String issue = "";
        if (ModelBooking.getInstance().getArrRepairName().size() > 0) {
            for (int i = 0; i < ModelBooking.getInstance().getArrRepairName().size(); i++) {
                issue += (ModelBooking.getInstance().getArrRepairName().get(i) + ",");
            }
        }
        return issue;
    }

    private void confirmBooking() {
        final String url = Global.arData[0] + Global.arData[1] + Global.arData[85];
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[70], ModelBooking.getInstance().getCarID());
        param.put(Global.arData[86], ModelBooking.getInstance().getServiceTypeID());
        param.put(Global.arData[51], String.format("855%s", getPhone()));
        param.put(Global.arData[87], initIssue());
        param.put(Global.arData[88], "");
        param.put(Global.arData[89], ModelBooking.getInstance().getDuration());
        param.put(Global.arData[59], ModelBooking.getInstance().getMileage());
        param.put(Global.arData[61], ModelBooking.getInstance().getDate());
        param.put(Global.arData[62], ModelBooking.getInstance().getTime());
        param.put(Global.arData[67], ModelBooking.getInstance().getStationID());
        param.put(Global.arData[90], ModelBooking.getInstance().getTokenFCM());
        param.put(Global.arData[91], MyFunction.getInstance().getAndroidID(ActivityViewBooking.this));
        param.put(Global.arData[56], ModelBooking.getInstance().getUserName());
        param.put(Global.arData[92], ModelBooking.getInstance().getMileageID());
        loadDataServer(param, url, new LoadDataListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    Log.e("response", response);
                    if (!response.isEmpty()) {
                        if (response.equals(Global.SUCCESS)) {
                            closeStackActivity(Global.activitySelectIssue);
                            closeStackActivity(Global.activitySelectTime);
                            closeStackActivity(Global.activitySelectStation);
                            closeStackActivity(Global.activitySelectDate);
                            closeStackActivity(Global.activityLogin);
                            closeStackActivity(Global.activitySelectCar);
                            closeStackActivity(Global.activityYourBooking);
                            closeStackActivity(Global.activitySelectService);
                            closeStackActivity(Global.activitySelectMileage);
                            MyFunction.getInstance().finishActivity(ActivityViewBooking.this);

                        } else {
                            MyFunction.getInstance().alertMessage(ActivityViewBooking.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                        }
                    } else {
                        MyFunction.getInstance().alertMessage(ActivityViewBooking.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                    }
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
            }
        });
    }

    private void loadDataServer(final HashMap<String, String> param, final String url, final LoadDataListener loadDataListener) {
        showDialog();
        countDownTimer.cancel();
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
                MyFunction.getInstance().alertMessage(ActivityViewBooking.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                hideDialog();
            }
        });
    }
}