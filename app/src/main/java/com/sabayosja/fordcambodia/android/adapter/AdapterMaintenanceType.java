package com.sabayosja.fordcambodia.android.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdapterMaintenanceType extends RecyclerView.Adapter<AdapterMaintenanceType.ItemHolder> {
    private JSONArray array;
    private Context context;

    public AdapterMaintenanceType(JSONArray array, Context context) {
        this.array = array;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_maintenance_type, parent, false);
        return new ItemHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        try {
            final JSONObject object = array.getJSONObject(position);
            holder.tv_type.setText(object.getString(Global.arData[15]));
            final LinearLayoutManager manager = new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
            final AdapterMaintenance adapterMaintenance = new AdapterMaintenance(object.optJSONArray("maintenance"),context);
            holder.recycleMaintenance.setLayoutManager(manager);
            holder.recycleMaintenance.setAdapter(adapterMaintenance);
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }

    }

    @Override
    public int getItemCount() {
        return Math.max(array.length(), 0);
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        private TextView tv_type;
        private RecyclerView recycleMaintenance;
        ItemHolder(@NonNull View itemView, Context context) {
            super(itemView);
            MyFont.getInstance().setFont(context, itemView, 3);
            tv_type = itemView.findViewById(R.id.tv_type);
            recycleMaintenance = itemView.findViewById(R.id.recycleMaintenance);
        }
    }
}
