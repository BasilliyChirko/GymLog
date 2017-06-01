package basilliy.gymlog.presentation.programActive;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import basilliy.gymlog.R;
import basilliy.gymlog.domain.entity.Approach;
import basilliy.gymlog.domain.entity.Exercise;

class ActiveApproachAdapter extends RecyclerView.Adapter<ActiveApproachAdapter.ViewHolder>{

    private Exercise data;
    private LayoutInflater inflater;

    ActiveApproachAdapter(Exercise data, LayoutInflater inflater) {
        this.data = data;
        this.inflater = inflater;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.element_approach_active, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Approach approach = data.getApproachList().get(position);
        holder.box.setText("Подход " + (position + 1) + ": " + (int) approach.getReps()
                + "x" + (int) approach.getValue() + " " + data.getStore().getMeasure().getName());
    }

    @Override
    public int getItemCount() {
        return data.getApproachList().size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox box;

        ViewHolder(View v) {
            super(v);
            box = (CheckBox) v.findViewById(R.id.box);
        }
    }
}
