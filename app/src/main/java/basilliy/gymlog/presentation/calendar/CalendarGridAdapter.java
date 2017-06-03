package basilliy.gymlog.presentation.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

import basilliy.gymlog.R;

class CalendarGridAdapter extends BaseAdapter {

    private CalendarMonth month;
    private LayoutInflater inflater;

    CalendarGridAdapter(Context context, CalendarMonth month) {
        this.month = month;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return month.getDays().size();
    }

    @Override
    public CalendarDay getItem(int position) {
        return month.getDays().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null)
            v = inflater.inflate(R.layout.element_calendar, parent, false);

        TextView number = (TextView) v.findViewById(R.id.number);

        CalendarDay day = month.getDays().get(position);

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(day.getDate());
        number.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));

        switch (day.getState()) {
            case CalendarDay.CURRENT:
                v.setBackgroundResource(R.drawable.calendar_current);
                number.setTextColor(0xfffafafa);
                break;
            case CalendarDay.SELECTED:
                v.setBackgroundResource(R.drawable.calendar_selected);
                number.setTextColor(0xfffafafa);
                break;
            case CalendarDay.NORMAL:
                v.setBackgroundResource(R.drawable.calendar_normal);
                number.setTextColor(0xff111122);
                break;
            case CalendarDay.DISABLE:
                v.setBackgroundResource(R.drawable.calendar_normal);
                number.setTextColor(0xffcccccc);
                break;
        }

        v.setOnClickListener(null);

        return v;
    }

}
