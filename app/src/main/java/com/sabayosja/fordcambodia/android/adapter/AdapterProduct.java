package com.sabayosja.fordcambodia.android.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayout;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.activity.ActivityProductDetail;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ItemHolder> {
    public final Context context;
    private final JSONArray arr;

    public AdapterProduct(Context context, JSONArray arr) {
        this.context = context;
        this.arr = arr;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_product_type,parent,false);
        return new ItemHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        try {
            final JSONObject object = arr.getJSONObject(position);
            holder.tv_category.setText(object.getString(Global.arData[15]));
            final String banner_url = object.getString(Global.arData[16]);
            if(banner_url.isEmpty()){
                holder.iv_banner.setVisibility(View.GONE);
            }else{
                Picasso.get().load(banner_url).placeholder(R.drawable.img_loading).error(R.drawable.img_loading).into(holder.iv_banner);
            }
            final JSONArray arrItem = object.getJSONArray(Global.arData[17]);
            for(int i = 0; i < arrItem.length();i++){
                JSONObject objCar = (JSONObject) arrItem.get(i);
                View view = LayoutInflater.from(context).inflate(R.layout.item_product, null, false);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                view.setLayoutParams(params);

                WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                int width = windowManager.getDefaultDisplay().getWidth();
                view.getLayoutParams().width = width / 3 - 17 ;
                getViewBook(view,objCar,i);

                holder.flb_layout.addView(view);
                Log.e("Index",""+i);
            }
        } catch (Exception e) {
            Log.e("Err",e.getMessage()+"");
        }
    }

    private void getViewBook(View view, final JSONObject objCar, int position){
        MyFont.getInstance().setFont(context,view,1);
        CardView card = view.findViewById(R.id.card);
        ImageView iv_car = view.findViewById(R.id.iv_car);
        final TextView tv_car = view.findViewById(R.id.tv_car);
        int height = MyFunction.getInstance().getHeight_250(context);
        card.getLayoutParams().height = height;
        try{
            String urlImg = objCar.getJSONObject(Global.arData[9]).getString(Global.arData[10]);
            Picasso.get().load(urlImg).error(R.drawable.img_loading).placeholder(R.drawable.img_loading).into(iv_car);
            tv_car.setText(objCar.getString(Global.arData[18]));
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        HashMap<String,String> map = new HashMap<>();
                        map.put(Global.arData[7],objCar.getString(Global.arData[7]));
                        MyFunction.getInstance().openActivity(context, ActivityProductDetail.class,map);
                        // MyFunction.getInstance().openActivity(ctx,ActivityVehicleDetail.class);
                    } catch (Exception e) {
                        Log.e("Err",e.getMessage()+"");
                    }
                }
            });
        }catch (Exception e){
            Log.e("Err",e.getMessage());
        }


    }

    @Override
    public int getItemCount() {
        return Math.max(arr.length(), 0);
    }

    static class ItemHolder extends RecyclerView.ViewHolder{
        final ImageView iv_banner;
        final TextView tv_category;
        final FlexboxLayout flb_layout;
        ItemHolder(@NonNull View itemView,Context context) {
            super(itemView);
            MyFont.getInstance().setFont(context,itemView,3);
            tv_category = itemView.findViewById(R.id.tv_category);
            iv_banner = itemView.findViewById(R.id.iv_banner);
            flb_layout = itemView.findViewById(R.id.flb_layout);

        }
    }
}
