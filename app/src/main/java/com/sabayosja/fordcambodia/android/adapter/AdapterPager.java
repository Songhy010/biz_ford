package com.sabayosja.fordcambodia.android.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.util.MyFont;

import java.util.ArrayList;
import java.util.List;



public class AdapterPager extends FragmentPagerAdapter {
    private String[] titles;
    private final List<Fragment> fragments = new ArrayList<>();
    private Context context;


    public AdapterPager(final FragmentManager manager, Context context, String[] titles) {
        super(manager);
        this.context = context;
        this.titles = titles;
    }

    public void addFrag(Fragment fragment) {
        try {
            fragments.add(fragment);
        } catch (Exception e) {
            Log.e("Err", e.getMessage());
        }
    }

    @Override
    public Fragment getItem(int position) {
        try {
            return fragments.get(position);
        } catch (Exception e) {
            Log.e("Err", e.getMessage());
        }
        return null;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        try {
            return titles[position];
        } catch (Exception e) {
            Log.e("Err", e.getMessage());
        }
        return "";
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_tap_in, null);
        MyFont.getInstance().setFont(context,view,1);
        TextView tabTextView = view.findViewById(R.id.tab);
        tabTextView.setText(titles[position]);
        return view;
    }
    public View getSelectedTabView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView tabTextView = view.findViewById(R.id.tab);
        tabTextView.setText(titles[position]);
        tabTextView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        return view;
    }
}
