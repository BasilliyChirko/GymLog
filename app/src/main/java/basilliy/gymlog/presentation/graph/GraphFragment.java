package basilliy.gymlog.presentation.graph;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.eazegraph.lib.charts.ValueLineChart;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import basilliy.gymlog.R;
import basilliy.gymlog.application.App;
import basilliy.gymlog.domain.entity.ExerciseResult;
import basilliy.gymlog.presentation.navigation.FragmentOnRoot;
import basilliy.gymlog.utils.D;


public class GraphFragment extends FragmentOnRoot {

    private Map<String, ArrayList<ExerciseResult>> mapResult;
    private ValueLineChart chart;
    private Spinner spinner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapResult = App.getExerciseResultService().getMapResult();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initRootActivityElement();
        return inflater.inflate(R.layout.fragment_graph, container, false);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        chart = (ValueLineChart) v.findViewById(R.id.chart);
        spinner = (Spinner) v.findViewById(R.id.spinner);

        setSpinnerContent();
    }

    private void setSpinnerContent() {
        if (mapResult != null) {
            Set<String> strings = mapResult.keySet();
            ArrayList<String> names = new ArrayList<>();
            for (String s : strings) names.add(s);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, names);

            spinner.setAdapter(adapter);
            spinner.setOnItemClickListener(itemClickListener);
        }
    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String item = (String) parent.getSelectedItem();
            paintChart(mapResult.get(item));
        }
    };



    private void paintChart(ArrayList<ExerciseResult> list) {

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
