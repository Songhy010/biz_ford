package com.sabayosja.fordcambodia.android.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.activity.ActivitySelectDate;
import com.sabayosja.fordcambodia.android.model.ModelBooking;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;

import org.json.JSONArray;
import org.json.JSONObject;

public class AdapterStation extends RecyclerView.Adapter<AdapterStation.ItemHolder> {

    private Context context;
    private JSONArray array;

    public AdapterStation(Context context, JSONArray array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_station,parent,false);
        return new ItemHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        try{
            final JSONObject object = array.getJSONObject(position);
            holder.tvStation.setText(object.getString(Global.arData[35]));
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        ModelBooking.getInstance().setStationID(object.getString(Global.arData[7]));
                        ModelBooking.getInstance().setStation(object.getString(Global.arData[35]));
                        MyFunction.getInstance().openActivity(context, ActivitySelectDate.class);
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
        private TextView tvStation;
        private CardView card;
        public ItemHolder(@NonNull View itemView, Context context) {
            super(itemView);
            MyFont.getInstance().setFont(context,itemView,1);
            tvStation = itemView.findViewById(R.id.tvStation);
            final int height = MyFunction.getInstance().getHeight_180(context);
            card = itemView.findViewById(R.id.card);
            card.getLayoutParams().height = height;
        }
    }
}
