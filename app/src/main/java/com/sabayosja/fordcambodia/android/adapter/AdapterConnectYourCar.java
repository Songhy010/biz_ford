package com.sabayosja.fordcambodia.android.adapter;

import android.content.Context;
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
import com.sabayosja.fordcambodia.android.activity.ActivityWebviewDetail;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class AdapterConnectYourCar extends RecyclerView.Adapter<AdapterConnectYourCar.ItemHolder> {
    final Context context;
    final JSONArray array;

    public AdapterConnectYourCar(Context context, JSONArray array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_connect_car,parent,false);
        return new ItemHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        try {
            final JSONObject obj = array.getJSONObject(position);
            holder.tvtitle.setText(obj.getString(Global.arData[18]));
            final String urlImg = obj.getJSONObject(Global.arData[9]).getString(Global.arData[10]);
            Picasso.get().load(urlImg).error(R.drawable.img_loading).placeholder(R.drawable.img_loading).into(holder.ivBasicImage);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        final HashMap<String,String> map = new HashMap<>();
                        map.put(Global.arData[18],obj.getString(Global.arData[18]));
                        map.put(Global.arData[7],obj.getString(Global.arData[7]));
                        MyFunction.getInstance().openActivity(context, ActivityWebviewDetail.class, map);
                    }catch (Exception e){
                        Log.e("Err",e.getMessage()+"");
                    }

                }
            });

        } catch (Exception e) {
            Log.e("Err",e.getMessage()+"");
        }
    }

    @Override
    public int getItemCount() {
        return array != null? array.length() : 0;
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView ivCar;
        private TextView tvtitle;
        private ImageView ivBasicImage;
        public ItemHolder(@NonNull View itemView, Context context) {
            super(itemView);
            MyFont.getInstance().setFont(context, itemView, 1);
            cardView = itemView.findViewById(R.id.cardView);
            ivCar = itemView.findViewById(R.id.ivCar);
            ivBasicImage = itemView.findViewById(R.id.ivCar);
            tvtitle = itemView.findViewById(R.id.tv_title);
            final int height = MyFunction.getInstance().getHeight_350(context);
            ivCar.getLayoutParams().height = height;
        }
    }
}
