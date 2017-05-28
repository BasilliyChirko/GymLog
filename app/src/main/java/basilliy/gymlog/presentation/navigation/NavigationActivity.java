package basilliy.gymlog.presentation.navigation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import basilliy.gymlog.R;
import basilliy.gymlog.presentation.exerciseList.ExerciseListFragment;
import basilliy.gymlog.presentation.measureList.MeasureListFragment;
import basilliy.gymlog.presentation.calendar.CalendarFragment;
import basilliy.gymlog.presentation.graph.GraphFragment;
import basilliy.gymlog.presentation.programActive.ProgramActiveFragment;
import basilliy.gymlog.presentation.programList.ProgramListFragment;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RootActivity {

    private BackPressedListener backPressedListener;

    private FrameLayout toolbarContent;
    private FloatingActionButton floatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar bar = getSupportActionBar();
        if (bar != null)
            bar.setDisplayShowTitleEnabled(false);

        toolbarContent = (FrameLayout) findViewById(R.id.toolbarContent);

        floatButton = (FloatingActionButton) findViewById(R.id.fab);


        //Настройка панели навигации
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (backPressedListener == null || !backPressedListener.onBackPressed()) {
                super.onBackPressed();
            }
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        switch (id) {
            case R.id.nav_program_active:
                fragment = ProgramActiveFragment.newInstance();
                break;
            case R.id.nav_program_list:
                fragment = ProgramListFragment.newInstance();
                break;
            case R.id.nav_exercise:
                fragment = ExerciseListFragment.newInstance();
                break;
            case R.id.nav_measure:
                fragment = MeasureListFragment.newInstance();
                break;
            case R.id.nav_calendar:
                fragment = CalendarFragment.newInstance();
                break;
            case R.id.nav_graph:
                fragment = GraphFragment.newInstance();
                break;
        }


        if (fragment != null) {
            if (fragment instanceof BackPressedListener) {
                backPressedListener = (BackPressedListener) fragment;
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, fragment)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setToolbarContent(View view) {
        toolbarContent.removeAllViews();
        if (view != null)
            toolbarContent.addView(view);
    }

    @Override
    public FloatingActionButton getFloatButton() {
        return floatButton;
    }

    @Override
    public void setFloatButtonVisible(boolean b) {
        // TODO: 14.05.2017 create animation
        floatButton.setVisibility(b ? View.VISIBLE : View.GONE);
    }
}
