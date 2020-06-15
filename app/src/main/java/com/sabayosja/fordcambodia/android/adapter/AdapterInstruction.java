package com.sabayosja.fordcambodia.android.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.util.MyFont;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;


public class AdapterInstruction extends PagerAdapter {

    ViewGroup view;
    private Context mContext;
    private JSONArray array;

    public AdapterInstruction(Context mContext, JSONArray array) {
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
            view = (ViewGroup) inflater.inflate(R.layout.item_instuction, container, false);
            MyFont.getInstance().setFont(mContext,view,1);
            final ImageView iv_instruction = view.findViewById(R.id.iv_instruction);
            final JSONObject obj = array.getJSONObject(position);
            final TextView tv_title = view.findViewById(R.id.tv_title);
            tv_title.setText(obj.getString("title"));
            final int id = obj.getInt("id");
            switch (id){//check index for change image
                case 1:
                    iv_instruction.setImageDrawable(mContext.getResources().getDrawable(R.drawable.img_instru_one));
                    break;
                case 2:
                    iv_instruction.setImageDrawable(mContext.getResources().getDrawable(R.drawable.img_instru_two));
                    break;
                case 3:
                    iv_instruction.setImageDrawable(mContext.getResources().getDrawable(R.drawable.img_instru_three));
                    break;
            }
            container.addView(view);
        }catch (Exception e){
            Log.e("Err",e.getMessage()+"");
        }
        return view;
    }


}
