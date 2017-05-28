package basilliy.gymlog.presentation.programList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import basilliy.gymlog.R;


class ProgramListViewHolder extends RecyclerView.ViewHolder {

    public TextView name;

    ProgramListViewHolder(View v) {
        super(v);
        name = (TextView) v.findViewById(R.id.name);
    }
}
