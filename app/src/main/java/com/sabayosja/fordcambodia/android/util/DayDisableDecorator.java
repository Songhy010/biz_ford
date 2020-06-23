package com.sabayosja.fordcambodia.android.util;


import android.content.Context;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.sabayosja.fordcambodia.android.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class DayDisableDecorator implements DayViewDecorator {
    private HashSet<CalendarDay> dates;
    private Context context;
    private int index = 0;
    private ArrayList<String> status;

    public DayDisableDecorator(Context context , Collection<CalendarDay> dates) {
        this.dates = new HashSet<>(dates);
        this.context = context;
        this.status = new ArrayList<>();
    }

    public DayDisableDecorator(Context context , Collection<CalendarDay> dates,final ArrayList<String> status) {
        this.dates = new HashSet<>(dates);
        this.context = context;
        this.status = status;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setDaysDisabled(true);
        if(status.size()>0) {
            switch (status.get(index)) {
                case "1":
                    view.addSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.red_400)));
                    break;
                default:
                    view.addSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.grey)));
                    break;
            }
        }else{
            view.addSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.red_400)));
        }
        index++;
    }
}
