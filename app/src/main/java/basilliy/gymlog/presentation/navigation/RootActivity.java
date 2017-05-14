package basilliy.gymlog.presentation.navigation;

import android.support.design.widget.FloatingActionButton;
import android.view.View;

public interface RootActivity {
    void setToolbarContent(View view);
    FloatingActionButton getFloatButton();
    void setFloatButtonVisible(boolean b);
}
