package com.sabayosja.fordcambodia.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.listener.VolleyCallback;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;

public class ActivityProduct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        initView();
    }

    private void initView() {
        loadProduct();
    }

    private void initRecycle(){
        final LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        final RecyclerView recycler = findViewById(R.id.recycle);

    }

    private void loadProduct(){
        final String lang  = MyFunction.getInstance().getText(ActivityProduct.this, Global.arData[6]);
        final String url = Global.arData[0]+Global.arData[1]+Global.arData[5];
        final HashMap<String,String> param = new HashMap<>();
        param.put(Global.arData[6],lang);
        param.put(Global.arData[7],Global.arData[14]);
        MyFunction.getInstance().requestString(this, Request.Method.POST, url, param, new VolleyCallback() {
            @Override
            public void onResponse(String response) {
                try {
                    if(MyFunction.getInstance().isValidJSON(response)){
                        Log.e("response",response);
                    }
                }catch (Exception e){
                    Log.e("Err",e.getMessage()+"");
                }
            }

            @Override
            public void onErrorResponse(VolleyError e) {
                Log.e("Err",e.getMessage()+"");
                loadProduct();
            }
        });
    }
}