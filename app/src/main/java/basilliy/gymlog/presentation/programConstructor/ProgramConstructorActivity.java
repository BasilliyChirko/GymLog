package basilliy.gymlog.presentation.programConstructor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import basilliy.gymlog.R;
import basilliy.gymlog.domain.entity.Day;
import basilliy.gymlog.domain.entity.Exercise;
import basilliy.gymlog.domain.entity.Program;
import basilliy.gymlog.presentation.utils.RecyclerDragAndSwipe;
import basilliy.gymlog.utils.D;
import io.realm.RealmList;

public class ProgramConstructorActivity extends ConstructorActivity {

    public static final int REQUEST = 1001;
    private Program program = new Program();
    private RecyclerAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new RecyclerAdapter();
        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setItemAnimator(new DefaultItemAnimator());
        RecyclerDragAndSwipe.enable(list, adapter, true, true);
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
        }
    }

}
