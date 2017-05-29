package basilliy.gymlog.presentation.programList;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import basilliy.gymlog.R;
import basilliy.gymlog.domain.entity.Day;
import io.realm.RealmList;

class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {

    private RealmList<Day> data;
    private LayoutInflater inflater;
    private Context context;

    DayAdapter(RealmList<Day> data, LayoutInflater inflater, Context context) {
        this.data = data;
        this.inflater = inflater;
        this.context = context;
    }

    @Override
    public DayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DayViewHolder(inflater.inflate(R.layout.element_day_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(DayViewHolder holder, int position) {
        Day day = data.get(position);
        if (day.getExerciseList().size() > 0) {
            holder.name.setText("День " + (position + 1) + ": " + day.getName());
            holder.list.setAdapter(new ExerciseAdapter(inflater, day.getExerciseList()));
            holder.list.setLayoutManager(new LinearLayoutManager(context));
        } else {
            holder.name.setText("Отдых");
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class DayViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        RecyclerView list;

        DayViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            list = (RecyclerView) v.findViewById(R.id.list);
        }
    }
}
