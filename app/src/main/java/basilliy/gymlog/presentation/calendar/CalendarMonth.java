package basilliy.gymlog.presentation.calendar;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import basilliy.gymlog.application.App;
import basilliy.gymlog.domain.entity.ActiveProgram;

class CalendarMonth implements Serializable {
    private ArrayList<CalendarDay> days;
    private String name;
    private Calendar calendar;
    private Context context;

    private static String[] namesOfMonth = { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};

    CalendarMonth(Context context) {
        this.context = context;
        calendar = Calendar.getInstance();
        setCalendar();
    }

    private CalendarMonth(Context context, Calendar calendar) {
        this.context = context;
        this.calendar = calendar;
        setCalendar();
    }

    public String getName() {
        return name;
    }

    ArrayList<CalendarDay> getDays(){
        return days;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public CalendarMonth create(int dif) {
        Calendar calendar = (Calendar) this.calendar.clone();
        calendar.add(Calendar.MONTH, dif);
        return new CalendarMonth(context, calendar);
    }

    private void setCalendar() {
        days = new ArrayList<>(7 * 6);
        int min = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, min);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        if (dayOfWeek < 0) dayOfWeek += 7;
        name = namesOfMonth[calendar.get(Calendar.MONTH)];

        Calendar clone = (Calendar) calendar.clone();
        clone.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
        clone.set(Calendar.HOUR_OF_DAY, 0);
        clone.set(Calendar.MINUTE, 0);
        clone.set(Calendar.SECOND, 0);
        clone.set(Calendar.MILLISECOND, 0);

        for (int i = 0; i < dayOfWeek; i++) {
            days.add(new CalendarDay(clone.getTime(), CalendarDay.DISABLE));
            clone.add(Calendar.DAY_OF_MONTH, 1);
        }

        for (int i = min; i <= max; i++) {
            days.add(new CalendarDay(clone.getTime(), CalendarDay.NORMAL));
            clone.add(Calendar.DAY_OF_MONTH, 1);
        }

        while (days.size() < 6 * 7) {
            days.add(new CalendarDay(clone.getTime(), CalendarDay.DISABLE));
            clone.add(Calendar.DAY_OF_MONTH, 1);
        }

        setDayMarker();
    }

    private void setDayMarker() {
        ActiveProgram activeProgram = App.getActiveProgramService().get();

        Calendar clone = (Calendar) calendar.clone();
        clone.set(Calendar.HOUR_OF_DAY, 0);
        clone.set(Calendar.MINUTE, 0);
        clone.set(Calendar.SECOND, 0);
        clone.set(Calendar.MILLISECOND, 0);

        while (calendar.get(Calendar.MONTH) == clone.get(Calendar.MONTH)) {
            clone.setTime(activeProgram.getWorkDate(clone));
            days = setDaySelected(days, clone, CalendarDay.SELECTED);
            clone.add(Calendar.DAY_OF_MONTH, 1);
        }

        clone = Calendar.getInstance();
        clone.set(Calendar.HOUR_OF_DAY, 0);
        clone.set(Calendar.MINUTE, 0);
        clone.set(Calendar.SECOND, 0);
        clone.set(Calendar.MILLISECOND, 0);

        if (calendar.get(Calendar.YEAR) == clone.get(Calendar.YEAR) &&
                calendar.get(Calendar.MONTH) == clone.get(Calendar.MONTH)) {
            setDaySelected(days, clone, CalendarDay.CURRENT);
        }
    }

    private ArrayList<CalendarDay> setDaySelected(ArrayList<CalendarDay> days, Calendar calendar, int state) {
        for (CalendarDay day : days)
            if (day.getDate().getTime() == calendar.getTimeInMillis()) {
                day.setState(state);
                break;
            }
        return days;
    }
}
