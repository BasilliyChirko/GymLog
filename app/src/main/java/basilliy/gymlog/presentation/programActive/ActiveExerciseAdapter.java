package basilliy.gymlog.presentation.programActive;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import basilliy.gymlog.R;
import basilliy.gymlog.domain.entity.Day;
import basilliy.gymlog.domain.entity.Exercise;

class ActiveExerciseAdapter extends RecyclerView.Adapter<ActiveExerciseAdapter.ViewHolder>{

    private Day day;
    private LayoutInflater inflater;
    private Context context;
    private OnExerciseDoneListener listener;

    ActiveExerciseAdapter(Day day, LayoutInflater inflater, Context context, OnExerciseDoneListener listener) {
        this.day = day;
        this.inflater = inflater;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.element_exercise_active, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Exercise exercise = day.getExerciseList().get(position);
        holder.name.setText(exercise.getStore().getName());
        holder.list.setAdapter(new ActiveApproachAdapter(exercise, inflater));
        holder.list.setLayoutManager(new LinearLayoutManager(context));
        holder.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onExerciseDoneClick(exercise, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return day.getExerciseList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerView list;
        TextView name;
        TextView done;

        public ViewHolder(View v) {
            super(v);
            list = (RecyclerView) v.findViewById(R.id.list);
            name = (TextView) v.findViewById(R.id.name);
            done = (TextView) v.findViewById(R.id.done);
        }
    }

    public interface OnExerciseDoneListener {
        void onExerciseDoneClick(Exercise exercise, int position);
    }
}
