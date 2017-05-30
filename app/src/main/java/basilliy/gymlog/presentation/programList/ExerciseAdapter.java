package basilliy.gymlog.presentation.programList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import basilliy.gymlog.R;
import basilliy.gymlog.domain.entity.Exercise;
import basilliy.gymlog.domain.entity.ExerciseStore;
import io.realm.RealmList;

class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private LayoutInflater inflater;
    private RealmList<Exercise> data;

    ExerciseAdapter(LayoutInflater inflater, RealmList<Exercise> data) {
        this.inflater = inflater;
        this.data = data;
    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExerciseViewHolder(inflater.inflate(R.layout.element_exercise_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(final ExerciseViewHolder holder, int position) {
        final Exercise exercise = data.get(position);
        ExerciseStore store = exercise.getStore();

        holder.name.setText(store.getName());
        holder.approach.setText("Подходов: " + exercise.getApproachList().size());
        holder.reps.setText("Повторения: " + exercise.getRepsString());
        holder.value.setText("Нагрузка: " + exercise.getValueString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView approach;
        TextView reps;
        TextView value;

        ExerciseViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            approach = (TextView) view.findViewById(R.id.approach);
            reps = (TextView) view.findViewById(R.id.reps);
            value = (TextView) view.findViewById(R.id.value);
        }
    }
}
