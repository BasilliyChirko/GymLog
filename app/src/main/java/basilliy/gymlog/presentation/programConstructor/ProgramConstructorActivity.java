package basilliy.gymlog.presentation.programConstructor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

import basilliy.gymlog.R;
import basilliy.gymlog.presentation.programConstructor.program.ProgramConstructorFragment;

public class ProgramConstructorActivity extends AppCompatActivity {

    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_constructor);
        setResult(RESULT_CANCELED);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar bar = getSupportActionBar();
        if (bar != null)
            bar.setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ArrayList<Fragment> data = new ArrayList<>();
        data.add(new ProgramConstructorFragment());

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new ProgramConstructorPager(getSupportFragmentManager(), data));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return true;
    }

}
