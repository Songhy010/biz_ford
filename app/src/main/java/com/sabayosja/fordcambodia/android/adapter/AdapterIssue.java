package com.sabayosja.fordcambodia.android.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.model.ModelBooking;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdapterIssue extends RecyclerView.Adapter<AdapterIssue.ItemHolder> {

    private JSONArray array;
    private Context context;
    private ArrayList<String> arrIdCheck = new ArrayList<>();
    final ArrayList<String> arrRepairID = new ArrayList<>();
    final ArrayList<String> arrRepairName = new ArrayList<>();

    public AdapterIssue(JSONArray array, Context context) {
        this.array = array;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_issue,parent,false);
        return new ItemHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int position) {
        try{
            final JSONObject object = array.getJSONObject(position);
            holder.tvIssues.setText(object.getString(Global.arData[35]));
            holder.relativeIssue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        boolean setCheck = true;
                        for (int i = 0 ; i<arrIdCheck.size();i++) {
                            if (object.getString(Global.arData[7]).equals(arrIdCheck.get(i))) {
                                arrRepairID.remove(i);
                                arrRepairName.remove(i);
                                arrIdCheck.remove(i);
                                holder.ivChecked.setVisibility(View.GONE);
                                setCheck = false;
                            }
                        }
                        if(setCheck){
                            arrRepairName.add(object.getString(Global.arData[35]));
                            arrRepairID.add(object.getString(Global.arData[7]));
                            arrIdCheck.add(object.getString(Global.arData[7]));
                            holder.ivChecked.setVisibility(View.VISIBLE);
                        }
                        ModelBooking.getInstance().setArrRepairID(arrRepairID);
                        ModelBooking.getInstance().setArrRepairName(arrRepairName);
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

    static class ItemHolder extends  RecyclerView.ViewHolder{
        private RelativeLayout relativeIssue;
        private ImageView ivChecked;
        private TextView tvIssues;
        public ItemHolder(@NonNull View itemView,Context context) {
            super(itemView);
            MyFont.getInstance().setFont(context,itemView,1);
            relativeIssue = itemView.findViewById(R.id.relativeIssue);
            ivChecked = itemView.findViewById(R.id.ivChecked);
            tvIssues = itemView.findViewById(R.id.tvIssues);
        }
    }
}
