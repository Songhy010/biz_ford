package com.sabayosja.fordcambodia.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;

import org.json.JSONArray;
import org.json.JSONObject;

public class AdapterTerm extends RecyclerView.Adapter<AdapterTerm.ItemHolder> {
    private Context context;
    private JSONArray array;

    public AdapterTerm(Context context, JSONArray array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_term, parent, false);
        return new ItemHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int position) {
        try{
            final JSONObject object = array.getJSONObject(position);
            holder.tvTerm.setText(object.getString(Global.arData[25]));
        }catch (Exception e){
            Log.e("Err",e.getMessage()+"");
        }
    }

    @Override
    public int getItemCount() {
        return array != null ? array.length() : 0;
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        private TextView tvTerm;
        public ItemHolder(@NonNull View itemView, Context context) {
            super(itemView);
            MyFont.getInstance().setFont(context,itemView,1);
            tvTerm = itemView.findViewById(R.id.tvTerm);
        }
    }
}
