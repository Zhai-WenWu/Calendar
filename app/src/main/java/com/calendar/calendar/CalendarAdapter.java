package com.calendar.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.calendar.R;
import com.calendar.utils.SV;
import com.calendar.utils.StringUtils;

import java.util.List;


/**
 * 日历适配器.
 */

public class CalendarAdapter extends BaseAdapter {

    private List<String> list;
    private Context context;
    private CalendarTime calendarTime;

    public CalendarAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_calendar, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (!list.get(position).equals("0")) {
            SV.show(viewHolder.calendarShow, list.get(position));
        } else {
            viewHolder.calendarShow.setText("");
            viewHolder.calendarShow.setBackgroundResource(R.drawable.chose_calendar_four);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewHolder.calendarShow.getText().length()>0){
                    calendarTime.showData(Integer.parseInt(viewHolder.calendarShow.getText().toString()));
                }
            }
        });

        return convertView;
    }


    static class ViewHolder {
        TextView calendarShow;

        ViewHolder(View view) {
            calendarShow = view.findViewById(R.id.calendar_show);
        }
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
