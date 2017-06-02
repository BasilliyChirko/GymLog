package basilliy.gymlog.presentation.programActive;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class SetExerciseResultDialog extends DialogFragment {

    private static final String KEY_POSITION = "key_position";
    public static final int GOOD = 1;
    public static final int NORMAL = 2;
    public static final int BAD = 3;

    private OnSetExerciseResultListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setTitle("Результат упражнения")
                .setMessage("Как тяжело было выполнить упражение?")
                .setPositiveButton("Легко", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        done(GOOD);
                    }
                })
                .setNeutralButton("Нормально", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        done(NORMAL);
                    }
                })
                .setNegativeButton("Тяжело", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        done(BAD);
                    }
                })
                .create();
    }

    private void done(int result) {
        if (listener != null)
            listener.onSetExerciseResult(result, getArguments().getInt(KEY_POSITION));
        dismiss();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnSetExerciseResultListener)
            listener = (OnSetExerciseResultListener) activity;
    }

    public static SetExerciseResultDialog newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        SetExerciseResultDialog fragment = new SetExerciseResultDialog();
        fragment.setArguments(args);
        return fragment;
    }

    interface OnSetExerciseResultListener {
        void onSetExerciseResult(int result, int position);
    }

}
