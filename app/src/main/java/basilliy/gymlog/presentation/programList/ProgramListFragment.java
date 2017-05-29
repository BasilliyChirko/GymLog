package basilliy.gymlog.presentation.programList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import basilliy.gymlog.R;
import basilliy.gymlog.application.App;
import basilliy.gymlog.domain.entity.Day;
import basilliy.gymlog.domain.entity.Program;
import basilliy.gymlog.presentation.navigation.FragmentOnRoot;
import basilliy.gymlog.presentation.programConstructor.ProgramConstructorActivity;
import io.realm.RealmResults;


public class ProgramListFragment extends FragmentOnRoot {

    private static final int REQUEST_CONSTRUCTOR = 1322;
    private RealmResults<Program> data;
    private ProgramListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = App.getProgramService().getAll();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initRootActivityElement();
        return inflater.inflate(R.layout.fragment_program_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (data.isEmpty()) {
            view.findViewById(R.id.label_empty).setVisibility(View.VISIBLE);
        } else {
            RecyclerView list = (RecyclerView) view.findViewById(R.id.recycler_view);
            adapter = new ProgramListAdapter(data, getActivity().getLayoutInflater());
            list.setAdapter(adapter);
            list.setItemAnimator(new DefaultItemAnimator());
            list.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }

    @Override
    public View getToolbarContent(LayoutInflater inflater) {
        return inflater.inflate(R.layout.action_bar_program_list, null);
    }

    @Override
    public void initFloatButton(FloatingActionButton actionButton) {
        setFloatButtonVisible(true);
        actionButton.setImageResource(R.drawable.ic_add_white);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startConstructor();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static ProgramListFragment newInstance() {
        Bundle args = new Bundle();
        ProgramListFragment fragment = new ProgramListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void startConstructor() {
        Intent intent = new Intent(getContext(), ProgramConstructorActivity.class);
        getActivity().startActivityForResult(intent, REQUEST_CONSTRUCTOR);
    }

    private class ProgramListViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView days;
        TextView daysWork;

        ProgramListViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            days = (TextView) v.findViewById(R.id.day_count);
            daysWork = (TextView) v.findViewById(R.id.day_work);
        }
    }

    private class ProgramListAdapter extends RecyclerView.Adapter<ProgramListViewHolder> {

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
}
