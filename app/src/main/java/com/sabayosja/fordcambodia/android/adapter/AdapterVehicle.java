package com.sabayosja.fordcambodia.android.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sabayosja.fordcambodia.android.R;

import com.sabayosja.fordcambodia.android.activity.ActivityAddVehicle;
import com.sabayosja.fordcambodia.android.activity.ActivityVehicle;
import com.sabayosja.fordcambodia.android.listener.SelectedListener;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class AdapterVehicle extends RecyclerView.Adapter<AdapterVehicle.ItemHolder> {
    private Context context;
    private JSONArray array;

    public AdapterVehicle(Context context, JSONArray array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_vehicle, parent, false);
        return new ItemHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int position) {
        try {
            final JSONObject object = array.getJSONObject(position);
            holder.tvModel.setText(object.getString(Global.arData[57]));
            holder.tvYear.setText(String.format("%s: %s", context.getString(R.string.all_year), object.getString(Global.arData[42])));
            holder.tvPlate.setText(String.format("%s: %s", context.getString(R.string.plate_number), object.getString(Global.arData[58])));
            String[] item = new String[]{context.getString(R.string.delete), context.getString(R.string.edit)};
            MyFunction.getInstance().initSelectItem(context, holder.iv_more, holder.tvNotShow, item, 1, new SelectedListener() {
                @Override
                public void onSelected(int str) {
                    if (str == 0)
                        ((ActivityVehicle) context).editAndDelete(object);
                    else {
                        final HashMap<String, String> map = new HashMap<>();
                        map.put(Global.arData[12],object.toString());
                        MyFunction.getInstance().openActivity(context, ActivityAddVehicle.class,map);
                    }
                }
            });
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        ((ActivityVehicle) context).loadVehicleHistory(object.getString(Global.arData[7]), object);
                    } catch (Exception e) {
                        Log.e("Err", e.getMessage() + "");
                    }
                }
            });
            final String urlImage = object.getJSONObject(Global.arData[9]).getString(Global.arData[10]);
            Picasso.get().load(urlImage).error(R.drawable.img_loading).placeholder(R.drawable.img_loading).into(holder.ivCar);
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    @Override
    public int getItemCount() {
        return array != null ? array.length() : 0;
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        private ImageView iv_more;
        private CardView cardView;
        private ImageView ivCar;
        private TextView tvModel, tvYear, tvPlate, tvNotShow;

        public ItemHolder(@NonNull View itemView, Context context) {
            super(itemView);
            MyFont.getInstance().setFont(context, itemView, 1);
            iv_more = itemView.findViewById(R.id.iv_more);
            cardView = itemView.findViewById(R.id.cardcar);
            ivCar = itemView.findViewById(R.id.ivCar);
            final int height = MyFunction.getInstance().getHeight_250(context);
            ivCar.getLayoutParams().height = height;
            tvModel = itemView.findViewById(R.id.tvModel);
            tvYear = itemView.findViewById(R.id.tvYear);
            tvPlate = itemView.findViewById(R.id.tvPlate);
            tvNotShow = itemView.findViewById(R.id.tvNotShow);
        }
    }
}
