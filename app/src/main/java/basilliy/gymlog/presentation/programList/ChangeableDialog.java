package basilliy.gymlog.presentation.programList;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import basilliy.gymlog.R;

public class ChangeableDialog extends DialogFragment {

    private ChangeableDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext(), R.style.AppTheme_PickerDialog)
                .setMessage("Изменять нагрузку упражнений в зависимости от результата?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null)
                            listener.onSetChangeable(true);
                        dismiss();
                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null)
                            listener.onSetChangeable(false);
                        dismiss();
                    }
                })
                .create();
    }

    public static ChangeableDialog newInstance() {
        return new ChangeableDialog();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ChangeableDialogListener)
            listener = (ChangeableDialogListener) activity;
    }

    public interface ChangeableDialogListener {
        void onSetChangeable(boolean changeable);
    }
}
