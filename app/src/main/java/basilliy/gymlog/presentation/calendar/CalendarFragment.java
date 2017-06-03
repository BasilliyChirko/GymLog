package basilliy.gymlog.presentation.calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import basilliy.gymlog.R;
import basilliy.gymlog.presentation.navigation.FragmentOnRoot;


public class CalendarFragment extends FragmentOnRoot {

    private ViewPager pager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initRootActivityElement();
        return initView(inflater.inflate(R.layout.fragment_calendar, container, false));
    }

    public View initView(View v) {
        pager = (ViewPager) v.findViewById(R.id.pager);
        CalendarPagerAdapter calendarPagerAdapter = new CalendarPagerAdapter(getChildFragmentManager());
        pager.setAdapter(calendarPagerAdapter);
        pager.setCurrentItem(calendarPagerAdapter.currentPosition, false);

        v.findViewById(R.id.left_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(pager.getCurrentItem() - 1, true);
            }
        });

        v.findViewById(R.id.right_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(pager.getCurrentItem() + 1, true);
            }
        });
        return v;
    }

    @Override
    public View getToolbarContent(LayoutInflater inflater) {
        return inflater.inflate(R.layout.action_bar_calendar, null, false);
    }

    @Override
    public void initFloatButton(FloatingActionButton actionButton) {
        setFloatButtonVisible(false);
    }

    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    private class CalendarPagerAdapter extends FragmentPagerAdapter {

        int currentPosition = 5000;
        CalendarMonth m;

        CalendarPagerAdapter(FragmentManager fm) {
            super(fm);
            m = new CalendarMonth(getContext());
        }

        @Override
        public Fragment getItem(int position) {
            m = m.create(position - currentPosition);
            currentPosition = position;
            return CalendarPagerFragment.newInstance(m);
        }

        @Override
        public int getCount() {
            return 10000;
        }
    }
}
