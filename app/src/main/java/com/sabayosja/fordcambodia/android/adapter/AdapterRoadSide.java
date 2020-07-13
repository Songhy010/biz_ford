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
import org.json.JSONObject;

public class AdapterRoadSide extends RecyclerView.Adapter<AdapterRoadSide.ItemHolder> {
    private Context context;
    private JSONArray array;

    public AdapterRoadSide(Context context, JSONArray array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_road_side, parent, false);
        return new ItemHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int position) {
        try{
            final JSONObject object = array.getJSONObject(position);
            holder.tvTitle.setText(object.getString(Global.arData[111]));
            holder.tvPhone.setText(object.getString(Global.arData[35]));
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        MyFunction.getInstance().initPhoneCall(context,object.getString(Global.arData[35]));
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
        private TextView tvPhone,tvTitle;
        private CardView card;
        public ItemHolder(@NonNull View itemView, Context context) {
            super(itemView);
            MyFont.getInstance().setFont(context,itemView,1);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            card = itemView.findViewById(R.id.card);
        }
    }
}
