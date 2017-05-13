package basilliy.gymlog.application;

import android.app.Application;
import android.content.Context;

import basilliy.gymlog.domain.entity.ExerciseStore;
import basilliy.gymlog.domain.entity.Measure;
import basilliy.gymlog.domain.entity.Program;
import basilliy.gymlog.domain.repository.Repository;
import basilliy.gymlog.utils.D;
import io.realm.Realm;
import io.realm.RealmConfiguration;
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

    public static Repository<Measure> getMeasureRepository() {
        return new Repository<>(Measure.class);
    }

    public static Repository<Program> getProgramRepository() {
        return new Repository<>(Program.class);
    }

    public static Repository<ExerciseStore> getExerciseStoreRepository() {
        return new Repository<>(ExerciseStore.class);
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
