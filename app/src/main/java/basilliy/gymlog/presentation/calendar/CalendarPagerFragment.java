package basilliy.gymlog.presentation.calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Calendar;

import basilliy.gymlog.R;


public class CalendarPagerFragment extends Fragment {

    private CalendarMonth month;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar_pager, container, false);

        TextView monthName = (TextView) v.findViewById(R.id.month);
        TextView years = (TextView) v.findViewById(R.id.year);

        monthName.setText(month.getName());
        years.setText(String.valueOf(month.getCalendar().get(Calendar.YEAR)));

        GridView grid = (GridView) v.findViewById(R.id.calendar_grid);
        CalendarGridAdapter gridAdapter = new CalendarGridAdapter(getContext(), month);
        grid.setAdapter(gridAdapter);

        return v;
    }

    public static CalendarPagerFragment newInstance(CalendarMonth m) {
        CalendarPagerFragment fragment = new CalendarPagerFragment();
        fragment.month = m;
        return fragment;
    }

}



