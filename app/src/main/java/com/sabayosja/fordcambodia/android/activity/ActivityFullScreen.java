package com.sabayosja.fordcambodia.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ActivityFullScreen extends ActivityController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.setSystemBarColor(this,R.color.black);
        setContentView(R.layout.activity_full_screen);
        initView();
    }

    private void initView(){
        initPlayer();
        initBack();
    }

    private void initBack(){
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initPlayer(){
      final YouTubePlayerView youtubePlayerView = findViewById(R.id.youtubePlayer);
        getLifecycle().addObserver(youtubePlayerView);
        youtubePlayerView.isFullScreen();
        youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(getIntentData(), 0);
            }
        });

    }

    private String getIntentData(){
        final HashMap<String,String> map = MyFunction.getInstance().getIntentHashMap(getIntent());
        return map.get(Global.arData[24]);
    }
}