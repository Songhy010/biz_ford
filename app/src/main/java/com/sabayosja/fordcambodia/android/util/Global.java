package com.sabayosja.fordcambodia.android.util;

import com.sabayosja.fordcambodia.android.activity.ActivityChooseLanguage;
import com.sabayosja.fordcambodia.android.activity.ActivityInstruction;
import com.sabayosja.fordcambodia.android.activity.ActivityLogin;
import com.sabayosja.fordcambodia.android.activity.ActivityMain;
import com.sabayosja.fordcambodia.android.activity.ActivityOtp;
import com.sabayosja.fordcambodia.android.activity.ActivitySelectCar;
import com.sabayosja.fordcambodia.android.activity.ActivitySelectDate;
import com.sabayosja.fordcambodia.android.activity.ActivitySelectIssue;
import com.sabayosja.fordcambodia.android.activity.ActivitySelectMileage;
import com.sabayosja.fordcambodia.android.activity.ActivitySelectService;
import com.sabayosja.fordcambodia.android.activity.ActivitySelectStation;
import com.sabayosja.fordcambodia.android.activity.ActivitySelectTime;
import com.sabayosja.fordcambodia.android.activity.ActivityYourBooking;

public class Global {
    public static String[] arData;
    public static String INFO_FILE = "info";
    public static String NOTIFICATION = "notification";
    public static String FIRST_TIME = "first_tile";
    public static final String SPLASH_SCREEN = "splash_screen";
    public static final String URI_MESSENGER = "fb-messenger://user-thread/";
    public static final String URI_STORE = "https://play.google.com/store/apps/details?id=";
    public static final String PACKAGE_MG = "com.facebook.orca";
    public static final  String HTML_HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HTML_HEADER_CONTENT_TYPE_VALUE_JSON = "application/json; charset=UTF-8";
    public static int PERMISSION_ALL = 1;

    /* Data */
    public static final String EN = "en";
    public static final String KM = "km";
    public static final String HOME = "home_data";
    public static final String MORNING = "morning.json";
    public static final String AFTERNOON = "afternoon.json";
    public static final String IS_EDIT = "is_edit";
    public static final String FAIL = "0";
    public static final String SUCCESS = "1";
    /* Activity */
    public static ActivityMain activityMain;
    public static ActivityChooseLanguage activityChooseLanguage;
    public static ActivityInstruction activityInstruction;
    public static ActivityOtp activityOtp;
    public static ActivityLogin activityLogin;

    public static ActivitySelectIssue activitySelectIssue;
    public static ActivitySelectMileage activitySelectMileage;
    public static ActivitySelectStation activitySelectStation;
    public static ActivitySelectDate activitySelectDate;
    public static ActivitySelectTime activitySelectTime;

    public static ActivitySelectCar activitySelectCar;
    public static ActivityYourBooking activityYourBooking;
    public static ActivitySelectService activitySelectService;

    /*code*/
    public static int ActivityViewBooking = 300;
}
