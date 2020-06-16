package com.sabayosja.fordcambodia.android.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.activity.ActivityFullScreen;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class AdapterFeature extends RecyclerView.Adapter<AdapterFeature.ItemHolder> {

    private final Context context;
    private final JSONArray array;

    public AdapterFeature(Context context, JSONArray array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_feature,parent,false);
        return new ItemHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, int position) {
        try{
            final JSONObject object = array.getJSONObject(position);
            holder.tv_title.setText(object.getString(Global.arData[25]));
            final String url = object.getString(Global.arData[9]);
            if(object.getString(Global.arData[26]).equals("1")){//1 video, 0 image
                holder.iv_img.setVisibility(View.GONE);
                holder.linear_video.setVisibility(View.VISIBLE);
                ((ComponentActivity)context).getLifecycle().addObserver(holder.youTubeView);
                holder.youTubeView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull final YouTubePlayer youTubePlayer) {
                        youTubePlayer.loadVideo(url, 0);
                        youTubePlayer.pause();
                    }
                });
                holder.youTubeView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
                    @Override
                    public void onYouTubePlayerEnterFullScreen() {
                        final HashMap<String,String> map = new HashMap<>();
                        map.put(Global.arData[24],url);
                        MyFunction.getInstance().openActivity(context, ActivityFullScreen.class,map);
                        holder.youTubeView.toggleFullScreen();
                    }

                    @Override
                    public void onYouTubePlayerExitFullScreen() {
                    }
                });
            }else {
                holder.iv_img.setVisibility(View.VISIBLE);
                holder.linear_video.setVisibility(View.GONE);
                Picasso.get().load(url).placeholder(R.drawable.img_loading).error(R.drawable.img_loading).into(holder.iv_img);
            }
        }catch (Exception e){
            Log.e("Err",e.getMessage()+"");
        }
    }



    @Override
    public int getItemCount() {
        return Math.max(array.length(), 0);
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        public RelativeLayout linear_video;
        public TextView tv_title;
        public ImageView iv_img;
        public LinearLayout linear;
        public YouTubePlayerView youTubeView;
        ItemHolder(@NonNull View itemView, final Context context) {
            super(itemView);
            MyFont.getInstance().setFont(context, itemView, 1);
            youTubeView = itemView.findViewById(R.id.youtube_player_view);
            linear = itemView.findViewById(R.id.linear);
            linear_video = itemView.findViewById(R.id.linear_video);
            tv_title = itemView.findViewById(R.id.tv_title);
            iv_img = itemView.findViewById(R.id.iv_img);
            final int height = MyFunction.getInstance().getBannerHeightTab(context);
            iv_img.getLayoutParams().height = height;
            linear_video.getLayoutParams().height = MyFunction.getInstance().getProductBannerHeight(context);
        }
    }
}
