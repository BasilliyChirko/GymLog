package basilliy.gymlog.presentation.programList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import basilliy.gymlog.R;


class ProgramListViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    TextView days;
    TextView daysWork;

    ProgramListViewHolder(View v) {
        super(v);
        name = (TextView) v.findViewById(R.id.name);
        days = (TextView) v.findViewById(R.id.day_count);
        daysWork = (TextView) v.findViewById(R.id.day_work);
    }
}
