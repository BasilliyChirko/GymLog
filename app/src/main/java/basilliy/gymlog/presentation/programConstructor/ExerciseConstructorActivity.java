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
import basilliy.gymlog.domain.entity.Exercise;
import basilliy.gymlog.presentation.utils.SecondActivity;
import basilliy.gymlog.presentation.utils.RecyclerDragAndSwipe;

public class ExerciseConstructorActivity extends SecondActivity implements ApproachConstructorDialog.OnApproachListener {

    public static final String KEY_EXERCISE = "key_exercise";
    public static final String KEY_POSITION = "key_position";

    private Exercise exercise;
    private RecyclerAdapter adapter;
    private View labelEmpty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new RecyclerAdapter();
        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setItemAnimator(new DefaultItemAnimator());
        RecyclerDragAndSwipe.enable(list, adapter, true, true);

        exercise = getIntent().getParcelableExtra(KEY_EXERCISE);
        ((TextView) findViewById(R.id.name)).setText(exercise.getStore().getName());
        labelEmpty = findViewById(R.id.label_empty);
        checkEmpty();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_exercise_constructor;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(KEY_EXERCISE, exercise);
        intent.putExtra(KEY_POSITION, getIntent().getIntExtra(KEY_POSITION, -1));
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    public void onClickFloatButton() {
        ApproachConstructorDialog
                .newInstance(exercise.getStore().getMeasure())
                .show(getSupportFragmentManager(), "tag");
    }

    private void checkEmpty() {
        labelEmpty.setVisibility(exercise.getApproachList().size() == 0 ? View.VISIBLE : View.GONE);
    }

    private void onClickItem(Approach approach, int position) {
        ApproachConstructorDialog
                .newInstance(approach, position, exercise.getStore().getMeasure())
                .show(getSupportFragmentManager(), "tag");
    }

    @Override
    public void onApproach(Approach approach, int position) {
        if (position == -1) {
            exercise.getApproachList().add(approach);
            adapter.notifyItemInserted(exercise.getApproachList().size() - 1);
        } else {
            exercise.getApproachList().remove(position);
            exercise.getApproachList().add(position, approach);
            adapter.notifyItemChanged(position);
        }
        checkEmpty();
    }


    private class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        View view;

        ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            this.view = view;
        }
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> implements RecyclerDragAndSwipe.Adapter {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(getLayoutInflater().inflate(R.layout.element_approach, parent, false));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final Approach approach = exercise.getApproachList().get(position);

            String text =
                    "Подход " + (position + 1) + ": " +
                    approach.getReps() + "x" +  approach.getValue()
                    + " " + exercise.getStore().getMeasure().getName();
            holder.name.setText(text);

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItem(approach, holder.getAdapterPosition());
                }
            });
        }

        @Override
        public int getItemCount() {
            return exercise.getApproachList().size();
        }

        @Override
        public void onItemMove(int fromPosition, int toPosition) {
            RecyclerDragAndSwipe.swapData(exercise.getApproachList(), fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onItemDismiss(int position) {
            exercise.getApproachList().remove(position);
            notifyItemRemoved(position);
            checkEmpty();
        }
    }
}
