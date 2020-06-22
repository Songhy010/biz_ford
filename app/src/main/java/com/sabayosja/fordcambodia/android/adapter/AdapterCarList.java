package com.sabayosja.fordcambodia.android.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.activity.ActivitySelectService;
import com.sabayosja.fordcambodia.android.model.ModelBooking;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class AdapterCarList extends RecyclerView.Adapter<AdapterCarList.ItemHolder> {

    private JSONArray array;
    private Context context;

    public AdapterCarList(JSONArray array, Context context) {
        this.array = array;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_select_car, parent, false);
        return new ItemHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        try {
            final JSONObject object = array.getJSONObject(position);
            holder.tvModel.setText(object.getString(Global.arData[57]));
            holder.tvYear.setText(String.format("%s : %s",context.getString(R.string.all_year),object.getString(Global.arData[42])));
            holder.tvPlate.setText(String.format("%s : %s",context.getString(R.string.plate_number),object.getString(Global.arData[58])));
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        ModelBooking.getInstance().setCarID(object.getString(Global.arData[7]));
                        MyFunction.getInstance().openActivity(context, ActivitySelectService.class);
                    }catch (Exception e){
                        Log.e("Err",e.getMessage()+"");
                    }
                }
            });
            final String urlImage = object.getJSONObject(Global.arData[9]).getString(Global.arData[10]);
            Picasso.get().load(urlImage).error(R.drawable.img_loading).placeholder(R.drawable.img_loading).into(holder.ivCar);
        } catch (Exception e) {
            Log.e("Err",e.getMessage()+"");
        }
    }

    @Override
    public int getItemCount() {
        return Math.max(array.length(), 0);
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        private ImageView ivCar;
        private TextView tvModel, tvYear, tvPlate;
        private LinearLayout card;
        public ItemHolder(@NonNull View itemView, Context context) {
            super(itemView);
            MyFont.getInstance().setFont(context,itemView,1);
            ivCar = itemView.findViewById(R.id.ivCar);
            final int height = MyFunction.getInstance().getHeight_250(context);
            ivCar.getLayoutParams().height = height;
            tvModel = itemView.findViewById(R.id.tvModel);
            tvYear = itemView.findViewById(R.id.tvYear);
            tvPlate = itemView.findViewById(R.id.tvPlate);
            card = itemView.findViewById(R.id.card);
        }
    }
}
