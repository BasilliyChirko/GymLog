package basilliy.gymlog.presentation.programActive;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import basilliy.gymlog.R;
import basilliy.gymlog.application.App;
import basilliy.gymlog.domain.entity.ActiveProgram;
import basilliy.gymlog.domain.entity.Day;
import basilliy.gymlog.domain.entity.Program;
import basilliy.gymlog.presentation.navigation.FragmentOnRoot;
import basilliy.gymlog.presentation.navigation.NavigationActivity;


public class ProgramActiveFragment extends FragmentOnRoot {

    private ActiveProgram program;

    private View cancel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initRootActivityElement();
        program = App.getActiveProgramService().get();
        return inflater.inflate(R.layout.fragmengt_program_active, container, false);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        TextView label = (TextView) v.findViewById(R.id.label_no_active);
        label.setVisibility(program == null ? View.VISIBLE : View.GONE);
        cancel.setVisibility(program == null ? View.GONE : View.VISIBLE);

        if (program != null) {
            Day day = program.getWorkDay(Calendar.getInstance());
            if (day == null || day.getExerciseList().isEmpty()) displayNextDate(label);
            else displayDay(day);
        }
    }

    private void displayNextDate(TextView label) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(program.getWorkDate(calendar));
        label.setVisibility(View.VISIBLE);
        String s = "Следующая тренировка будет " + calendar.get(Calendar.DAY_OF_MONTH) + " " +
                getResources().getStringArray(R.array.month_name)[calendar.get(Calendar.MONTH)];
        label.setText(s);
    }

    private void displayDay(Day day) {

    }


    @Override
    public View getToolbarContent(LayoutInflater inflater) {
        View toolbar = inflater.inflate(R.layout.action_bar_program_active, null, false);
        cancel = toolbar.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeProgram();
            }
        });
        return toolbar;
    }

    private void removeProgram() {
        App.getActiveProgramService().remove();
        ((NavigationActivity) getActivity()).setPage(NavigationActivity.PROGRAM_ACTIVE);
        Toast.makeText(getContext(), "Активная тренировка отменена", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initFloatButton(FloatingActionButton actionButton) {
        setFloatButtonVisible(true);
    }

    public static ProgramActiveFragment newInstance() {
        Bundle args = new Bundle();
        ProgramActiveFragment fragment = new ProgramActiveFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
