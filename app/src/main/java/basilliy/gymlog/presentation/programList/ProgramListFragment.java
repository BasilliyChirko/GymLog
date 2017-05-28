package basilliy.gymlog.presentation.programList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import basilliy.gymlog.R;
import basilliy.gymlog.presentation.navigation.FragmentOnRoot;


public class ProgramListFragment extends FragmentOnRoot {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initRootActivityElement();
        View v = inflater.inflate(R.layout.fragment_program_list, container, false);
        return v;
    }

    @Override
    public View getToolbarContent(LayoutInflater inflater) {
        return inflater.inflate(R.layout.action_bar_program_list, null);
    }

    @Override
    public void initFloatButton(FloatingActionButton actionButton) {
        setFloatButtonVisible(false);
    }

    public static ProgramListFragment newInstance() {
        Bundle args = new Bundle();
        ProgramListFragment fragment = new ProgramListFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
