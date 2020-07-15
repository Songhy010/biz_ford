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
import com.sabayosja.fordcambodia.android.activity.ActivityAssistantDetail;
import com.sabayosja.fordcambodia.android.activity.ActivityRoadside;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class AdapterAssistant extends RecyclerView.Adapter<AdapterAssistant.ItemHolder> {
    final Context context;
    final JSONArray array;
    final ArrayList<String> title = new ArrayList();

    public AdapterAssistant(Context context, JSONArray array) {
        this.context = context;
        this.array = array;
        title.add(context.getString(R.string.ass_sale));
        title.add(context.getString(R.string.ass_service));
        title.add(context.getString(R.string.ass_roadside));
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_assistant,parent,false);
        return new ItemHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, final int position) {
        try {

            Random rnd = new Random();
            int color = Color.argb(150, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT,
                    new int[] {0x80303F9F,color});
            holder.gradient.setBackground(gd);

            final JSONObject object = array.getJSONObject(position);
            holder.tvTitle.setText(title.get(position));
            String urlImg = object.getJSONObject(Global.arData[105]).getString(Global.arData[10]);
            Picasso.get().load(urlImg).error(R.drawable.img_loading).placeholder(R.drawable.img_loading).into(holder.ivCar);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position != 2){//2 : 24h Roadside
                        final HashMap<String,String> map = new HashMap<>();
                        map.put(Global.arData[18],title.get(position));
                        map.put(Global.arData[12],object.toString());
                        MyFunction.getInstance().openActivity(context, ActivityAssistantDetail.class,map);
                    }else {
                        final HashMap<String,String> map = new HashMap<>();
                        map.put(Global.arData[18],title.get(position));
                        map.put(Global.arData[12],object.toString());
                        MyFunction.getInstance().openActivity(context, ActivityRoadside.class,map);
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
        private TextView tvTitle;
        private ImageView ivCar,gradient;
        public ItemHolder(@NonNull View itemView, Context context) {
            super(itemView);
            MyFont.getInstance().setFont(context, itemView, 1);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivCar = itemView.findViewById(R.id.ivCar);
            gradient = itemView.findViewById(R.id.gradient);
            cardView = itemView.findViewById(R.id.cardView);
            final int height = MyFunction.getInstance().getHeight_350(context);
            cardView.getLayoutParams().height = height;
        }
    }
}
