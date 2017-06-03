package basilliy.gymlog.presentation.exerciseList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;

import basilliy.gymlog.R;
import basilliy.gymlog.application.App;
import basilliy.gymlog.domain.entity.ExerciseStore;
import basilliy.gymlog.presentation.programConstructor.MuscleDropDownAdapter;
import basilliy.gymlog.presentation.utils.SecondActivity;

public class ExerciseStoreActivity extends SecondActivity implements ExerciseStoreAdapter.ExerciseListListener {

    public static final int REQUEST = 1453;
    public static final String KEY_EXERCISE = "key_exercise";
    
    private String anyMuscle = "Любая";
    private ExerciseStoreAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.fab).setVisibility(View.GONE);

        final ArrayList<String> muscleList = App.getExerciseStoreService().getMuscleList();
        muscleList.add(0, anyMuscle);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new MuscleDropDownAdapter(this, android.R.layout.simple_spinner_dropdown_item, muscleList));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onSelectMuscle(muscleList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        adapter = new ExerciseStoreAdapter(getLayoutInflater());
        adapter.setData(App.getExerciseStoreService().getAll());
        adapter.setListener(this);

        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public int getContentView() {
        return R.layout.activity_exercise_store;
    }

    @Override
    public void onClickFloatButton() {}

    private void onSelectMuscle(String muscle) {
        if (muscle.equals(anyMuscle)) adapter.setData(App.getExerciseStoreService().getAll());
        else adapter.setData(App.getExerciseStoreService().getByMusle(muscle));

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClickItem(ExerciseStore store) {
        Intent intent = new Intent();
        intent.putExtra(KEY_EXERCISE, store);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClickMore(ExerciseStore store) {
        Intent intent = new Intent(this, ExerciseStoreMoreActivity.class);
        intent.putExtra(ExerciseStoreMoreActivity.KEY_EXERCISE, store);
        startActivityForResult(intent, REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST && resultCode == RESULT_OK)
            onClickItem((ExerciseStore) data.getParcelableExtra(ExerciseStoreMoreActivity.KEY_EXERCISE));
    }


}
