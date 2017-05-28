package basilliy.gymlog.presentation.programConstructor;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import basilliy.gymlog.R;
import basilliy.gymlog.application.App;

public class MuscleDropDownAdapter extends ArrayAdapter<String> {


    public MuscleDropDownAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setBackgroundResource(R.color.colorPrimary);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setTextColor(Color.WHITE);
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setBackgroundColor(Color.WHITE);
            textView.setTextColor(App.getContext().getResources().getColor(R.color.cardview_dark_background));
        }
        return view;
    }
}
