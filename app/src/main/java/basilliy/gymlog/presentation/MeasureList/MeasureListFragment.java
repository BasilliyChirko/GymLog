package basilliy.gymlog.presentation.MeasureList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import basilliy.gymlog.presentation.navigation.FragmentOnRoot;


public class MeasureListFragment extends FragmentOnRoot {

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
        setFloatButtonVisible(false);
    }

    public static MeasureListFragment newInstance() {
        Bundle args = new Bundle();
        MeasureListFragment fragment = new MeasureListFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
