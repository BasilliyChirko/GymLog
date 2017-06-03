package basilliy.gymlog.presentation.exerciseList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import basilliy.gymlog.presentation.navigation.FragmentOnRoot;


public class ExerciseStoreListFragment extends FragmentOnRoot {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initRootActivityElement();
        return null;
    }

    @Override
    public View getToolbarContent(LayoutInflater inflater) {
        return null;
    }

    @Override
    public void initFloatButton(FloatingActionButton actionButton) {
        setFloatButtonVisible(false);
    }

    public static ExerciseStoreListFragment newInstance() {
        Bundle args = new Bundle();
        ExerciseStoreListFragment fragment = new ExerciseStoreListFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
