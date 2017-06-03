package basilliy.gymlog.presentation.programActive;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

import basilliy.gymlog.R;

public class SetExerciseResultDialog extends DialogFragment {

    private static final String KEY_POSITION = "key_position";
    public static final int GOOD = 1;
    public static final int NORMAL = 2;
    public static final int BAD = 3;

    private OnSetExerciseResultListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_set_exercise_result, null, false);

        v.findViewById(R.id.good).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done(GOOD);
            }
        });

        v.findViewById(R.id.normal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done(NORMAL);
            }
        });

        v.findViewById(R.id.bad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done(BAD);
            }
        });

        return new AlertDialog.Builder(getContext()).setView(v).create();
    }

    private void done(int result) {
        if (listener != null)
            listener.onSetExerciseResult(result, getArguments().getInt(KEY_POSITION));
        dismiss();
    }

    public static SetExerciseResultDialog newInstance(int position, OnSetExerciseResultListener listener) {
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        SetExerciseResultDialog fragment = new SetExerciseResultDialog();
        fragment.listener = listener;
        fragment.setArguments(args);
        return fragment;
    }

    interface OnSetExerciseResultListener {
        void onSetExerciseResult(int result, int position);
    }

}
