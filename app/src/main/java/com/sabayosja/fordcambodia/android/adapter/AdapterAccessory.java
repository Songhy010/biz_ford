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
import com.sabayosja.fordcambodia.android.activity.ActivityAccessoryDetail;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class AdapterAccessory extends RecyclerView.Adapter<AdapterAccessory.ItemHolder> {

    private JSONArray array;
    private Context context;

    public AdapterAccessory(JSONArray array, Context context) {
        this.array = array;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_accessory,parent,false);
        return new ItemHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        try {
            final JSONObject object = array.getJSONObject(position);
            holder.tvCode.setText(object.getString(Global.arData[44]));
            holder.tvPrice.setText(object.getString(Global.arData[45]));
            final String url_img = object.getJSONObject(Global.arData[9]).getString(Global.arData[10]);
            Picasso.get().load(url_img).placeholder(R.drawable.img_loading).error(R.drawable.img_loading).into(holder.ivAccessory);
            final String enName = object.getJSONObject(Global.arData[18]).getString(Global.EN);
            final String kmName = object.getJSONObject(Global.arData[18]).getString(Global.KM);
            holder.tvEnName.setText(enName);
            holder.tvKmName.setText(kmName);
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final HashMap<String,String> map = new HashMap<>();
                    map.put(Global.arData[12],object.toString());
                    map.put(Global.arData[18],enName);
                    MyFunction.getInstance().openActivity(context, ActivityAccessoryDetail.class,map);
                }
            });
        }catch (Exception e){
            Log.e("Err",e.getMessage()+"");
        }
    }

    @Override
    public int getItemCount() {
        return Math.max(array.length(),0);
    }

    static class ItemHolder extends RecyclerView.ViewHolder{
        private CardView card;
        private TextView tvEnName;
        private TextView tvKmName;
        private TextView tvCode;
        private TextView tvPrice;
        private ImageView ivAccessory;
        public ItemHolder(@NonNull View itemView, Context context) {
            super(itemView);
            MyFont.getInstance().setFont(context,itemView,1);
            card = itemView.findViewById(R.id.card);
            tvEnName = itemView.findViewById(R.id.tvEnName);
            tvKmName = itemView.findViewById(R.id.tvKmName);
            tvCode = itemView.findViewById(R.id.tvCode);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            ivAccessory = itemView.findViewById(R.id.ivAccessory);
            int height = MyFunction.getInstance().getHeight_450(context);
            card.getLayoutParams().height = height;
        }
    }
}
