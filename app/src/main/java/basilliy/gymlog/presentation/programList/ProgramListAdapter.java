package basilliy.gymlog.presentation.programList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import basilliy.gymlog.R;
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
    }

    @Override
    public int getItemCount() {
        if (data != null)
            return data.size();
        return 0;
    }
}
