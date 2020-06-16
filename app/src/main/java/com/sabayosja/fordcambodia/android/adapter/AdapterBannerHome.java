package com.sabayosja.fordcambodia.android.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.activity.ActivityProductDetail;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class AdapterBannerHome extends PagerAdapter {

    ViewGroup view;
    private Context mContext;
    private JSONArray array;

    public AdapterBannerHome(Context mContext, JSONArray array) {
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
            iv_banner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        HashMap<String,String> map = new HashMap<>();
                        map.put(Global.arData[7],objBanner.getString(Global.arData[24]));
                        MyFunction.getInstance().openActivity(mContext, ActivityProductDetail.class,map);
                    } catch (Exception e) {
                        Log.e("Err",e.getMessage()+"");
                    }
                }
            });
            final String img_url = objBanner.getJSONObject(Global.arData[9]).getString(Global.arData[10]);
            Picasso.get().load(img_url).placeholder(R.drawable.img_loading).error(R.drawable.img_loading).into(iv_banner);
            container.addView(view);
        }catch (Exception e){
            Log.e("Err",e.getMessage()+"");
        }

        return view;
    }


}
