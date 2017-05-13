package basilliy.gymlog.utils;

import android.util.Log;

import basilliy.gymlog.application.App;
import basilliy.gymlog.domain.entity.Program;
import io.realm.Realm;

public final class D {

    public static final String TAG = "GymLog";

    public static void clearDatabase() {
        Realm realm = App.getRealm();
        realm.beginTransaction();
        realm.where(Program.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();
    }

    public static void log(Object object) {
        if (object == null) {
            Log.d(TAG, "null");
        } else {
            Log.d(TAG, String.valueOf(object));
        }
    }
}
