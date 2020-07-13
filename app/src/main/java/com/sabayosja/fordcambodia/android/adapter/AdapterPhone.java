package com.sabayosja.fordcambodia.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;

import org.json.JSONArray;
import org.json.JSONObject;

public class AdapterPhone extends RecyclerView.Adapter<AdapterPhone.ItemHolder> {
    private Context context;
    private JSONArray array;

    public AdapterPhone(Context context, JSONArray array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_phone, parent, false);
        return new ItemHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int position) {
        try{
            holder.tvPhone.setText(array.getString(position));
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse(String.format("%s:%s", "tel",array.getString(position))));
                        context.startActivity(intent);
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
        private TextView tvPhone;
        private CardView card;
        public ItemHolder(@NonNull View itemView, Context context) {
            super(itemView);
            MyFont.getInstance().setFont(context,itemView,1);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            card = itemView.findViewById(R.id.card);
        }
    }
}
