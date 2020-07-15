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
import com.sabayosja.fordcambodia.android.activity.ActivityViewHistory;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AdapterVehicleHistory extends RecyclerView.Adapter<AdapterVehicleHistory.ItemHolder> {
    private Context context;
    private JSONArray array;

    public AdapterVehicleHistory(Context context, JSONArray array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_historyservice, parent, false);
        return new ItemHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        try {
            final JSONObject object = array.getJSONObject(position);
            holder.tvIssue.setText(object.getString(Global.arData[60]));
            MyFont.getInstance().setFont(context,holder.tvIssue,3);
            holder.tvMileage.setText(String.format("%s%skm",context.getString(R.string.mileage),object.getString(Global.arData[59])));
            holder.tvBooking.setText(String.format("%s: %s",context.getString(R.string.booking_id), object.getString(Global.arData[64])));
            final String date = object.getString(Global.arData[61]);
            final String branch = object.getString(Global.arData[63]);
            holder.tvDate.setText(String.format("%s %s %s",date, context.getString(R.string.at), branch));
            if (object.getString(Global.arData[79]).equals("0"))
                holder.tvStatus.setText(context.getString(R.string.status_canceled));
            else
                holder.tvStatus.setText(context.getString(R.string.status_accepted));

            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final HashMap<String,String> map = new HashMap<>();
                    map.put(Global.arData[12],object.toString());
                    MyFunction.getInstance().openActivity(context, ActivityViewHistory.class,map);
                }
            });
        } catch (JSONException e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    @Override
    public int getItemCount() {
        return array != null ? array.length() : 0;
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        private TextView tvIssue, tvMileage, tvDate, tvStatus, tvBooking;
        private CardView card;
        public ItemHolder(@NonNull View itemView, Context context) {
            super(itemView);
            MyFont.getInstance().setFont(context, itemView, 1);
            tvIssue = itemView.findViewById(R.id.tvIssue);
            tvMileage = itemView.findViewById(R.id.tvMileage);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvBooking = itemView.findViewById(R.id.tvBooking);
            card = itemView.findViewById(R.id.card);
        }
    }
}