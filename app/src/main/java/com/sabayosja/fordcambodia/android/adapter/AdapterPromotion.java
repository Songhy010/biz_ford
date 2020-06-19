package com.sabayosja.fordcambodia.android.adapter;

import android.content.Context;
import android.graphics.fonts.Font;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AdapterPromotion extends RecyclerView.Adapter<AdapterPromotion.ItemHolder> {
    private JSONArray array;
    private Context context;

    public AdapterPromotion(JSONArray array, Context context) {
        this.array = array;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_promotion,parent,false);
        return new ItemHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        try {
            final JSONObject object = array.getJSONObject(position);
            holder.tvPromotion.setText(object.getString(Global.arData[18]));
            final String url_img = object.getJSONObject(Global.arData[9]).getString(Global.arData[10]);
            Picasso.get().load(url_img).error(R.drawable.img_loading).placeholder(R.drawable.img_loading).into(holder.ivPromotion);
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        final HashMap<String,String> map = new HashMap<>();
                        map.put(Global.arData[18],object.getString(Global.arData[18]));
                        map.put(Global.arData[7],object.getString(Global.arData[7]));
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
        return Math.max(array.length(),0);
    }

    static class ItemHolder extends RecyclerView.ViewHolder{
        private CardView card;
        private ImageView ivPromotion;
        private TextView tvPromotion;
        ItemHolder(@NonNull View itemView, Context context) {
            super(itemView);
            MyFont.getInstance().setFont(context,itemView,1);
            card = itemView.findViewById(R.id.card);
            ivPromotion = itemView.findViewById(R.id.ivPromotion);
            tvPromotion = itemView.findViewById(R.id.tvPromotion);
            final int height = MyFunction.getInstance().getBannerHeightTab(context);
            card.getLayoutParams().height = height;
        }
    }
}
