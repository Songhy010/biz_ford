package com.sabayosja.fordcambodia.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.util.MyFont;

import java.util.ArrayList;

public class AdapterListIssue extends RecyclerView.Adapter<AdapterListIssue.ItemHolder> {
    private ArrayList<String> array;
    private Context context;

    public AdapterListIssue(ArrayList<String> array, Context context) {
        this.array = array;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_list_issue,parent,false);
        return new ItemHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.tab.setText(String.format("%s. %s",(position+1),array.get(position)));
    }

    @Override
    public int getItemCount() {
        return array != null? array.size() : 0;
    }

    static class ItemHolder extends RecyclerView.ViewHolder{
        private TextView tab;
        public ItemHolder(@NonNull View itemView,Context context) {
            super(itemView);
            MyFont.getInstance().setFont(context,itemView,1);
            tab = itemView.findViewById(R.id.tab);
        }
    }
}
