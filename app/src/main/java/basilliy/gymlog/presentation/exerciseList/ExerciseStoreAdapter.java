package basilliy.gymlog.presentation.exerciseList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import basilliy.gymlog.R;
import basilliy.gymlog.domain.entity.Exercise;
import basilliy.gymlog.domain.entity.ExerciseStore;
import io.realm.RealmResults;

class ExerciseStoreAdapter extends RecyclerView.Adapter<ExerciseStoreAdapter.ViewHolder> {

    private RealmResults<ExerciseStore> data;
    private boolean[] show;
    private ExerciseListListener listener;
    private LayoutInflater inflater;


    ExerciseStoreAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    void setListener(ExerciseListListener listener) {
        this.listener = listener;
    }

    public void setData(RealmResults<ExerciseStore> data) {
        this.data = data;
        show = new boolean[data.size()];
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.element_exercise_store, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ExerciseStore store = data.get(position);
        holder.name.setText(store.getName());
        holder.level.setText("Сложность: " + store.getLevelString());
        holder.inventory.setText("Инвентарь: " + store.getInventory());
        holder.targetMuscle.setText("Целевая мышца: " + store.getTargetMuscle());
        holder.involvedMuscle.setText("Вовлеченные мышцы: " + store.getInvolvedMuscle());

        holder.detail.setVisibility(show[holder.getAdapterPosition()] ? View.VISIBLE : View.GONE);

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                 listener.onClickMore(store);
            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClickItem(store);
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show[holder.getAdapterPosition()] = !show[holder.getAdapterPosition()];
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        View more;
        View add;
        View detail;
        TextView name;
        TextView level;
        TextView targetMuscle;
        TextView involvedMuscle;
        TextView inventory;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            name = (TextView) view.findViewById(R.id.name);
            level = (TextView) view.findViewById(R.id.level);
            targetMuscle = (TextView) view.findViewById(R.id.target_muscle);
            involvedMuscle = (TextView) view.findViewById(R.id.extra_muscle);
            inventory = (TextView) view.findViewById(R.id.inventory);
            more = view.findViewById(R.id.more);
            add = view.findViewById(R.id.add);
            detail = view.findViewById(R.id.detail);
        }
    }

    interface ExerciseListListener {
        void onClickMore(ExerciseStore exercise);
        void onClickItem(ExerciseStore exercise);
    }

}
