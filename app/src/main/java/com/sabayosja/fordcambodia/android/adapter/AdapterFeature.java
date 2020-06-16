package com.sabayosja.fordcambodia.android.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

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
            holder.tv_title.setText(object.getString("content"));
            final String url = object.getString(Global.arData[9]);
            if(object.getString("type").equals("1")){
                holder.iv_img.setVisibility(View.GONE);
                holder.linear_video.setVisibility(View.VISIBLE);
                ((ComponentActivity)context).getLifecycle().addObserver(holder.youTubeView);
                holder.youTubeView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        String videoId = "fW0JM7Jo8o4";
                        youTubePlayer.loadVideo(videoId, 0f);
                        youTubePlayer.pause();
                    }
                });
                holder.youTubeView.getPlayerUiController();
            }else {
                holder.iv_img.setVisibility(View.VISIBLE);
                holder.linear_video.setVisibility(View.GONE);
                Picasso.get().load(url).placeholder(R.drawable.img_loading).error(R.drawable.img_loading).into(holder.iv_img);
            }
            //Picasso.get().load(object.getString(Global.arData[9])).placeholder(R.drawable.img_loading).error(R.drawable.img_loading).into(holder.iv_img);
        }catch (Exception e){
            Log.e("Err",e.getMessage()+"");
        }
    }

    @Override
    public int getItemCount() {
        return Math.max(array.length(), 0);
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        public LinearLayout linear_video;
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
            linear_video.getLayoutParams().height = height;
        }
    }
}
