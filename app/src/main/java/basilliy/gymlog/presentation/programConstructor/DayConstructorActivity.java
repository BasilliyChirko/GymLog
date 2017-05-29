package basilliy.gymlog.presentation.programConstructor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import basilliy.gymlog.R;
import basilliy.gymlog.domain.entity.Approach;
import basilliy.gymlog.domain.entity.Day;
import basilliy.gymlog.domain.entity.Exercise;
import basilliy.gymlog.domain.entity.ExerciseStore;
import basilliy.gymlog.presentation.utils.RecyclerDragAndSwipe;


public class DayConstructorActivity extends ConstructorActivity {

    public static final String KEY_DAY = "key_day";
    public static final String KEY_POSITION = "key_position";
    public static final int REQUEST_STORE = 1002;
    public static final int REQUEST_EXERCISE = 1003;

    private Day day;
    private RecyclerAdapter adapter;
    private TextView name;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new RecyclerAdapter();
        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setItemAnimator(new DefaultItemAnimator());
        RecyclerDragAndSwipe.enable(list, adapter, true, true);

        day = getIntent().getParcelableExtra(KEY_DAY);

        name = (TextView) findViewById(R.id.name);
        name.setText(day.getName() == null ? "" : day.getName());
    }

    @Override
    public int getContentView() {
        return R.layout.activity_day_constructor;
    }

    @Override
    public void onClickFloatButton() {
        Intent intent = new Intent(this, ExerciseStoreActivity.class);
        startActivityForResult(intent, REQUEST_STORE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_STORE && resultCode == RESULT_OK) {
            ExerciseStore store = data.getParcelableExtra(ExerciseStoreActivity.KEY_EXERCISE);
            Exercise exercise = new Exercise();
            exercise.setStore(store);
            day.getExerciseList().add(exercise);
            adapter.notifyItemInserted(day.getExerciseList().size() - 1);
        } else if (requestCode == REQUEST_EXERCISE && resultCode == RESULT_OK) {
            Exercise exercise = data.getParcelableExtra(ExerciseConstructorActivity.KEY_EXERCISE);
            int position = data.getIntExtra(ExerciseConstructorActivity.KEY_POSITION, -1);
            day.getExerciseList().remove(position);
            day.getExerciseList().add(position, exercise);
            adapter.notifyItemChanged(position);
        }
    }

    private void onClickItem(Exercise exercise, int position) {
        Intent intent = new Intent(this, ExerciseConstructorActivity.class);
        intent.putExtra(ExerciseConstructorActivity.KEY_EXERCISE, exercise);
        intent.putExtra(ExerciseConstructorActivity.KEY_POSITION, position);
        startActivityForResult(intent, REQUEST_EXERCISE);
    }

    @Override
    public void onBackPressed() {
        day.setName(name.getText().toString());
        Intent intent = new Intent();
        intent.putExtra(KEY_DAY, day);
        intent.putExtra(KEY_POSITION, getIntent().getIntExtra(KEY_POSITION, -1));
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        View view;
        View detail;
        TextView approach;
        TextView reps;
        TextView value;

        ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            approach = (TextView) view.findViewById(R.id.approach);
            reps = (TextView) view.findViewById(R.id.reps);
            value = (TextView) view.findViewById(R.id.value);
            detail = view.findViewById(R.id.detail);
            this.view = view;
        }
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> implements RecyclerDragAndSwipe.Adapter {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(getLayoutInflater().inflate(R.layout.element_exercise, parent, false));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final Exercise exercise = day.getExerciseList().get(position);
            ExerciseStore store = exercise.getStore();

            holder.name.setText(store.getName());

            boolean showInfo = exercise.getApproachList().size() > 0;

            holder.approach.setVisibility(showInfo ? View.VISIBLE : View.GONE);
            holder.reps.setVisibility(showInfo ? View.VISIBLE : View.GONE);
            holder.value.setVisibility(showInfo ? View.VISIBLE : View.GONE);
            holder.detail.setVisibility(showInfo ? View.VISIBLE : View.GONE);

            holder.approach.setText("Подходов: " + exercise.getApproachList().size());
            holder.reps.setText("Повторений: " + exercise.getRepsString());
            holder.value.setText("Нагрузка: " + exercise.getValueString());

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItem(exercise, holder.getAdapterPosition());
                }
            });
        }

        @Override
        public int getItemCount() {
            return day.getExerciseList().size();
        }

        @Override
        public void onItemMove(int fromPosition, int toPosition) {
            RecyclerDragAndSwipe.swapData(day.getExerciseList(), fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onItemDismiss(int position) {
            day.getExerciseList().remove(position);
            notifyItemRemoved(position);
        }
    }
}
