package basilliy.gymlog.presentation.programList;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import basilliy.gymlog.R;
import basilliy.gymlog.domain.entity.Program;
import basilliy.gymlog.presentation.utils.SecondActivity;

public class ProgramActivity extends SecondActivity {

    public static final String KEY_PROGRAM = "key_program";
    public static final String KEY_POSITION = "key_position";

    private Program program;
    private int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        program = getIntent().getParcelableExtra(KEY_PROGRAM);
        position = getIntent().getIntExtra(KEY_POSITION, -1);

        TextView name = (TextView) findViewById(R.id.name);
        name.setText(program.getName());

        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new DayAdapter(program.getDayList(), getLayoutInflater(), this));
    }

    @Override
    public int getContentView() {
        return R.layout.activity_program;
    }

    @Override
    public void onClickFloatButton() {}




}
