package com.sabayosja.fordcambodia.android.activity;

import androidx.cardview.widget.CardView;
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
import com.sabayosja.fordcambodia.android.adapter.AdapterAccessory;
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

public class ActivityAccessory extends ActivityController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessory);
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(ActivityAccessory.this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        initView();
    }

    private void initView() {
        initToolbar();
        loadModel();
        loadAccessory("");//all
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
        tv_title.setText(getString(R.string.accessory_price));
    }

    private void initSelectModelYear(final JSONArray array,final String modelID) {
        try {
            final CardView cardYear = findViewById(R.id.cardYear);
            final TextView tvYear = findViewById(R.id.tvYear);
            final String[] model = new String[array.length() + 1];
            model[0] = getString(R.string.all_year);
            for (int i = 0; i < array.length(); i++) {
                final JSONObject object = array.getJSONObject(i);
                model[i + 1] = object.getString(Global.arData[42]);
            }
            MyFunction.getInstance().initSelectItem(ActivityAccessory.this, cardYear, tvYear, model, 1, new SelectedListener() {
                @Override
                public void onSelected(int str) {
                    try {
                        if (str == 0) {
                            loadAccessory("_"+modelID);
                        } else {
                            final JSONObject object = array.getJSONObject(str - 1);
                            loadAccessory("_" + object.getString(Global.arData[47]));
                        }
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
            final CardView card_model = findViewById(R.id.card_model);
            final TextView tv_model = findViewById(R.id.tv_model);
            final String[] model = new String[array.length() + 1];
            model[0] = getString(R.string.all_model);
            for (int i = 0; i < array.length(); i++) {
                final JSONObject object = array.getJSONObject(i);
                model[i + 1] = object.getString(Global.arData[39]);
            }
            MyFunction.getInstance().initSelectItem(ActivityAccessory.this, card_model, tv_model, model, 1, new SelectedListener() {
                @Override
                public void onSelected(int str) {
                    try {
                        if (str == 0) {
                            loadAccessory("");
                        } else {
                            final TextView tvYear = findViewById(R.id.tvYear);
                            tvYear.setText(getString(R.string.all_year));
                            final JSONObject object = array.getJSONObject(str - 1);
                            initSelectModelYear(object.getJSONArray(Global.arData[41]),object.getString(Global.arData[46]));
                            loadAccessory("_" + object.getString(Global.arData[46]));

                        }
                    } catch (Exception e) {
                        Log.e("Err", e.getMessage() + "");
                    }
                }
            });
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    private void initAccessory(final JSONArray array) {
        final RecyclerView recyclerAccessory = findViewById(R.id.recycleAccessory);
        final GridLayoutManager manager = new GridLayoutManager(this, 2);
        final AdapterAccessory adapterAccessory = new AdapterAccessory(array, this);
        recyclerAccessory.setLayoutManager(manager);
        recyclerAccessory.setAdapter(adapterAccessory);
    }

    private void loadAccessory(final String id) {
        final String lang = MyFunction.getInstance().getText(ActivityAccessory.this, Global.arData[6]);
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[6], lang);
        param.put(Global.arData[7], Global.arData[43] + id);
        loadDataServer(param, new LoadDataListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    final JSONArray array = new JSONArray(response);
                    initAccessory(array);
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
            }
        });
    }

    private void loadModel() {
        final String lang = MyFunction.getInstance().getText(ActivityAccessory.this, Global.arData[6]);
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
                        MyFunction.getInstance().alertMessage(ActivityAccessory.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                    }
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
                hideDialog();
            }

            @Override
            public void onErrorResponse(VolleyError e) {
                Log.e("Err", e.getMessage() + "");
                MyFunction.getInstance().alertMessage(ActivityAccessory.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                hideDialog();
            }
        });
    }
}