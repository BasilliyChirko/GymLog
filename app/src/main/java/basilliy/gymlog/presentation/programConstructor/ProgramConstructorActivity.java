package basilliy.gymlog.presentation.programConstructor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import basilliy.gymlog.R;
import basilliy.gymlog.application.App;
import basilliy.gymlog.domain.entity.Day;
import basilliy.gymlog.domain.entity.Exercise;
import basilliy.gymlog.domain.entity.Program;
import basilliy.gymlog.presentation.utils.SecondActivity;
import basilliy.gymlog.presentation.utils.RecyclerDragAndSwipe;
import basilliy.gymlog.utils.D;

public class ProgramConstructorActivity extends SecondActivity {

    public static final String KEY_PROGRAM = "key_program";
    public static final String KEY_POSITION = "key_position";
    public static final int REQUEST = 1001;

    private Program program;
    private RecyclerAdapter adapter;
    private EditText name;
    private Boolean secondClose = false;
    private View labelEmpty;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new RecyclerAdapter();
        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setItemAnimator(new DefaultItemAnimator());
        RecyclerDragAndSwipe.enable(list, adapter, true, true);

        findViewById(R.id.done).setOnClickListener(onClickDone);
        name = (EditText) findViewById(R.id.name);
        labelEmpty = findViewById(R.id.label_empty);

        Intent intent = getIntent();
        if (intent.hasExtra(KEY_PROGRAM)) {
            program = intent.getParcelableExtra(KEY_PROGRAM);
        } else {
            program = new Program();
            program.getDayList().add(new Day());
            program.getDayList().add(new Day());
            program.getDayList().add(new Day());
        }

        name.setText(program.getName());
        checkEmpty();
    }

    View.OnClickListener onClickDone = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            program.setName(name.getText().toString());
            if (checkProgram(program)) {
                App.getProgramService().persist(program);
                Intent intent = new Intent();
                intent.putExtra(KEY_PROGRAM, program);
                if (getIntent().hasExtra(KEY_POSITION))
                    intent.putExtra(KEY_POSITION, getIntent().getIntExtra(KEY_POSITION, -1));
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    };

    private boolean checkProgram(Program program) {
        if (program.getDayList().size() == 0)
            finish();

        String name = program.getName();
        if (name == null || name.isEmpty()) {
            D.toast("Введи название тренировки");
            return false;
        }

        boolean b = false;
        for (Day day : program.getDayList())
            if (day.getExerciseList().size() != 0) {
                b = true;
                break;
            }
        if (!b) {
            D.toast("Только отдыхать - это не дело");
            return false;
        }

        for (Day day : program.getDayList())
            for (Exercise exercise : day.getExerciseList())
                if (exercise.getApproachList().size() == 0) {
                    String exName = exercise.getStore().getName();
                    exName = exName.length() > 20 ? exName.substring(0, 20) + ".." : exName;
                    D.toast("Установи подходы для День" + (program.getDayList().indexOf(day) + 1) + ": " + exName);
                    return false;
                }

        return true;
    }

    private void checkEmpty() {
        labelEmpty.setVisibility(program.getDayList().size() == 0 ? View.VISIBLE : View.GONE);
    }

    private void onClickItem(Day day, int position) {
        Intent intent = new Intent(this, DayConstructorActivity.class);
        intent.putExtra(DayConstructorActivity.KEY_DAY, day);
        intent.putExtra(DayConstructorActivity.KEY_POSITION, position);
        startActivityForResult(intent, REQUEST);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_program_constructor;
    }

    @Override
    public void onClickFloatButton() {
        program.getDayList().add(new Day());
        adapter.notifyItemInserted(program.getDayList().size() - 1);
        checkEmpty();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            Day day = data.getParcelableExtra(DayConstructorActivity.KEY_DAY);
            int position = data.getIntExtra(DayConstructorActivity.KEY_POSITION, -1);

            program.getDayList().remove(position);
            program.getDayList().add(position, day);
            adapter.notifyItemChanged(position);
            checkEmpty();
        }
    }

    @Override
    public void onBackPressed() {
        if (!secondClose) {
            Toast.makeText(this, "Нажмите еще раз для выхода из создания тренировки", Toast.LENGTH_SHORT).show();
            secondClose = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    secondClose = false;
                }
            }, 4000);
        } else {
            super.onBackPressed();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        View view;
        ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            name = ((TextView) itemView.findViewById(R.id.name));
        }
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> implements RecyclerDragAndSwipe.Adapter {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(getLayoutInflater().inflate(R.layout.element_day, parent, false));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final Day day = program.getDayList().get(position);
            if (!day.getExerciseList().isEmpty()) {
                String name = day.getName() == null ? "" : day.getName();
                holder.name.setText("День " + (position + 1) + ": " + name);
            } else holder.name.setText("Отдых");
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItem(day, holder.getAdapterPosition());
                }
            });
        }

        @Override
        public int getItemCount() {
            return program.getDayList().size();
        }

        @Override
        public void onItemMove(int fromPosition, int toPosition) {
            RecyclerDragAndSwipe.swapData(program.getDayList(), fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onItemDismiss(int position) {
            program.getDayList().remove(position);
            notifyItemRemoved(position);
            checkEmpty();
        }
    }

}
