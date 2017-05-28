package basilliy.gymlog.presentation.programConstructor.program;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import basilliy.gymlog.R;
import basilliy.gymlog.domain.entity.Day;
import basilliy.gymlog.presentation.utils.RecyclerDragAndSwipe;


public class ProgramConstructorFragment extends Fragment {

    ArrayList<Day> data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_program_constructor, container, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        RecyclerAdapter adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
        RecyclerDragAndSwipe.enable(recyclerView, adapter, true, true);

        data = new ArrayList<>();
        Day day;

        day = new Day();
        day.setName("ONe");
        data.add(day);
        day = new Day();
        day.setName("Two");
        data.add(day);
        day = new Day();
        day.setName("THree");
        data.add(day);
        day = new Day();
        day.setName("Four");
        data.add(day);
        return recyclerView;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        ViewHolder(View v) {
            super(v);
            name = ((TextView) v.findViewById(R.id.name));
        }
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> implements RecyclerDragAndSwipe.Adapter {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.element_day, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Day day = data.get(position);
            holder.name.setText(day.getName());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public void onItemMove(int fromPosition, int toPosition) {
            RecyclerDragAndSwipe.swapData(data, fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onItemDismiss(int position) {
            data.remove(position);
            notifyItemRemoved(position);
        }
    }
}
