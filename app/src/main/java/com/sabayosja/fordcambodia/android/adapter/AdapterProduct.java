package com.sabayosja.fordcambodia.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sabayosja.fordcambodia.android.R;

import org.json.JSONArray;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ItemHolder> {
    private final Context context;
    private final JSONArray arr;

    public AdapterProduct(Context context, JSONArray arr) {
        this.context = context;
        this.arr = arr;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_product_type,parent,false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
