package com.sabayosja.fordcambodia.android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.activity.ActivityAccessory;
import com.sabayosja.fordcambodia.android.activity.ActivityMaintenanceTip;
import com.sabayosja.fordcambodia.android.activity.ActivityWebviewDetail;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

public class AdapterService extends RecyclerView.Adapter<AdapterService.ItemHolder> {

    private JSONArray array;
    private Context context;

    public AdapterService(JSONArray array, Context context) {
        this.array = array;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_service, parent, false);
        return new ItemHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        try {
            Random rnd = new Random();
            int color = Color.argb(150, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT,
                    new int[] {0x80303F9F,color});
            holder.gradient.setBackground(gd);

            final JSONObject object = array.getJSONObject(position);
            holder.tv_service.setText(object.getString(Global.arData[18]));
            final String url = object.getJSONObject(Global.arData[9]).getString(Global.arData[10]);
            Picasso.get().load(url).error(R.drawable.img_loading).error(R.drawable.img_loading).into(holder.iv_service);

            holder.gradient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (object.getString(Global.arData[26]).equals("1")) {// 1 webview ,0 interface
                            final HashMap<String,String> map = new HashMap<>();
                            map.put(Global.arData[18],object.getString(Global.arData[18]));
                            map.put(Global.arData[7],object.getString(Global.arData[7]));
                            MyFunction.getInstance().openActivity(context, ActivityWebviewDetail.class,map);
                        }else {
                            switch (object.getString(Global.arData[7])){
                                case "134": //Maintenance
                                    MyFunction.getInstance().openActivity(context, ActivityMaintenanceTip.class);
                                    break;
                                case "139":
                                    MyFunction.getInstance().openActivity(context, ActivityAccessory.class);
                                    break;
                            }
                        }
                    } catch (JSONException e) {
                        Log.e("Err",e.getMessage()+"");
                    }
                }
            });

        } catch (Exception e) {
            Log.e("Err", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return Math.max(array.length(), 0);
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView iv_service, gradient;
        private TextView tv_service;

        ItemHolder(@NonNull View itemView, Context context) {
            super(itemView);
            MyFont.getInstance().setFont(context, itemView, 3);
            iv_service = itemView.findViewById(R.id.iv_service);
            cardView = itemView.findViewById(R.id.card);
            gradient = itemView.findViewById(R.id.gradient);
            tv_service = itemView.findViewById(R.id.tv_service);

            final int height = MyFunction.getInstance().getHeight_350(context);
            cardView.getLayoutParams().height = height;
        }
    }
}
