package basilliy.gymlog.presentation.programList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import basilliy.gymlog.R;
import basilliy.gymlog.application.App;
import basilliy.gymlog.domain.entity.Program;
import basilliy.gymlog.presentation.navigation.FragmentOnRoot;
import basilliy.gymlog.presentation.programConstructor.ProgramConstructorActivity;
import io.realm.RealmResults;


public class ProgramListFragment extends FragmentOnRoot {

    private static final int REQUEST_CONSTRUCTOR = 1322;
    private RealmResults<Program> data;
    private ProgramListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = App.getProgramService().getAll();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initRootActivityElement();
        return inflater.inflate(R.layout.fragment_program_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (data.isEmpty()) {
            view.findViewById(R.id.label_empty).setVisibility(View.VISIBLE);
        } else {
            RecyclerView list = (RecyclerView) view.findViewById(R.id.recycler_view);
            adapter = new ProgramListAdapter(data, getActivity().getLayoutInflater());
            list.setAdapter(adapter);
            list.setItemAnimator(new DefaultItemAnimator());
            list.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        startConstructor();
    }

    @Override
    public View getToolbarContent(LayoutInflater inflater) {
        return inflater.inflate(R.layout.action_bar_program_list, null);
    }

    @Override
    public void initFloatButton(FloatingActionButton actionButton) {
        setFloatButtonVisible(true);
        actionButton.setImageResource(R.drawable.ic_add_white);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startConstructor();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static ProgramListFragment newInstance() {
        Bundle args = new Bundle();
        ProgramListFragment fragment = new ProgramListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void startConstructor() {
        Intent intent = new Intent(getContext(), ProgramConstructorActivity.class);
        getActivity().startActivityForResult(intent, REQUEST_CONSTRUCTOR);
    }
}
