package basilliy.gymlog.application;

import android.app.Application;
import android.content.Context;

import basilliy.gymlog.domain.ProgramRepository;
import basilliy.gymlog.domain.ProgramRepositoryImpl;
import basilliy.gymlog.utils.D;
import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.exceptions.RealmMigrationNeededException;

public class App extends Application {
    private static App app;

    public static Context getContext() {
        return app.getApplicationContext();
    }

    public static Realm getRealm() {
        try {
            return Realm.getDefaultInstance();
        } catch (RealmMigrationNeededException e) {
            Realm.deleteRealm(App.app.getRealmConfiguration());
            return Realm.getDefaultInstance();
        }
    }

    public static ProgramRepository getProgramRepository() {
        return new ProgramRepositoryImpl();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Realm.init(this);

        configureRealm();
    }

    private void configureRealm() {
        Realm.setDefaultConfiguration(getRealmConfiguration());
    }

    private RealmConfiguration getRealmConfiguration() {
        return new RealmConfiguration.Builder()
                .name("main_db")
                .schemaVersion(D.constant.DATABASE_VERSION)
                .build();
    }



}
