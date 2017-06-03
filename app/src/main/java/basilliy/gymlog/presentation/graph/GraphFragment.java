package basilliy.gymlog.presentation.graph;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Map;

import basilliy.gymlog.R;
import basilliy.gymlog.application.App;
import basilliy.gymlog.domain.entity.ExerciseResult;
import basilliy.gymlog.presentation.navigation.FragmentOnRoot;
import basilliy.gymlog.utils.D;


public class GraphFragment extends FragmentOnRoot {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initRootActivityElement();
        Map<String, ArrayList<ExerciseResult>> mapResult = App.getExerciseResultService().getMapResult();
        return null;
    }

    @Override
    public View getToolbarContent(LayoutInflater inflater) {
        return inflater.inflate(R.layout.action_bar_graph, null, false);
    }

    @Override
    public void initFloatButton(FloatingActionButton actionButton) {
        setFloatButtonVisible(false);
    }

    public static GraphFragment newInstance() {
        Bundle args = new Bundle();
        GraphFragment fragment = new GraphFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
