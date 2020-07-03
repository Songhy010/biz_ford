package com.sabayosja.fordcambodia.android.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.activity.ActivitySelectTime;
import com.sabayosja.fordcambodia.android.activity.ActivityViewBooking;
import com.sabayosja.fordcambodia.android.model.ModelBooking;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;

import org.json.JSONArray;
import org.json.JSONObject;

public class AdapterTime extends RecyclerView.Adapter<AdapterTime.ItemHolder> {

    private Context context;
    private JSONArray array;

    public AdapterTime(Context context, JSONArray array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_time, parent, false);
        return new ItemHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        try {
            final JSONObject object = array.getJSONObject(position);
            holder.tvTime.setText(String.format("%s%s", object.getString(Global.arData[66]), object.getString(Global.arData[80])));
            final int status = Integer.parseInt(object.getString(Global.arData[79]));
            if(status != 1){
                holder.linearTime.setBackgroundColor(context.getResources().getColor(R.color.grey));
            }
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        ModelBooking.getInstance().setTime(String.format("%s%s", object.getString(Global.arData[66]), object.getString(Global.arData[80])));
                        ((ActivitySelectTime)context).initAddTempBook();
                    }catch (Exception e){
                        Log.e("Err",e.getMessage()+"");
                    }
                }
            });
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    @Override
    public int getItemCount() {
        return array != null ? array.length() : 0;
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        private CardView card;
        private LinearLayout linearTime;
        private TextView tvTime;

        public ItemHolder(@NonNull View itemView, Context context) {
            super(itemView);
            MyFont.getInstance().setFont(context, itemView, 1);
            card = itemView.findViewById(R.id.card);
            card.getLayoutParams().width = MyFunction.getInstance().getHeight_170(context);
            tvTime = itemView.findViewById(R.id.tvTime);
            linearTime = itemView.findViewById(R.id.linearTime);
        }
    }
}
