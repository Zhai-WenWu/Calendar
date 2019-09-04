package com.calendar.calendar;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.calendar.R;
import com.calendar.utils.SV;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class YueViewPagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<List<String>> mData;
    //    private List<String> list;
    private int currentYear;    //当前显示的年份
    private int currentMonth;    //当前显示的月份
    private int week = -1;    //当前月份1号是星期几
    private CalendarTime calendarTime;

//    public YueViewPagerAdapter(Context context) {
//        this.context = context;
//    }


    public ArrayList<List<String>> getmData() {
        return mData;
    }

    public YueViewPagerAdapter(Context context, ArrayList<List<String>> list) {
        this.context = context;
        mData = list;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(context, R.layout.item_yue, null);
        GridView calendar_day = (GridView) view.findViewById(R.id.calendar_day);

        CalendarAdapter calendarAdapter = new CalendarAdapter(context, mData.get(position));
        calendar_day.setAdapter(calendarAdapter);
        calendarAdapter.setOnTimeLisenter(new CalendarAdapter.CalendarTime() {
            @Override
            public void showData(int data) {
                calendarTime.showData(data);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container,position,object); 这一句要删除，否则报错
        container.removeView((View) object);
    }

    //用于被外部监听
    public void setOnTimeLisenter(CalendarTime calendarTime) {
        this.calendarTime = calendarTime;
    }

    //用来供外部获取点击的时间数据
    public interface CalendarTime {
        void showData(int data);
    }

}
