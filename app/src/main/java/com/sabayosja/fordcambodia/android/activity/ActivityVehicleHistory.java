package com.sabayosja.fordcambodia.android.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.adapter.AdapterVehicleHistory;
import com.sabayosja.fordcambodia.android.listener.LoadDataListener;
import com.sabayosja.fordcambodia.android.listener.VolleyCallback;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class ActivityVehicleHistory extends ActivityController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_history);
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        initView();
    }

    private void initView() {
        initToolbar();
        initVehicle();
        initRecyclerHistory();
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
        tv_title.setText(getString(R.string.history));
    }

    private void initRecyclerHistory() {
        try{
            final JSONObject objHistory = new JSONObject(getDataIntent().get(Global.arData[12]));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            final AdapterVehicleHistory vehicleHistory = new AdapterVehicleHistory(this, objHistory.getJSONArray(Global.arData[115]));
            final RecyclerView recyclerView = findViewById(R.id.listcar);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(vehicleHistory);
        }catch (Exception e){
            Log.e("Err",e.getMessage()+"");
        }

    }

    private void initVehicle() {
        try {
            final CardView card = findViewById(R.id.card);
            final ImageView ivCar = findViewById(R.id.ivCar);
            card.getLayoutParams().height = MyFunction.getInstance().getHeight_400(this);
            final JSONObject objVehicle = new JSONObject(getDataIntent().get(Global.arData[70]));
            final String imaUrl = objVehicle.getJSONObject(Global.arData[9]).getString(Global.arData[10]);
            Picasso.get().load(imaUrl).error(R.drawable.img_loading).placeholder(R.drawable.img_loading).into(ivCar);
            final TextView tvModel = findViewById(R.id.tvModel);
            final TextView tvYear = findViewById(R.id.tvYear);
            tvModel.setText(objVehicle.getString(Global.arData[57]));
            tvYear.setText(String.format("%s / %s", objVehicle.getString(Global.arData[42]), objVehicle.getString(Global.arData[58])));
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

}