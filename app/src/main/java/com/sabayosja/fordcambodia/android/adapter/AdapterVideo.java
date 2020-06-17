package com.sabayosja.fordcambodia.android.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

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


public class AdapterVideo extends PagerAdapter {

    ViewGroup view;
    private Context mContext;
    private JSONArray array;

    public AdapterVideo(Context mContext, JSONArray array) {
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
            view = (ViewGroup) inflater.inflate(R.layout.item_video, container, false);
            MyFont.getInstance().setFont(mContext,view,1);
            final JSONObject object = array.getJSONObject(position);
            final String url = object.getString(Global.arData[29]);
            final YouTubePlayerView youtube_player_view = view.findViewById(R.id.youtube_player_view);
            ((ComponentActivity)mContext).getLifecycle().addObserver(youtube_player_view);
            youtube_player_view.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull final YouTubePlayer youTubePlayer) {
                    youTubePlayer.loadVideo(url, 0);
                    youTubePlayer.pause();
                }
            });
            youtube_player_view.addFullScreenListener(new YouTubePlayerFullScreenListener() {
                @Override
                public void onYouTubePlayerEnterFullScreen() {
                    final HashMap<String,String> map = new HashMap<>();
                    map.put(Global.arData[24],url);
                    MyFunction.getInstance().openActivity(mContext, ActivityFullScreen.class,map);
                    youtube_player_view.toggleFullScreen();
                }

                @Override
                public void onYouTubePlayerExitFullScreen() {
                }
            });
            container.addView(view);
        }catch (Exception e){
            Log.e("Err",e.getMessage());
        }

        return view;
    }


}
