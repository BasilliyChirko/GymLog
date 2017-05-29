package basilliy.gymlog.presentation.programList;

import android.app.Activity;
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

    private static final int REQUEST_CREATE = 1322;
    private static final int REQUEST_EDIT = 5212;
    private ArrayList<Program> data;
    private ProgramListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new ArrayList<>();
        RealmResults<Program> all = App.getProgramService().getAll();
        data.addAll(all);
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
            adapter = new ProgramListAdapter();
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
                createNewProgram();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CREATE && resultCode == Activity.RESULT_OK) {
            Program program = data.getParcelableExtra(ProgramConstructorActivity.KEY_PROGRAM);
            this.data.add(program);
            adapter.notifyItemInserted(this.data.size() - 1);
        } else if (requestCode == REQUEST_EDIT && resultCode == Activity.RESULT_OK) {
            Program program = data.getParcelableExtra(ProgramConstructorActivity.KEY_PROGRAM);
            int position = data.getIntExtra(ProgramConstructorActivity.KEY_POSITION, -1);
            this.data.remove(position);
            this.data.add(position, program);
            adapter.notifyItemChanged(position);
        }
    }

    private void showPopup(View view, final Program program, final int position) {
        PopupMenu popup = new PopupMenu(getContext(), view);
        popup.inflate(R.menu.element_program_list);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit:
                        editProgram(program, position);
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
        getActivity().startActivityForResult(intent, REQUEST_CREATE);
    }

    private void removeProgram(Program program, int position) {
        data.remove(position);
        App.getProgramService().remove(program);
        adapter.notifyItemRemoved(position);
    }

    private void editProgram(Program program, int position) {
        Intent intent = new Intent(getContext(), ProgramConstructorActivity.class);
        intent.putExtra(ProgramConstructorActivity.KEY_PROGRAM, program);
        intent.putExtra(ProgramConstructorActivity.KEY_POSITION, position);
        getActivity().startActivityForResult(intent, REQUEST_EDIT);
    }

    private class ProgramListViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView days;
        TextView daysWork;
        View more;

        ProgramListViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            days = (TextView) v.findViewById(R.id.day_count);
            daysWork = (TextView) v.findViewById(R.id.day_work);
            more = v.findViewById(R.id.more);
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
