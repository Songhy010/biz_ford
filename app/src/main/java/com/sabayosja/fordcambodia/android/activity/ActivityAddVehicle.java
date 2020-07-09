package com.sabayosja.fordcambodia.android.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.listener.LoadDataListener;
import com.sabayosja.fordcambodia.android.listener.SelectedListener;
import com.sabayosja.fordcambodia.android.listener.VolleyCallback;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class ActivityAddVehicle extends ActivityController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(ActivityAddVehicle.this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        initView();
    }

    private void initView() {
        initToolbar();
        initCheckBox();
        loadModel();
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
        tv_title.setText(getString(R.string.add_vehicle));
    }

    private void initCheckBox() {
        final CheckBox checkBox = findViewById(R.id.ckNoPlate);
        final RelativeLayout relativePrefix = findViewById(R.id.relativePrefix);
        final EditText edtPlate = findViewById(R.id.edtPlate);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    relativePrefix.setVisibility(View.GONE);
                    edtPlate.setHint(getString(R.string.chassis));
                } else {
                    relativePrefix.setVisibility(View.VISIBLE);
                    edtPlate.setHint(getString(R.string.plate));
                }
            }
        });
    }

    private void initSelectModelYear(final JSONArray array) {
        try {
            final RelativeLayout relativeYear = findViewById(R.id.relativeYear);
            final EditText edtYear = findViewById(R.id.edtYear);
            final String[] model = new String[array.length()];
            for (int i = 0; i < array.length(); i++) {
                final JSONObject object = array.getJSONObject(i);
                model[i] = object.getString(Global.arData[42]);
            }
            MyFunction.getInstance().initSelectItem(ActivityAddVehicle.this, relativeYear, edtYear, model, 1, new SelectedListener() {
                @Override
                public void onSelected(int str) {
                    try {

                    } catch (Exception e) {
                        Log.e("Err", e.getMessage() + "");
                    }
                }
            });
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    private void initSelectModel(final JSONArray array) {
        try {
            final RelativeLayout relativeModel = findViewById(R.id.relativeModel);
            final EditText edtModel = findViewById(R.id.edtModel);
            final String[] model = new String[array.length()];
            for (int i = 0; i < array.length(); i++) {
                final JSONObject object = array.getJSONObject(i);
                model[i] = object.getString(Global.arData[39]);
            }
            MyFunction.getInstance().initSelectItem(ActivityAddVehicle.this, relativeModel, edtModel, model, 1, new SelectedListener() {
                @Override
                public void onSelected(int str) {
                    try {
                        final JSONObject object = array.getJSONObject(str);
                        initSelectModelYear(object.getJSONArray(Global.arData[41]));
                    } catch (Exception e) {
                        Log.e("Err", e.getMessage() + "");
                    }
                }
            });
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    private void loadAccessory(final String id) {
        final String lang = MyFunction.getInstance().getText(ActivityAddVehicle.this, Global.arData[6]);
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[6], lang);
        param.put(Global.arData[7], Global.arData[43] + id);
        loadDataServer(param, new LoadDataListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    final JSONArray array = new JSONArray(response);
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
            }
        });
    }

    private void loadModel() {
        final String lang = MyFunction.getInstance().getText(ActivityAddVehicle.this, Global.arData[6]);
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[6], lang);
        param.put(Global.arData[7], Global.arData[38]);
        loadDataServer(param, new LoadDataListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    final JSONArray array = new JSONArray(response);
                    initSelectModel(array);
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
            }
        });
    }

    private void loadDataServer(HashMap<String, String> param, final LoadDataListener loadData) {
        final String url = Global.arData[0] + Global.arData[1] + Global.arData[5];
        showDialog();
        MyFunction.getInstance().requestString(this, Request.Method.POST, url, param, new VolleyCallback() {
            @Override
            public void onResponse(String response) {
                try {
                    if (MyFunction.getInstance().isValidJSON(response)) {
                        loadData.onSuccess(response);
                    } else {
                        MyFunction.getInstance().alertMessage(ActivityAddVehicle.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                    }
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
                hideDialog();
            }

            @Override
            public void onErrorResponse(VolleyError e) {
                Log.e("Err", e.getMessage() + "");
                MyFunction.getInstance().alertMessage(ActivityAddVehicle.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                hideDialog();
            }
        });
    }
}