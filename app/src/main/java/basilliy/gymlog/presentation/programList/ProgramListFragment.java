package basilliy.gymlog.presentation.programList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import basilliy.gymlog.R;
import basilliy.gymlog.application.App;
import basilliy.gymlog.domain.entity.Day;
import basilliy.gymlog.domain.entity.Program;
import basilliy.gymlog.presentation.navigation.FragmentOnRoot;
import basilliy.gymlog.presentation.programConstructor.ProgramConstructorActivity;
import io.realm.RealmResults;


public class ProgramListFragment extends FragmentOnRoot {

    private ArrayList<Program> data;
    private ProgramListAdapter adapter;
    private boolean flag;
    private View label;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new ArrayList<>();
        RealmResults<Program> all = App.getProgramService().getAll();
        data.addAll(all);
        flag = true;
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
            label = view.findViewById(R.id.label_empty);
            label.setVisibility(View.VISIBLE);
        } else {
            RecyclerView list = (RecyclerView) view.findViewById(R.id.recycler_view);
            adapter = new ProgramListAdapter();
            list.setAdapter(adapter);
            list.setItemAnimator(new DefaultItemAnimator());
            list.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!flag) {
            data = new ArrayList<>();
            RealmResults<Program> all = App.getProgramService().getAll();
            data.addAll(all);
            adapter.notifyDataSetChanged();

            if (data.isEmpty()) label.setVisibility(View.VISIBLE);
        }
        flag = false;
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
                createNewProgram();
            }
        });
    }

    private void showPopup(View view, final Program program, final int position) {
        PopupMenu popup = new PopupMenu(getContext(), view);
        popup.inflate(R.menu.element_program_list);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit:
                        editProgram(program);
                        return true;
                    case R.id.remove:
                        removeProgram(program, position);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }

    public static ProgramListFragment newInstance() {
        Bundle args = new Bundle();
        ProgramListFragment fragment = new ProgramListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void createNewProgram() {
        Intent intent = new Intent(getContext(), ProgramConstructorActivity.class);
        getActivity().startActivity(intent);
    }

    private void removeProgram(Program program, int position) {
        data.remove(position);
        App.getProgramService().remove(program);
        adapter.notifyItemRemoved(position);
    }

    private void editProgram(Program program) {
        Intent intent = new Intent(getContext(), ProgramConstructorActivity.class);
        intent.putExtra(ProgramConstructorActivity.KEY_PROGRAM, program);
        getActivity().startActivity(intent);
    }

    private void openProgram(Program program) {
        Intent intent = new Intent(getContext(), ProgramActivity.class);
        intent.putExtra(ProgramActivity.KEY_PROGRAM, program);
        getActivity().startActivity(intent);
    }

    private class ProgramListViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView days;
        TextView daysWork;
        View more;
        View view;

        ProgramListViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            days = (TextView) v.findViewById(R.id.day_count);
            daysWork = (TextView) v.findViewById(R.id.day_work);
            more = v.findViewById(R.id.more);
            view = v;
        }
    }

    private class ProgramListAdapter extends RecyclerView.Adapter<ProgramListViewHolder> {

        @Override
        public ProgramListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ProgramListViewHolder(LayoutInflater.from(getContext())
                    .inflate(R.layout.element_program_list, parent, false));
        }

        @Override
        public void onBindViewHolder(final ProgramListViewHolder holder, int position) {
            final Program program = data.get(position);

            holder.name.setText(program.getName());
            holder.days.setText("Продолжительность " + String.valueOf(program.getDayList().size())
                    + " " + getDayName(program.getDayList().size()));

            int i = 0;
            for (Day day : program.getDayList())
                if (day.getExerciseList().size() > 0)
                    i++;

            holder.daysWork.setText("Тренировок " + i + " " + getDayName(i));
            holder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopup(holder.more, program, holder.getAdapterPosition());
                }
            });

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openProgram(program);
                }
            });
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
