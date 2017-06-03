package basilliy.gymlog.presentation.exerciseList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;

import basilliy.gymlog.R;
import basilliy.gymlog.application.App;
import basilliy.gymlog.domain.entity.ExerciseStore;
import basilliy.gymlog.presentation.navigation.FragmentOnRoot;
import basilliy.gymlog.presentation.programConstructor.MuscleDropDownAdapter;


public class ExerciseStoreListFragment extends FragmentOnRoot implements ExerciseStoreAdapter.ExerciseListListener {

    private String anyMuscle = "Любая";
    private String muscle = anyMuscle;
    private ExerciseStoreAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initRootActivityElement();
        View v = inflater.inflate(R.layout.fragment_exercise_store, container, false);

        adapter = new ExerciseStoreAdapter(getActivity().getLayoutInflater());
        adapter.setData(App.getExerciseStoreService().getAll());
        adapter.setShowDone(false);
        adapter.setListener(this);

        RecyclerView list = (RecyclerView) v.findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setItemAnimator(new DefaultItemAnimator());

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        onSelectMuscle(muscle);
    }

    @Override
    public View getToolbarContent(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.action_bar_exercise_store, null, false);
        final ArrayList<String> muscleList = App.getExerciseStoreService().getMuscleList();
        muscleList.add(0, anyMuscle);
        Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
        spinner.setAdapter(new MuscleDropDownAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, muscleList));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onSelectMuscle(muscleList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        return v;
    }

    private void onSelectMuscle(String muscle) {
        this.muscle = muscle;
        if (muscle.equals(anyMuscle)) adapter.setData(App.getExerciseStoreService().getAll());
        else adapter.setData(App.getExerciseStoreService().getByMusle(muscle));

        adapter.notifyDataSetChanged();
    }

    @Override
    public void initFloatButton(FloatingActionButton actionButton) {
        setFloatButtonVisible(true);
        actionButton.setImageResource(R.drawable.ic_add_white);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getContext(), CreateExerciseActivity.class));
            }
        });
    }

    public static ExerciseStoreListFragment newInstance() {
        return new ExerciseStoreListFragment();
    }

    @Override
    public void onClickMore(ExerciseStore store) {
        Intent intent = new Intent(getContext(), ExerciseStoreMoreActivity.class);
        intent.putExtra(ExerciseStoreMoreActivity.KEY_EXERCISE, store);
        intent.putExtra(ExerciseStoreMoreActivity.KEY_DONE, false);
        getActivity().startActivity(intent);
    }

    @Override
    public void onClickItem(ExerciseStore exercise) {}
}
