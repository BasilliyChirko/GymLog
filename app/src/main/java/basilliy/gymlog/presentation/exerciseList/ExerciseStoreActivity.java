package basilliy.gymlog.presentation.exerciseList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import basilliy.gymlog.R;
import basilliy.gymlog.application.App;
import basilliy.gymlog.domain.entity.ExerciseStore;
import basilliy.gymlog.presentation.programConstructor.MuscleDropDownAdapter;
import basilliy.gymlog.presentation.utils.SecondActivity;
import io.realm.RealmResults;

public class ExerciseStoreActivity extends SecondActivity {

    public static final int REQUEST = 1453;
    public static final String KEY_EXERCISE = "key_exercise";

    private RealmResults<ExerciseStore> data;
    private boolean[] show;
    private String anyMuscle = "Любая";
    private RecyclerAdapter adapter;

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


        data = App.getExerciseStoreService().getAll();
        show = new boolean[data.size()];

        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        adapter = new RecyclerAdapter();
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
        if (muscle.equals(anyMuscle)) {
            data = App.getExerciseStoreService().getAll();
        } else {
            data = App.getExerciseStoreService().getByMusle(muscle);
        }

        show = new boolean[data.size()];
        adapter.notifyDataSetChanged();
    }

    private void onClickItem(ExerciseStore store) {
        Intent intent = new Intent();
        intent.putExtra(KEY_EXERCISE, store);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void onClickMore(ExerciseStore store) {
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

    private class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        View more;
        View add;
        View detail;
        TextView name;
        TextView level;
        TextView targetMuscle;
        TextView involvedMuscle;
        TextView inventory;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            name = (TextView) view.findViewById(R.id.name);
            level = (TextView) view.findViewById(R.id.level);
            targetMuscle = (TextView) view.findViewById(R.id.target_muscle);
            involvedMuscle = (TextView) view.findViewById(R.id.extra_muscle);
            inventory = (TextView) view.findViewById(R.id.inventory);
            more = view.findViewById(R.id.more);
            add = view.findViewById(R.id.add);
            detail = view.findViewById(R.id.detail);
        }
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(getLayoutInflater().inflate(R.layout.element_exercise_store, parent, false));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final ExerciseStore store = data.get(position);
            holder.name.setText(store.getName());
            holder.level.setText("Сложность: " + store.getLevelString());
            holder.inventory.setText("Инвентарь: " + store.getInventory());
            holder.targetMuscle.setText("Целевая мышца: " + store.getTargetMuscle());
            holder.involvedMuscle.setText("Вовлеченные мышцы: " + store.getInvolvedMuscle());

            holder.detail.setVisibility(show[holder.getAdapterPosition()] ? View.VISIBLE : View.GONE);

            holder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickMore(store);
                }
            });

            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItem(store);
                }
            });

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show[holder.getAdapterPosition()] = !show[holder.getAdapterPosition()];
                    notifyItemChanged(holder.getAdapterPosition());
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

    }
}
