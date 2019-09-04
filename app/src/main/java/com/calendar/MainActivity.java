package com.calendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.calendar.calendar.SelectCalendar;

public class MainActivity extends AppCompatActivity implements SelectCalendar.CalendarTime {

    private SelectCalendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendar = (SelectCalendar) findViewById(R.id.calendar);
        calendar.setOnTimeLisenter(this);
    }

    @Override
    public void showData(int nian, int yue, int ri) {
        Toast.makeText(this, nian + "-" + yue + "-" + ri, Toast.LENGTH_SHORT).show();
    }
}
