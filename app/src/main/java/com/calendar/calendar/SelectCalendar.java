package com.calendar.calendar;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.calendar.R;
import com.calendar.calendar.YueViewPagerAdapter;
import com.calendar.utils.SV;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * 日历选择控件
 */

public class SelectCalendar extends RelativeLayout {

    private View view;
    private int status = 0;    //当前选中的状态，0表示未选择，1表示选择一个，二表示选择俩个
    private int currentYear;    //当前显示的年份
    private int currentMonth;    //当前显示的月份
    private int nian;    //当前显示的年份
    private int yue;    //当前显示的月份
    private int week = -1;    //当前月份1号是星期几
    private int fromweek = -1;  //起始点所在月份1号星期几
    private int toweek = -1;     //结束点所在所在1号星期几
    private CalendarTime calendarTime;
    private ArrayList<List<String>> weeks;
    private YueViewPagerAdapter yueViewPagerAdapter;
    private ImageView calendarUp;
    private TextView calendarTimeTv;
    private ImageView calendarDown;
    private ViewPager viewpager_yue;
    private int lastValue = 96;
    private String mTimeTv;
    public static int mPosition = -1;
    private int currentYear_;
    private int currentMonth_;

    public SelectCalendar(Context context) {
        super(context);
    }

    public SelectCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public SelectCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        view = LayoutInflater.from(context).inflate(R.layout.layout_calendar, SelectCalendar.this);
        calendarUp = view.findViewById(R.id.calendar_up);
        calendarTimeTv = view.findViewById(R.id.calendar_time);
        calendarDown = view.findViewById(R.id.calendar_down);
        viewpager_yue = view.findViewById(R.id.viewpager_yue);
        weeks = new ArrayList<>();
//        weeks = WeeksData.getInstance().getWeeks();
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        nian = currentYear;
        yue = currentMonth;
        currentYear_ = currentYear;
        currentMonth_ = currentMonth;
        SV.show(calendarTimeTv, currentYear + "年" + currentMonth + "月");

        for (int i = 0; i < 96; i++) {
            if (currentMonth_ != 1) {
                currentMonth_ = currentMonth_ - i;
            } else {
                currentYear_ = currentYear_ - 1;
                currentMonth_ = 12;
            }
            List<String> week1 = getWeek(currentYear_, currentMonth_);
            weeks.add(0, week1);
        }

        for (int i = 0; i < 6; i++) {
            if (currentMonth != 12) {
                currentMonth++;
            } else {
                currentYear++;
                currentMonth = 1;
            }
            List<String> week1 = getWeek(currentYear, currentMonth);
            weeks.add(week1);
        }

//        viewpager_yue.setOffscreenPageLimit(3);
        yueViewPagerAdapter = new YueViewPagerAdapter(context, weeks);
        viewpager_yue.setAdapter(yueViewPagerAdapter);

        viewpager_yue.setCurrentItem(96);

        yueViewPagerAdapter.setOnTimeLisenter(new YueViewPagerAdapter.CalendarTime() {
            @Override
            public void showData(int data, int position) {
                calendarTime.showData(nian, yue, data);
                if (!calendarTimeTv.getText().toString().equals(mTimeTv)) {
                    yueViewPagerAdapter.notifyDataSetChanged();
                }
                mPosition = position;
                mTimeTv = calendarTimeTv.getText().toString();
            }
        });

        viewpager_yue.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                if (position == yueViewPagerAdapter.getmData().size() - 2) {
                    for (int i = 0; i < 6; i++) {
                        if (currentMonth != 12) {
                            currentMonth++;
                        } else {
                            currentYear++;
                            currentMonth = 1;
                        }
                        List<String> week1 = getWeek(currentYear, currentMonth);
                        weeks.add(week1);
                    }
                    yueViewPagerAdapter.notifyDataSetChanged();
                }

//                if (position == 1) {
//                    for (int i = 0; i < 6; i++) {
//                        if (currentMonth != 1) {
//                            List<String> week1 = getWeek(currentYear, currentMonth - i);
//                            weeks.add(0, week1);
//                        } else {
//                            currentYear_ = currentYear - 1;
//                            List<String> week1 = getWeek(currentYear_, 12);
//                            weeks.add(0, week1);
//                        }
//                    }
//                    yueViewPagerAdapter.notifyDataSetChanged();
//                }


                if (position > lastValue) {
//                    Log.e("onPageScrolled","--->左划");
                    if (yue != 12) {
                        yue++;
                    } else {
                        nian++;
                        yue = 1;
                    }
                } else if (position < lastValue) {
//                    Log.e("onPageScrolled","--->右划");
                    if (yue != 1) {
                        yue--;
                    } else {
                        nian--;
                        yue = 12;
                    }
                }

                SV.show(calendarTimeTv, nian + "年" + yue + "月");
                lastValue = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        calendarUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {   //往前退月份
                reFreshView("-");
            }
        });
        calendarDown.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                reFreshView("+");
            }
        });
    }

    private List<String> getWeek(int currentYear, int currentMonth) {
        List<String> list = new ArrayList<>();
        try {
            week = com.calendar.calendar.CalendarUtils.getWeek(currentYear, currentMonth, 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (week != -1) {
            for (int i = 0; i < week; i++) {
                list.add("0");    //添加空格
            }
        }
        for (int i = 0; i < com.calendar.calendar.CalendarUtils.getDayByMonth(currentYear, currentMonth); i++) {
            list.add(i + 1 + "");    //添加本月多少号
        }
        return list;
    }

    //翻日历的时候刷新布局
    public void reFreshView(String s) {

        if (status == 1) {
            toweek = week;
        } else {
            fromweek = week;
        }
        if ("+".equals(s)) {
            viewpager_yue.setCurrentItem(viewpager_yue.getCurrentItem() + 1);

        } else if ("-".equals(s) && viewpager_yue.getCurrentItem() > 0) {
            viewpager_yue.setCurrentItem(viewpager_yue.getCurrentItem() - 1);
        }
    }

    //用于被外部监听
    public void setOnTimeLisenter(CalendarTime calendarTime) {
        this.calendarTime = calendarTime;
    }

    //用来供外部获取点击的时间数据
    public interface CalendarTime {
        void showData(int nian, int yue, int ri);
    }

}
