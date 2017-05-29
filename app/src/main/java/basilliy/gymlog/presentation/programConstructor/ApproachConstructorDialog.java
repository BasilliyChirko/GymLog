package basilliy.gymlog.presentation.programConstructor;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import basilliy.gymlog.R;
import basilliy.gymlog.domain.entity.Approach;
import basilliy.gymlog.domain.entity.Measure;

public class ApproachConstructorDialog extends DialogFragment {

    public static final String KEY_APPROACH = "key_approach";
    public static final String KEY_MEASURE = "key_measure";
    public static final String KEY_POSITION = "key_position";

    private OnApproachListener listener;

    private EditText reps;
    private EditText value;
    private Approach approach;
    private int position;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();

        Measure measure = args.getParcelable(KEY_MEASURE);
        approach = args.containsKey(KEY_APPROACH) ? (Approach) args.getParcelable(KEY_APPROACH) : new Approach();
        position = args.containsKey(KEY_POSITION) ? args.getInt(KEY_POSITION) : -1;

        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_approach_constructor, null);
        reps = (EditText) v.findViewById(R.id.reps);
        value = (EditText) v.findViewById(R.id.value);

        ((TextView) v.findViewById(R.id.measure)).setText(measure.getName());
        v.findViewById(R.id.done).setOnClickListener(onClickDone);

        return new AlertDialog.Builder(getContext()).setView(v).create();
    }

    View.OnClickListener onClickDone = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (listener != null) {
                approach.setValue(Integer.parseInt(value.getText().toString()));
                approach.setReps(Integer.parseInt(reps.getText().toString()));
                listener.onApproach(approach, position);
            }
            dismiss();
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnApproachListener)
            listener = (OnApproachListener) activity;
    }

    public static ApproachConstructorDialog newInstance(Approach approach, int position, Measure measure) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_APPROACH, approach);
        args.putParcelable(KEY_MEASURE, measure);
        args.putInt(KEY_POSITION, position);
        ApproachConstructorDialog fragment = new ApproachConstructorDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public static ApproachConstructorDialog newInstance(Measure measure) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_MEASURE, measure);
        ApproachConstructorDialog fragment = new ApproachConstructorDialog();
        fragment.setArguments(args);
        return fragment;
    }

    interface OnApproachListener {
        void onApproach(Approach approach, int position);
    }
}
