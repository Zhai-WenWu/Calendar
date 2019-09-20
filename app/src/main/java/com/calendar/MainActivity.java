package com.calendar;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.calendar.calendar.SelectCalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    private SelectCalendar calendar;
    public static long stime;
    public static long etime;
    private SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendar = (SelectCalendar) findViewById(R.id.calendar);

        sdf = new SimpleDateFormat("yyyy-MM-dd");
        calendar.setOnTimeLisenter(new SelectCalendar.CalendarTime() {

            @Override
            public void showData(int nian, int yue, int ri) {
                if (stime == 0) {
                    try {
                        stime = sdf.parse(nian + "-" + yue + "-" + ri).getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        etime = sdf.parse(nian + "-" + yue + "-" + ri).getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (etime < stime) {
                        Toast.makeText(MainActivity.this, "结束时间不得大于开始时间", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, nian + "-" + yue + "-" + ri, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}
