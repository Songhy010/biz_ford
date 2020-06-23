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
import com.sabayosja.fordcambodia.android.activity.ActivitySelectIssue;
import com.sabayosja.fordcambodia.android.activity.ActivitySelectMileage;
import com.sabayosja.fordcambodia.android.model.ModelBooking;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdapterServiceType extends RecyclerView.Adapter<AdapterServiceType.ItemHolder> {

    private JSONArray array;
    private Context context;

    public AdapterServiceType(JSONArray array, Context context) {
        this.array = array;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_service_type, parent, false);
        return new ItemHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, final int position) {
        try {
            final JSONObject object = array.getJSONObject(position);
            holder.tvName.setText(object.getString(Global.arData[18]));
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        if (position == 0) {// Maintenance
                            MyFunction.getInstance().openActivity(context, ActivitySelectMileage.class);
                            ModelBooking.getInstance().setArrRepairID(new ArrayList<String>());
                        }
                        else if(position == 1) {// Repair
                            MyFunction.getInstance().openActivity(context, ActivitySelectIssue.class);
                            ModelBooking.getInstance().setMileageID("");
                        }
                        else { //Maintenance and Repair
                            MyFunction.getInstance().openActivity(context, ActivitySelectIssue.class);
                        }
                        ModelBooking.getInstance().setServiceTypeID(object.getString(Global.arData[7]));
                    }catch (Exception e){
                        Log.e("Err",e.getMessage()+"");
                    }
                }
            });
        } catch (JSONException e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    @Override
    public int getItemCount() {
        return Math.max(array.length(), 0);
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private CardView card;

        public ItemHolder(@NonNull View itemView, Context context) {
            super(itemView);
            MyFont.getInstance().setFont(context, itemView, 1);
            tvName = itemView.findViewById(R.id.tvName);
            card = itemView.findViewById(R.id.card);
            final int height = MyFunction.getInstance().getHeight_180(context);
            card.getLayoutParams().height = height;
        }
    }
}
