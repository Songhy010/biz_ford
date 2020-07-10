package com.sabayosja.fordcambodia.android.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.activity.ActivityWebviewDetail;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.ItemHolder> {

    private Context context;
    private JSONArray array;

    public AdapterNotification(Context context, JSONArray array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
        return new ItemHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        try{
            final JSONObject object = array.getJSONObject(position);
            holder.tvTitle.setText(object.getString(Global.arData[18]));
            MyFont.getInstance().setFont(context,holder.tvTitle,3);
            holder.tvDesc.setText(object.getString(Global.arData[100]));
            holder.tvDate.setText(object.getString(Global.arData[68]));
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        if(object.getString(Global.arData[26]).equals(Global.SUCCESS)){
                            final HashMap<String,String> map = new HashMap<>();
                            map.put(Global.arData[18],object.getString(Global.arData[18]));
                            map.put(Global.arData[7],object.getString(Global.arData[7]));
                            MyFunction.getInstance().openActivity(context, ActivityWebviewDetail.class, map);
                        }else {
                            Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
                        }
                        
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
        return array != null ? array.length() : 0;
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        private CardView card;
        private TextView tvTitle,tvDesc,tvDate;

        public ItemHolder(@NonNull View itemView, Context context) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvDate = itemView.findViewById(R.id.tvDate);
            final int height = MyFunction.getInstance().getHeight_250(context);
            card.getLayoutParams().height = height;
            MyFont.getInstance().setFont(context,itemView,1);
        }
    }
}
