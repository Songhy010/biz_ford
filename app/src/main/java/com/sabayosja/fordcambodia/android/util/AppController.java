package com.sabayosja.fordcambodia.android.util;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {
    private static AppController mInstance;
    private static RequestQueue queue;

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;


       /* final String global = MyFunction.getInstance().getDecrypted(this, MyFunction.getInstance().readFileAsset(this, getFilename()));
        Global.arData = global.split(";");*/
        Global.arData = new String[100];
        Global.arData[0] = "http://rmagroup.phumtest.com/";
        Global.arData[1] = "mobile/";
        Global.arData[2] = "Authorization";
        Global.arData[3] = "Bearer";
        Global.arData[4] = "Access_Token";
        Global.arData[5] = "get_data.php";
        Global.arData[6] = "language";
        Global.arData[7] = "id";
        Global.arData[8] = "splash_screen";
        Global.arData[9] = "image";
        Global.arData[10] = "image_720";
        Global.arData[11] = "home_banner";
        Global.arData[12] = "data";
        Global.arData[13] = "home_data";
        Global.arData[14] = "product_list_data";
        Global.arData[15] = "cate_name";
        Global.arData[16] = "ads_banner";
        Global.arData[17] = "products";
        Global.arData[18] = "title";
        Global.arData[19] = "products/product_detail_";

        Global.arData[20] = "slide";
        Global.arData[21] = "gallery";
        Global.arData[22] = "exterior";
        Global.arData[23] = "interior";
        Global.arData[24] = "url";
        Global.arData[25] = "content";
        Global.arData[26] = "type";




/*
        String s = "";
        for (int i = 0; i < Global.arData.length; i++) {
            s += Global.arData[i] + ";";
        }
        final String encrypted = MyFunction.getInstance().getEncrypted(this, s);
        Log.e("Encrypted", encrypted + "");
*/

        queue = Volley.newRequestQueue(this.getApplicationContext());

    }

    public <T> void addToRequestQueue(Request<T> req) {
        queue.add(req);
    }


    private String getFilename() {
        StringBuilder result = new StringBuilder();
        final int[] st = {100, 97, 116, 97};
        for (int value : st) {
            result.append((char) value);
        }
        return result.toString();
    }
}
