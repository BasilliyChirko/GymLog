package basilliy.gymlog.presentation.navigation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class FragmentOnRoot extends Fragment implements RootActivity {

    private RootActivity activity;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof RootActivity) {
            this.activity = (RootActivity) activity;
        }
    }

    public void initRootActivityElement(){
        setToolbarContent(getToolbarContent());
        initFloatButton(getFloatButton());
    }

    public abstract View getToolbarContent();

    public abstract void initFloatButton(FloatingActionButton actionButton);

    @Override
    public void setToolbarContent(View view) {
        if (activity != null)
            activity.setToolbarContent(view);
    }

    @Override
    public FloatingActionButton getFloatButton() {
        if (activity != null)
            return activity.getFloatButton();
        return null;
    }

    @Override
    public void setFloatButtonVisible(boolean b) {
        if (activity != null)
            activity.setFloatButtonVisible(b);
    }
}
