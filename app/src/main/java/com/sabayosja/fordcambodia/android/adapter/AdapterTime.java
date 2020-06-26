package com.sabayosja.fordcambodia.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.util.MyFunction;

import org.json.JSONArray;

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
        final View view = LayoutInflater.from(context).inflate(R.layout.item_time,parent,false);
        return new ItemHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 13;
    }

    static class ItemHolder extends RecyclerView.ViewHolder{
        private CardView card;
        public ItemHolder(@NonNull View itemView, Context context) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            card.getLayoutParams().width = MyFunction.getInstance().getHeight_170(context);
        }
    }
}
