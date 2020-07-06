package com.sabayosja.fordcambodia.android.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.activity.ActivitySelectMileage;
import com.sabayosja.fordcambodia.android.activity.ActivitySelectStation;
import com.sabayosja.fordcambodia.android.model.ModelBooking;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;

import org.json.JSONArray;
import org.json.JSONObject;

public class AdapterMileage extends RecyclerView.Adapter<AdapterMileage.ItemHolder> {

    private Context context;
    private JSONArray array;

    public AdapterMileage(Context context, JSONArray array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_mileage,parent,false);
        return new ItemHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        try{
            final JSONObject object = array.getJSONObject(position);
            if (object.getString(Global.arData[76]).equals("0")){// Other mileage
                holder.tvMileage.setText(String.format("%s",object.getString(Global.arData[74])));
            }else {
                holder.tvMileage.setText(String.format("%skm - %skm(%sh)",object.getString(Global.arData[73]),object.getString(Global.arData[74]),object.getString(Global.arData[75])));
            }

            holder.linearMileage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        if (object.getString(Global.arData[76]).equals("0"))// Other mileage
                            ((ActivitySelectMileage)context).showOtherDialog(object.getString(Global.arData[73]));
                        else
                            MyFunction.getInstance().openActivity(context, ActivitySelectStation.class);

                        ModelBooking.getInstance().setMileageID(object.getString(Global.arData[76]));
                        ModelBooking.getInstance().setDuration(object.getString(Global.arData[75]));
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
        private TextView tvMileage;
        private RelativeLayout linearMileage;
        public ItemHolder(@NonNull View itemView, Context context) {
            super(itemView);
            MyFont.getInstance().setFont(context,itemView,1);
            linearMileage = itemView.findViewById(R.id.linearMileage);
            tvMileage = itemView.findViewById(R.id.tvMileage);
        }
    }
}
