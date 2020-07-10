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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class ActivityAddVehicle extends ActivityController {

    private String model;
    private String year;
    private String vehicle;
    private String plate;


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
        loadPrefix();
        initAdd();
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

    private void initSelectItem(final JSONArray array, final String objName, View viewSelect, View viewDisplay, final SelectedListener selectedListener) {
        try {
            final String[] model = new String[array.length()];
            for (int i = 0; i < array.length(); i++) {
                final JSONObject object = array.getJSONObject(i);
                model[i] = object.getString(objName);
            }
            MyFunction.getInstance().initSelectItem(ActivityAddVehicle.this, viewSelect, viewDisplay, model, 1, new SelectedListener() {
                @Override
                public void onSelected(int str) {
                    selectedListener.onSelected(str);
                }
            });
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    private String getPhone() {
        final String phone = MyFunction.getInstance().getText(this,Global.INFO_FILE);
        return phone;
    }

    private void initSelectModelYear(final JSONArray array,final String modelID) {
        try {

            final RelativeLayout relativeYear = findViewById(R.id.relativeYear);
            final TextView tvYear = findViewById(R.id.tvYear);
            initSelectItem(array, Global.arData[42], relativeYear, tvYear, new SelectedListener() {
                @Override
                public void onSelected(int str) {
                    try{
                        String id = "";
                        final JSONObject object = array.getJSONObject(str);
                        if(object.getString(Global.arData[47]).equals("") || object.isNull(Global.arData[47])){
                            id = modelID;
                        }else{
                            id = object.getString(Global.arData[47]);
                        }
                        year = object.getString(Global.arData[47]);
                        loadImage(id);
                    }catch (Exception e){
                        Log.e("Err",e.getMessage()+"");
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
            final TextView tvModel = findViewById(R.id.tvModel);
            initSelectItem(array, Global.arData[39], relativeModel, tvModel, new SelectedListener() {
                @Override
                public void onSelected(int str) {
                    try {
                        final TextView tvYear = findViewById(R.id.tvYear);
                        tvYear.setText(getString(R.string.all_year));
                        final JSONObject object = array.getJSONObject(str);
                        model = object.getString(Global.arData[46]);
                        initSelectModelYear(object.getJSONArray(Global.arData[41]),object.getString(Global.arData[46]));
                    } catch (Exception e) {
                        Log.e("Err", e.getMessage() + "");
                    }
                }
            });
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    private void initSelectPrefix(final JSONArray array) {
        try {
            final RelativeLayout relativePrefix = findViewById(R.id.relativePrefix);
            final TextView tvPrefix = findViewById(R.id.tvPrefix);
            initSelectItem(array, Global.arData[94], relativePrefix, tvPrefix, new SelectedListener() {
                @Override
                public void onSelected(int str) {
                    final EditText edtPlate = findViewById(R.id.edtPlate);
                    if (str != 0) {
                        try {
                            final JSONObject obj = array.getJSONObject(str);
                            edtPlate.setText(String.format("%s 2", obj.getString(Global.arData[94])));
                        } catch (Exception e) {
                            Log.e("Err", e.getMessage() + "");
                        }
                    } else {
                        edtPlate.setText("");
                        edtPlate.setHint(getString(R.string.plate));
                    }
                }
            });
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    private void loadPrefix() {
        final String url = Global.arData[0] + Global.arData[1] + Global.arData[93];
        final String lang = MyFunction.getInstance().getText(ActivityAddVehicle.this, Global.arData[6]);
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[6], lang);
        loadDataServer(param, url, new LoadDataListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    if (MyFunction.getInstance().isValidJSON(response)) {
                        final JSONObject obj = new JSONObject(response);
                        if (obj.getString(Global.arData[72]).equals(Global.SUCCESS)) {
                            final JSONArray array = obj.getJSONArray(Global.arData[12]);
                            initSelectPrefix(array);
                        }
                    }
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
            }
        });
    }

    private void loadImage(final String id) {
        final String url = Global.arData[0] + Global.arData[1] + Global.arData[5];
        final String lang = MyFunction.getInstance().getText(ActivityAddVehicle.this, Global.arData[6]);
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[6], lang);
        param.put(Global.arData[95], id);
        loadDataServer(param, url, new LoadDataListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    if (MyFunction.getInstance().isValidJSON(response)) {
                        final JSONObject obj = new JSONObject(response);
                        vehicle = obj.getString(Global.arData[98]);
                        final ImageView ivCar = findViewById(R.id.ivCar);
                        final JSONObject objImage =  obj.getJSONObject(Global.arData[9]);
                        Picasso.get().load(objImage.getString(Global.arData[10])).placeholder(R.drawable.img_loading).error(R.drawable.img_loading).into(ivCar);
                    }
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
            }
        });
    }

    private void loadModel() {
        final String url = Global.arData[0] + Global.arData[1] + Global.arData[5];
        final String lang = MyFunction.getInstance().getText(ActivityAddVehicle.this, Global.arData[6]);
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[6], lang);
        param.put(Global.arData[7], Global.arData[38]);
        loadDataServer(param, url, new LoadDataListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    if (MyFunction.getInstance().isValidJSON(response)) {
                        final JSONArray array = new JSONArray(response);
                        initSelectModel(array);
                    }
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
            }
        });
    }

    private void initAdd(){
        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAddCar();
            }
        });
    }

    private void loadAddCar(){
        final String url = Global.arData[0] + Global.arData[1] + Global.arData[96];
        final EditText edtPlate = findViewById(R.id.edtPlate);
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[70], vehicle);
        param.put(Global.arData[57], model);
        param.put(Global.arData[42],year);
        param.put(Global.arData[51],getPhone());
        param.put(Global.arData[58],edtPlate.getText().toString());
        param.put(Global.arData[97],"1");
        loadDataServer(param, url, new LoadDataListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    if(response.equals(Global.SUCCESS)){
                        setResult(RESULT_OK);
                        MyFunction.getInstance().finishActivity(ActivityAddVehicle.this);
                    }
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
            }
        });
    }

    private void loadDataServer(HashMap<String, String> param, final String url, final LoadDataListener loadData) {

        showDialog();
        MyFunction.getInstance().requestString(this, Request.Method.POST, url, param, new VolleyCallback() {
            @Override
            public void onResponse(String response) {
                try {
                    loadData.onSuccess(response);
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