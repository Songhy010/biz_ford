package com.sabayosja.fordcambodia.android.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.listener.LoadDataListener;
import com.sabayosja.fordcambodia.android.listener.VolleyCallback;
import com.sabayosja.fordcambodia.android.model.ModelBooking;
import com.sabayosja.fordcambodia.android.util.DayDisableDecorator;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ActivitySelectDate extends ActivityController {

    MaterialCalendarView materialCalendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        Global.activitySelectDate = this;
        initView();
    }

    private void initView() {
        initToolbar();
        initCalendar();
        initHoliday();
        initCalendarSelected();
    }

    private void initCalendarSelected() {
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                try {
                    String strDate = DateFormat.getDateInstance(DateFormat.SHORT).format(date.getDate());
                    ModelBooking.getInstance().setDate(MyFunction.getInstance().formatDate(strDate,"dd/MM/yyyy","yyyy-MM-dd"));
                    MyFunction.getInstance().openActivity(ActivitySelectDate.this,ActivitySelectTime.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void initCalendar() {
        materialCalendarView = findViewById(R.id.calendarView);
        MyFont.getInstance().setFont(this, materialCalendarView.getRootView(), 1);
        final Calendar calendar = Calendar.getInstance();
        final Calendar calendarNextMonth = Calendar.getInstance();
        calendarNextMonth.add(Calendar.MONTH, 1);
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)))
                .setMaximumDate(CalendarDay.from(calendarNextMonth.get(Calendar.YEAR), calendarNextMonth.get(Calendar.MONTH), calendarNextMonth.getActualMaximum(Calendar.DAY_OF_MONTH)))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        initWeekend(calendar);
        initWeekend(calendarNextMonth);
    }

    private void initWeekend(Calendar cal) {
        List<Date> disable = new ArrayList<>();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int month = cal.get(Calendar.MONTH);
        do {
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            if(ModelBooking.getInstance().getArrRepairID().size()>0) {
                if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY)
                    disable.add(cal.getTime());
            }else{
                if (dayOfWeek == Calendar.SUNDAY)
                    disable.add(cal.getTime());
            }
            cal.add(Calendar.DAY_OF_MONTH, 1);
        } while (cal.get(Calendar.MONTH) == month);
        final ArrayList<CalendarDay> enabledDates = new ArrayList<>();
        final ArrayList<String> status = new ArrayList<>();
        for (Date date : disable) {
            status.add("1");//holiday
            enabledDates.add(new CalendarDay(date));
        }
        materialCalendarView.addDecorator(new DayDisableDecorator(ActivitySelectDate.this,enabledDates,status));
    }

    private void initToolbar() {
        final TextView tv_title = findViewById(R.id.tv_title);
        final ImageView iv_ford = findViewById(R.id.iv_ford);
        final ImageView iv_search = findViewById(R.id.iv_search);
        final ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setImageDrawable(getResources().getDrawable(R.drawable.img_arrow));
        iv_back.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.MULTIPLY);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        iv_ford.setVisibility(View.GONE);
        iv_search.setVisibility(View.GONE);
        tv_title.setText(getString(R.string.select_date));
    }

    private void blockDate(final JSONArray array) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            final ArrayList<String> status = new ArrayList<>();
            final ArrayList<CalendarDay> enabledDates = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                final JSONObject object = array.getJSONObject(i);
                final Date date = format.parse(object.getString(Global.arData[68]));
                status.add(object.getString(Global.arData[79]));
                enabledDates.add(new CalendarDay(date));
            }
            materialCalendarView.addDecorator(new DayDisableDecorator(ActivitySelectDate.this,enabledDates,status));
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    private void initHoliday() {
        final String lang = MyFunction.getInstance().getText(ActivitySelectDate.this, Global.arData[6]);
        final String url = Global.arData[0] + Global.arData[1] + Global.arData[5];
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[6], lang);
        param.put(Global.arData[7], String.format("%s_%s", Global.arData[78], ModelBooking.getInstance().getStationID()));
        loadDataServer(param, url, new LoadDataListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    Log.e("response", response);
                    if (!response.isEmpty()) {
                        if (MyFunction.getInstance().isValidJSON(response)) {
                            blockDate(new JSONArray(response));
                        } else {
                            MyFunction.getInstance().alertMessage(ActivitySelectDate.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                        }
                    }
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
            }
        });
    }

    private void loadDataServer(final HashMap<String, String> param, final String url, final LoadDataListener loadDataListener) {
        showDialog();
        MyFunction.getInstance().requestString(this, Request.Method.POST, url, param, new VolleyCallback() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Response", response);
                    loadDataListener.onSuccess(response);
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
                hideDialog();
            }

            @Override
            public void onErrorResponse(VolleyError e) {
                Log.e("Err", e.getMessage() + "");
                MyFunction.getInstance().alertMessage(ActivitySelectDate.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                hideDialog();
            }
        });
    }


}