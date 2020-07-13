package com.sabayosja.fordcambodia.android.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;

import org.json.JSONArray;
import org.json.JSONObject;

public class AdapterAssistantDetail extends RecyclerView.Adapter<AdapterAssistantDetail.ItemHolder> {
    private Context context;
    private JSONArray array;

    public AdapterAssistantDetail(Context context, JSONArray array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_assistant_detail, parent, false);
        return new ItemHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        try{
            final JSONObject object = array.getJSONObject(position);
            holder.tvTitle.setText(object.getString(Global.arData[111]));
            initRecycler(object.getJSONArray(Global.arData[35]),holder.recyclePhone);
        }catch (Exception e){
            Log.e("Err",e.getMessage()+"");
        }
    }

    @Override
    public int getItemCount() {
        return array != null ? array.length() : 0;
    }

    private void initRecycler(JSONArray array,final RecyclerView recycler) {
        final LinearLayoutManager manager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        final AdapterPhone adapter = new AdapterPhone(context, array);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private RecyclerView recyclePhone;
        public ItemHolder(@NonNull View itemView, Context context) {
            super(itemView);
            MyFont.getInstance().setFont(context,itemView,1);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            recyclePhone = itemView.findViewById(R.id.recyclePhone);
        }
    }
}
