package basilliy.gymlog.presentation.programActive;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.Map;
import java.util.Set;

import basilliy.gymlog.R;
import basilliy.gymlog.application.App;
import basilliy.gymlog.domain.entity.ActiveProgram;
import basilliy.gymlog.domain.entity.Day;
import basilliy.gymlog.domain.entity.Exercise;
import basilliy.gymlog.presentation.navigation.FragmentOnRoot;
import basilliy.gymlog.presentation.navigation.NavigationActivity;


public class ProgramActiveFragment extends FragmentOnRoot
        implements ActiveExerciseAdapter.OnExerciseDoneListener,
        SetExerciseResultDialog.OnSetExerciseResultListener {

    private ActiveProgram program;
    private ActiveExerciseAdapter adapter;
    private Day day;

    private View cancel;
    private RecyclerView list;
    private TextView label;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initRootActivityElement();
        program = App.getActiveProgramService().get();
        return inflater.inflate(R.layout.fragmengt_program_active, container, false);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        label = (TextView) v.findViewById(R.id.label_no_active);
        label.setVisibility(program == null ? View.VISIBLE : View.GONE);
        cancel.setVisibility(program == null ? View.GONE : View.VISIBLE);

        if (program != null) {
            day = program.getWorkDay(Calendar.getInstance());
            if (day == null || day.getExerciseList().isEmpty()) displayNextDate(label);
            else displayDay(day, v);
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

    private void displayDay(Day day, View view) {
        list = (RecyclerView) view.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setItemAnimator(new DefaultItemAnimator());
        adapter = new ActiveExerciseAdapter(day, getActivity().getLayoutInflater(), getContext(), this);
        list.setAdapter(adapter);
        checkExercisesIsDone();
    }

    @Override
    public void onExerciseDoneClick(int position) {
        if (program.isChangeable()) {
            SetExerciseResultDialog.newInstance(position, this).show(getFragmentManager(), "tag");
        } else finishExercise(position);
    }

    @Override
    public void onSetExerciseResult(int result, int position) {
        Exercise exercise = day.getExerciseList().get(position);
        switch (result) {
            case SetExerciseResultDialog.GOOD:
                exercise.increaseApproachValue();
                break;
            case SetExerciseResultDialog.BAD:
                exercise.decreaseApproachValue();
                break;
        }
        finishExercise(position);
    }

    private void finishExercise(int position) {
        if (day != null && !day.getExerciseList().isEmpty()) {
            day.getExerciseList().get(position).setDone(true);
            adapter.notifyItemChanged(position);
        }
        checkExercisesIsDone();
    }

    private void checkExercisesIsDone() {
        for (Exercise exercise : day.getExerciseList())
            if (!exercise.isDone()) return;

        label.setText("Все упражнения на сегодня выполнены");
        list.setVisibility(View.GONE);
        label.setVisibility(View.VISIBLE);
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
        setFloatButtonVisible(false);
    }

    public static ProgramActiveFragment newInstance() {
        Bundle args = new Bundle();
        ProgramActiveFragment fragment = new ProgramActiveFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
