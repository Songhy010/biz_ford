package com.sabayosja.fordcambodia.android.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;



public class AdapterBanner extends PagerAdapter {

    ViewGroup view;
    private Context mContext;
    private JSONArray array;

    public AdapterBanner(Context mContext, JSONArray array) {
        this.mContext = mContext;
        this.array = array;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return array.length();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        view = (ViewGroup) object;
        super.setPrimaryItem(container, position, object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        try{
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = (ViewGroup) inflater.inflate(R.layout.item_banner, container, false);
            MyFont.getInstance().setFont(mContext,view,1);
            final JSONObject objBanner = array.getJSONObject(position);
            final ImageView iv_banner = view.findViewById(R.id.iv_banner);
            final String img_url = objBanner.getJSONObject(Global.arData[9]).getString(Global.arData[10]);
            Picasso.get().load(img_url).placeholder(R.drawable.img_loading).error(R.drawable.img_loading).into(iv_banner);
            container.addView(view);
        }catch (Exception e){
            Log.e("Err",e.getMessage());
        }

        return view;
    }


}
