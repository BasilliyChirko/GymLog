package basilliy.gymlog.presentation.calendar;


import java.util.Date;

class CalendarDay {

    static final int CURRENT = 1;
    static final int SELECTED = 2;
    static final int DISABLE = 3;
    static final int NORMAL = 4;

    private Date date;
    private int state = DISABLE;

    CalendarDay(Date date, int state) {
        this.date = date;
        this.state = state;
    }

    Date getDate() {
        return date;
    }

    void setDate(Date date) {
        this.date = date;
    }

    int getState() {
        return state;
    }

    void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "CalendarDay{" +
                ", state=" + (state == CURRENT ? "CURRENT" : (state == SELECTED ? "SELECTED" : "DISABLE")) +
                ", date=" + date +
                '}';
    }
}
