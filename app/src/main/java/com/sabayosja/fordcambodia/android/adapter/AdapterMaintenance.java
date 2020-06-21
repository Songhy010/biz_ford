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

public class AdapterMaintenance extends RecyclerView.Adapter<AdapterMaintenance.ItemHolder> {

    private JSONArray array;
    private Context context;

    public AdapterMaintenance(JSONArray array, Context context) {
        this.array = array;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_maintenance,parent,false);
        return new ItemHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        try {
            final JSONObject object = array.getJSONObject(position);
            holder.tv_name.setText(object.getString(Global.arData[18]));
            final String url_img = object.getJSONObject(Global.arData[9]).getString(Global.arData[10]);
            Picasso.get().load(url_img).error(R.drawable.img_loading).placeholder(R.drawable.img_loading).into(holder.iv_maintenance);
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
        }catch (Exception e){
            Log.e("Err",e.getMessage()+"");
        }
    }

    @Override
    public int getItemCount() {
        return Math.max(array.length(),0);
    }


    static class ItemHolder extends RecyclerView.ViewHolder{
        private TextView tv_name;
        private ImageView iv_maintenance;
        private CardView card;
        public ItemHolder(@NonNull View itemView, Context context) {
            super(itemView);
            MyFont.getInstance().setFont(context,itemView,1);
            tv_name = itemView.findViewById(R.id.tv_name);
            iv_maintenance = itemView.findViewById(R.id.iv_maintenance);
            card = itemView.findViewById(R.id.card);
            final int height = MyFunction.getInstance().getHeight_450(context);
            card.getLayoutParams().height = height;
        }
    }
}
