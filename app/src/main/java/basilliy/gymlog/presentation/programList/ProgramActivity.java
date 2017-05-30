package basilliy.gymlog.presentation.programList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import basilliy.gymlog.R;
import basilliy.gymlog.application.App;
import basilliy.gymlog.application.service.Service;
import basilliy.gymlog.domain.entity.Program;
import basilliy.gymlog.presentation.programConstructor.ProgramConstructorActivity;
import basilliy.gymlog.presentation.utils.SecondActivity;

public class ProgramActivity extends SecondActivity {

    public static final String KEY_PROGRAM = "key_program";
    private static final int REQUEST = 1244;

    private Program program;
    private TextView name;
    private DayAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        program = getIntent().getParcelableExtra(KEY_PROGRAM);
        name = (TextView) findViewById(R.id.name);
        name.setText(program.getName());

        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DayAdapter(program.getDayList(), getLayoutInflater(), this);
        list.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.element_program_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                editProgram(program);
                return true;
            case R.id.remove:
                removeProgram();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void removeProgram() {
        Service<Program> programService = App.getProgramService();
        programService.persist(program);
        programService.remove(program);

        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    private void editProgram(Program program) {
        Intent intent = new Intent(this, ProgramConstructorActivity.class);
        intent.putExtra(ProgramConstructorActivity.KEY_PROGRAM, program);
        startActivityForResult(intent, REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            program = data.getParcelableExtra(ProgramConstructorActivity.KEY_PROGRAM);
            adapter.setData(program.getDayList());
            name.setText(program.getName());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getContentView() {
        return R.layout.activity_program;
    }

    @Override
    public void onClickFloatButton() {

    }

}
