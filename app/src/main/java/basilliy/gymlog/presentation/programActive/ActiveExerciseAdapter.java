package basilliy.gymlog.presentation.programActive;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import basilliy.gymlog.R;
import basilliy.gymlog.domain.entity.Day;
import basilliy.gymlog.domain.entity.Exercise;
import basilliy.gymlog.utils.D;

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
        Exercise exercise = day.getExerciseList().get(position);

        if (exercise.isDone()) {
            holder.name.setText(D.stroke(exercise.getStore().getName()));
            holder.name.setPaintFlags(holder.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.list.setVisibility(View.GONE);
            holder.done.setVisibility(View.GONE);
        } else {
            holder.name.setText(exercise.getStore().getName());
            holder.list.setVisibility(View.VISIBLE);
            holder.list.setAdapter(new ActiveApproachAdapter(exercise, inflater));
            holder.list.setLayoutManager(new LinearLayoutManager(context));
            holder.done.setVisibility(View.VISIBLE);
            holder.done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onExerciseDoneClick(holder.getAdapterPosition());
                }
            });
        }
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

    interface OnExerciseDoneListener {
        void onExerciseDoneClick(int position);
    }
}
