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
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        final View view = LayoutInflater.from(context).inflate(R.layout.item_service_type,parent,false);
        return new ItemHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        try {
            final JSONObject object = array.getJSONObject(position);
            holder.tvName.setText(object.getString(Global.arData[18]));
        } catch (JSONException e) {
            Log.e("Err",e.getMessage()+"");
        }
    }

    @Override
    public int getItemCount() {
        return Math.max(array.length(),0);
    }

    static class ItemHolder extends RecyclerView.ViewHolder{
        private TextView tvName;
        private CardView card;
        public ItemHolder(@NonNull View itemView, Context context) {
            super(itemView);
            MyFont.getInstance().setFont(context,itemView,1);
            tvName = itemView.findViewById(R.id.tvName);
            card = itemView.findViewById(R.id.card);
            final int height = MyFunction.getInstance().getHeight_180(context);
            card.getLayoutParams().height = height;
        }
    }
}
