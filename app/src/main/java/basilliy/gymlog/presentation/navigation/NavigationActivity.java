package basilliy.gymlog.presentation.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import basilliy.gymlog.presentation.exerciseList.ExerciseStoreListFragment;
import basilliy.gymlog.presentation.calendar.CalendarFragment;
import basilliy.gymlog.presentation.graph.GraphFragment;
import basilliy.gymlog.presentation.programActive.ProgramActiveFragment;
import basilliy.gymlog.presentation.programList.ProgramListFragment;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RootActivity {

    public static final int PROGRAM_ACTIVE = 0;
    public static final int PROGRAM_LIST = 1;
    public static final int EXERCISE = 2;
    public static final int CALENDAR = 3;
    public static final int GRAPH = 4;

    private BackPressedListener backPressedListener;
    private Fragment fragment;
    private FrameLayout toolbarContent;
    private FloatingActionButton floatButton;
    private NavigationView navigationView;

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

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setPage(EXERCISE);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        setContentFragment(item.getItemId());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setPage(int number) {
        int id = 0;
        switch (number) {
            case PROGRAM_ACTIVE: id = R.id.nav_program_active; break;
            case PROGRAM_LIST: id = R.id.nav_program_list; break;
            case EXERCISE: id = R.id.nav_exercise; break;
            case CALENDAR: id = R.id.nav_calendar; break;
            case GRAPH: id = R.id.nav_graph; break;
        }
        navigationView.getMenu().getItem(number).setChecked(true);
        setContentFragment(id);
    }

    private void setContentFragment(int id) {
        fragment = null;

        switch (id) {
            case R.id.nav_program_active:
                fragment = ProgramActiveFragment.newInstance();
                break;
            case R.id.nav_program_list:
                fragment = ProgramListFragment.newInstance();
                break;
            case R.id.nav_exercise:
                fragment = ExerciseStoreListFragment.newInstance();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (fragment != null)
            fragment.onActivityResult(requestCode, resultCode, data);
    }
}
