package com.sabayosja.fordcambodia.android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.activity.ActivityProductDetail;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFunction;

import org.json.JSONArray;
import org.json.JSONObject;

public class AdapterColor extends RecyclerView.Adapter<AdapterColor.ItemHolder> {

    private JSONArray array;
    private Context context;
    private int index = 0;

    public AdapterColor(JSONArray array, Context context) {
        this.array = array;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_color, parent, false);
        return new ItemHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int position) {
        try {
            final JSONObject object = array.getJSONObject(position);
            holder.img_color.setColorFilter(Color.parseColor(object.getString(Global.arData[31])));
            if (position == index) {
                holder.img_check.setVisibility(View.VISIBLE);
                if(object.getString(Global.arData[31]).equals("#FFFFFF") ||object.getString(Global.arData[31]).equals("#ffffff")){
                    holder.img_check.setColorFilter(Color.parseColor("#000000"));
                }
            }
            else
                holder.img_check.setVisibility(View.GONE);
            holder.img_color.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ((ActivityProductDetail) context).initColorlizer(object.getJSONObject(Global.arData[9]).getString(Global.arData[10]));
                        index = position;
                    } catch (Exception e) {
                        Log.e("Err", e.getMessage() + "");
                    }
                }
            });
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    @Override
    public int getItemCount() {
        return Math.max(array.length(), 0);
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        private ImageView img_color;
        private ImageView img_check;

        ItemHolder(@NonNull View itemView, Context context) {
            super(itemView);
            img_check = itemView.findViewById(R.id.img_check);
            img_color = itemView.findViewById(R.id.img_color);
        }
    }
}
