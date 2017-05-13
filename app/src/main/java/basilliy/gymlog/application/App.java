package basilliy.gymlog.application;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static App app;

    public static Context getContext() {
        return app.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
