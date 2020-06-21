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
import com.sabayosja.fordcambodia.android.activity.ActivityYourBooking;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class AdapterYourBooking extends RecyclerView.Adapter<AdapterYourBooking.ItemHolder> {

    private JSONArray array;
    private Context context;

    public AdapterYourBooking(JSONArray array, Context context) {
        this.array = array;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_booking,parent,false);
        return new ItemHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        try {
            final JSONObject object = array.getJSONObject(position);
            holder.tvName.setText(object.getString(Global.arData[56]));
            holder.tvPhone.setText(MyFunction.getInstance().getText(context,Global.INFO_FILE));
            holder.tvModel.setText(object.getString(Global.arData[57]));
            holder.tvYear.setText(object.getString(Global.arData[42]));
            holder.tvPlate.setText(object.getString(Global.arData[58]));
            holder.tvMileage.setText(object.getString(Global.arData[59]));
            holder.tvIssues.setText(object.getString(Global.arData[60]));
            holder.tvDate.setText(object.getString(Global.arData[61]));
            holder.tvTime.setText(object.getString(Global.arData[62]));
            holder.tvLocation.setText(object.getString(Global.arData[63]));
            holder.tvBookId.setText(object.getString(Global.arData[64]));
            holder.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        final HashMap<String,String> param = new HashMap<>();
                        param.put(Global.arData[51],MyFunction.getInstance().getText(context,Global.INFO_FILE));
                        param.put(Global.arData[7],object.getString(Global.arData[7]));
                        param.put(Global.arData[63],object.getString(Global.arData[67]));
                        param.put(Global.arData[66],object.getString(Global.arData[62]));
                        param.put(Global.arData[68],object.getString(Global.arData[61]));
                        ((ActivityYourBooking)context).initCancel(param);
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
        return Math.max(array.length(),0);
    }

    static class ItemHolder extends RecyclerView.ViewHolder{
        private TextView tvName;
        private TextView tvPhone;
        private TextView tvModel;
        private TextView tvYear;
        private TextView tvPlate;
        private TextView tvMileage;
        private TextView tvIssues;
        private TextView tvDate;
        private TextView tvTime;
        private TextView tvLocation;
        private TextView tvBookId;
        private CardView btnCancel;
        public ItemHolder(@NonNull View itemView, Context context) {
            super(itemView);
            MyFont.getInstance().setFont(context,itemView,1);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvModel = itemView.findViewById(R.id.tvModel);
            tvYear = itemView.findViewById(R.id.tvYear);
            tvPlate = itemView.findViewById(R.id.tvPlate);
            tvMileage = itemView.findViewById(R.id.tvMileage);
            tvIssues = itemView.findViewById(R.id.tvIssues);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvBookId = itemView.findViewById(R.id.tvBookId);
            btnCancel = itemView.findViewById(R.id.btnCancel);
        }
    }
}
