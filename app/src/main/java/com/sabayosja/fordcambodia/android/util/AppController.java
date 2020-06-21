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
        Global.arData[27] = "features";
        Global.arData[28] = "videos";
        Global.arData[29] = "video";
        Global.arData[30] = "colorizer";
        Global.arData[31] = "color";
        Global.arData[32] = "price";
        Global.arData[33] = "service_list_data";
        Global.arData[34] = "maintenance_type";
        Global.arData[35] = "name";
        Global.arData[36] = "maintenance/maintenance_list";
        Global.arData[37] = "cate_id";
        Global.arData[38] = "models_list_data";
        Global.arData[39] = "models_name";
        Global.arData[40] = "maintenance";
        Global.arData[41] = "years";
        Global.arData[42] = "year";
        Global.arData[43] = "accessories/accessory_list";
        Global.arData[44] = "accid";
        Global.arData[45] = "price";
        Global.arData[46] = "models_id";
        Global.arData[47] = "years_id";
        Global.arData[48] = "post_type";
        Global.arData[49] = "pt-news-promo";
        Global.arData[50] = "news_promotions";
        Global.arData[51] = "phone";
        Global.arData[52] = "sendotp.php";
        Global.arData[53] = "verify_otp.php";
        Global.arData[54] = "otp";
        Global.arData[55] = "booking/booking_list_855";
        Global.arData[56] = "customer";
        Global.arData[57] = "model";
        Global.arData[58] = "plate_number";
        Global.arData[59] = "mileage";
        Global.arData[60] = "issues";
        Global.arData[61] = "service_date";
        Global.arData[62] = "service_time";
        Global.arData[63] = "branch";
        Global.arData[64] = "booking_id";
        Global.arData[65] = "update_booking_status.php";
        Global.arData[66] = "time";
        Global.arData[67] = "branch_id";
        Global.arData[68] = "date";





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
