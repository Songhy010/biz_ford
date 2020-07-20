package com.sabayosja.fordcambodia.android.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.activity.ActivityLocation;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdapterCollapsedList extends RecyclerView.Adapter<AdapterCollapsedList.ItemHolder> {
    private Context context;
    private JSONArray array;

    public AdapterCollapsedList(Context context, JSONArray array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_collapsed_list, parent, false);
        return new ItemHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int position) {
        try{
            final JSONObject object = array.getJSONObject(position);
            holder.tvPlace.setText(object.getString(Global.arData[35]));
            holder.tvAddress.setText(object.getString(Global.arData[123]));
            holder.linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        final JSONObject obj = object.getJSONObject(Global.arData[122]);
                        final double lat = Double.parseDouble(obj.getString(Global.arData[124]));
                        final double lng = Double.parseDouble(obj.getString(Global.arData[125]));
                        ((ActivityLocation)context).moveCamera(new LatLng(lat,lng));
                    } catch (Exception e) {
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
        private TextView tvPlace,tvAddress;
        private RelativeLayout linear;
        public ItemHolder(@NonNull View itemView, Context context) {
            super(itemView);
            MyFont.getInstance().setFont(context,itemView,1);
            tvPlace = itemView.findViewById(R.id.tvPlace);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            linear = itemView.findViewById(R.id.linear);
        }
    }
}
