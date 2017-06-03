package basilliy.gymlog.presentation.exerciseList;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import basilliy.gymlog.R;
import basilliy.gymlog.application.App;
import basilliy.gymlog.domain.entity.ExerciseStore;
import basilliy.gymlog.domain.entity.Measure;
import basilliy.gymlog.presentation.utils.RecyclerDragAndSwipe;
import basilliy.gymlog.presentation.utils.SecondActivity;
import basilliy.gymlog.utils.D;
import io.realm.RealmResults;

public class CreateExerciseActivity extends SecondActivity {

    private ExerciseStore store = new ExerciseStore();

    private EditText name;
    private EditText instruction;
    private EditText description;
    private EditText advice;

    private RecyclerAdapter inMuscleAdapter;
    private RecyclerAdapter inventoryAdapter;
    private AutoCompleteTextView inMuscle;
    private AutoCompleteTextView inventoryText;

    private RealmResults<Measure> measures;
    private ArrayList<String> muscle;
    private ArrayList<String> inventory;

    public void getData() {
        measures = App.getMeasureService().getAll();
        muscle = App.getExerciseStoreService().getMuscleList();
        inventory = App.getExerciseStoreService().getInventoryList();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();

        name = (EditText) findViewById(R.id.name);
        instruction = (EditText) findViewById(R.id.instruction);
        description = (EditText) findViewById(R.id.description);
        advice = (EditText) findViewById(R.id.advice);

        findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done();
            }
        });

        Spinner spinnerMeasure = (Spinner) findViewById(R.id.spinnerMeasure);
        spinnerMeasure.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, measures));
        spinnerMeasure.setOnItemSelectedListener(measureSelect);

        Spinner spinnerLevel = (Spinner) findViewById(R.id.spinnerLevel);
        spinnerLevel.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.level)));
        spinnerLevel.setOnItemSelectedListener(levelSelect);

        Spinner spinnerMuscle = (Spinner) findViewById(R.id.spinnerMuscle);
        spinnerMuscle.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, muscle));
        spinnerMuscle.setOnItemSelectedListener(muscleSelect);

        inMuscle = (AutoCompleteTextView) findViewById(R.id.edit_involved_muscle);
        inMuscle.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, muscle));
        inMuscle.setOnItemClickListener(inMuscleSelect);
        inMuscleAdapter = new RecyclerAdapter(getLayoutInflater());
        RecyclerView list = (RecyclerView) findViewById(R.id.listMuscle);
        list.setAdapter(inMuscleAdapter);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setItemAnimator(new DefaultItemAnimator());
        RecyclerDragAndSwipe.enable(list, inMuscleAdapter, true, true);

        inventoryText = (AutoCompleteTextView) findViewById(R.id.edit_inventory);
        inventoryText.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, inventory));
        inventoryText.setOnItemClickListener(inventorySelect);
        inventoryAdapter = new RecyclerAdapter(getLayoutInflater());
        list = (RecyclerView) findViewById(R.id.listInventory);
        list.setAdapter(inventoryAdapter);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setItemAnimator(new DefaultItemAnimator());
        RecyclerDragAndSwipe.enable(list, inventoryAdapter, true, true);
    }

    AdapterView.OnItemSelectedListener measureSelect = new ItemSelectListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            store.setMeasure(measures.get(position));
        }};

    AdapterView.OnItemSelectedListener levelSelect = new ItemSelectListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            store.setLevel(position + 1);
        }};

    AdapterView.OnItemSelectedListener muscleSelect = new ItemSelectListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            store.setTargetMuscle(muscle.get(position));
        }};

    AdapterView.OnItemClickListener inMuscleSelect = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            inMuscleAdapter.addData(inMuscle.getAdapter().getItem(position).toString());
            inMuscleAdapter.notifyItemInserted(inMuscleAdapter.getItemCount() - 1);
            inMuscle.setText("");
        }};

    AdapterView.OnItemClickListener inventorySelect = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            inventoryAdapter.addData(inventoryText.getAdapter().getItem(position).toString());
            inventoryAdapter.notifyItemInserted(inventoryAdapter.getItemCount() - 1);
            inventoryText.setText("");
        }};

    private void done() {
        if (name.getText().toString().isEmpty()) {
            D.toast("Введи название");
            return;
        }

        store.setName(name.getText().toString());
        store.setDescription(description.getText().toString());
        store.setInstruction(instruction.getText().toString());
        store.setAdvice(advice.getText().toString());

        ArrayList<String> data = inMuscleAdapter.getData();
        String muscle = "";
        for (String s : data) muscle += s + ", ";
        store.setInvolvedMuscle(data.isEmpty() ? "-" : muscle.substring(0, muscle.length() - 2));

        data = inventoryAdapter.getData();
        String string = "";
        for (String s : data) string += s + ", ";
        store.setInventory(data.isEmpty() ? "-" : string.substring(0, string.length() - 2));

        App.getExerciseStoreService().persist(store);

        finish();

    }

    @Override
    public int getContentView() {
        return R.layout.activity_create_exercise;
    }

    @Override
    public void onClickFloatButton() {
        done();
    }

    private abstract class ItemSelectListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    }
}
