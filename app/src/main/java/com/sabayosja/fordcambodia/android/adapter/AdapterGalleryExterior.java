package com.sabayosja.fordcambodia.android.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class AdapterGalleryExterior extends RecyclerView.Adapter<AdapterGalleryExterior.ItemHolder> {

    private final Context context;
    private final JSONArray array;

    public AdapterGalleryExterior(Context context, JSONArray array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_gallery,parent,false);
        return new ItemHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        try{
            final JSONObject object = array.getJSONObject(position);
            final String url_img = object.getJSONObject(Global.arData[9]).getString(Global.arData[10]);
            Picasso.get().load(url_img).placeholder(R.drawable.img_loading).error(R.drawable.img_loading).into(holder.iv_banner);
        }catch (Exception e){
            Log.e("Err",e.getMessage()+"");
        }

    }

    @Override
    public int getItemCount() {
        return Math.max(array.length(), 0);
    }

    static class ItemHolder extends RecyclerView.ViewHolder{
        final ImageView iv_banner;
        ItemHolder(@NonNull View itemView, final Context context) {
            super(itemView);
            iv_banner = itemView.findViewById(R.id.iv_banner);
            iv_banner.getLayoutParams().height = MyFunction.getInstance().getHeight_300(context);
        }
    }
}
