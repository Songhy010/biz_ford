package com.sabayosja.fordcambodia.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.adapter.AdapterRoadSide;
import com.sabayosja.fordcambodia.android.adapter.AdapterTerm;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class ActivityRoadside extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roadside);
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        initView();
    }

    private void initView() {
        initToolbar();
        initData();
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
        tv_title.setText(getDataIntent().get(Global.arData[18]));
    }

    private HashMap<String, String> getDataIntent() {
        return MyFunction.getInstance().getIntentHashMap(getIntent());
    }

    private void initData(){
        try{
            final JSONObject object = new JSONObject(getDataIntent().get(Global.arData[12]));
            final ImageView ivBanner = findViewById(R.id.tvBanner);
            final int height = MyFunction.getInstance().getHeight_350(this);
            ivBanner.getLayoutParams().height = height;
            final String urlImage = object.getJSONObject(Global.arData[9]).getString(Global.arData[10]);
            Picasso.get().load(urlImage).error(R.drawable.img_loading).placeholder(R.drawable.img_loading).into(ivBanner);
            final AdapterTerm adapterTerm = new AdapterTerm(this,object.getJSONArray(Global.arData[112]));
            final RecyclerView recycleTerm = findViewById(R.id.recycleTerm);
            initRecycler(recycleTerm,adapterTerm);

            final AdapterRoadSide adapterRoadSide = new AdapterRoadSide(this,object.getJSONArray(Global.arData[113]));
            final RecyclerView recycleRoadside = findViewById(R.id.recycleRoadside);
            initRecycler(recycleRoadside,adapterRoadSide);

        }catch (Exception e){
            Log.e("Err",e.getMessage()+"");
        }
    }

    private void initRecycler(final RecyclerView recycler, final RecyclerView.Adapter adapter){
        final LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);
    }
}
