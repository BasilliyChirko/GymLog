package basilliy.gymlog.presentation.programList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import basilliy.gymlog.R;
import basilliy.gymlog.domain.entity.Day;
import basilliy.gymlog.domain.entity.Program;
import io.realm.RealmResults;

class ProgramListAdapter extends RecyclerView.Adapter<ProgramListViewHolder> {

    public RealmResults<Program> data;
    private LayoutInflater inflater;

    ProgramListAdapter(RealmResults<Program> data, LayoutInflater inflater) {
        this.data = data;
        this.inflater = inflater;
    }

    @Override
    public ProgramListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProgramListViewHolder(inflater.inflate(R.layout.element_program_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ProgramListViewHolder holder, int position) {
        Program program = data.get(position);

        holder.name.setText(program.getName());
        holder.days.setText("Продолжительность " + String.valueOf(program.getDayList().size())
                + " " + getDayName(program.getDayList().size()));

        int i = 0;
        for (Day day : program.getDayList())
            if (day.getExerciseList().size() > 0)
                i++;

        holder.daysWork.setText("Тренировок " + i + " " + getDayName(i));
    }

    private String getDayName(int i) {
        if (i == 1) return "день";
        if (i <= 4) return "дня";
        if (i == 11) return "дней";
        if (i % 10 == 1) return "день";
        return "дней";
    }

    @Override
    public int getItemCount() {
        if (data != null)
            return data.size();
        return 0;
    }
}
