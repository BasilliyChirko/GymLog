package basilliy.gymlog.presentation.programActive;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import basilliy.gymlog.presentation.navigation.FragmentOnRoot;


public class ProgramActiveFragment extends FragmentOnRoot {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initRootActivityElement();
        return null;
    }

    @Override
    public View getToolbarContent() {
        return null;
    }

    @Override
    public void initFloatButton(FloatingActionButton actionButton) {
        actionButton.setVisibility(View.GONE);
    }

    public static ProgramActiveFragment newInstance() {
        Bundle args = new Bundle();
        ProgramActiveFragment fragment = new ProgramActiveFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
