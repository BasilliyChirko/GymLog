package basilliy.gymlog.presentation.exerciseList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import basilliy.gymlog.R;
import basilliy.gymlog.domain.entity.ExerciseStore;
import basilliy.gymlog.presentation.utils.SecondActivity;

public class ExerciseStoreMoreActivity extends SecondActivity {

    public static final String KEY_EXERCISE = "key_exercise";
    public static final String KEY_DONE = "key_done";

    private boolean showDone = true;
    private ExerciseStore store;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        store = getIntent().getParcelableExtra(KEY_EXERCISE);
        showDone = getIntent().getBooleanExtra(KEY_DONE, true);

        View done = findViewById(R.id.done);
        ((TextView) findViewById(R.id.name)).setText(store.getName());
        ((TextView) findViewById(R.id.level)).setText("Сложность: " + store.getLevelString());
        ((TextView) findViewById(R.id.target_muscle)).setText("Целевая мышца: " + store.getTargetMuscle());
        ((TextView) findViewById(R.id.extra_muscle)).setText("Вовлеченные мышцы: " + store.getInvolvedMuscle());
        ((TextView) findViewById(R.id.inventory)).setText("Интвентарь: " + store.getInventory());
        ((TextView) findViewById(R.id.description)).setText("Описание: \n" + store.getDescription());
        ((TextView) findViewById(R.id.instruction)).setText("Инструкция: \n" + store.getInstruction());
        ((TextView) findViewById(R.id.advice)).setText("Советы: \n" + store.getAdvice());

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickFloatButton();
            }
        });

        done.setVisibility(showDone ? View.VISIBLE : View.GONE);
        findViewById(R.id.fab).setVisibility(showDone ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_exercise_store_more;
    }

    @Override
    public void onClickFloatButton() {
        Intent intent = new Intent();
        intent.putExtra(KEY_EXERCISE, store);
        setResult(RESULT_OK, intent);
        finish();
    }
}
