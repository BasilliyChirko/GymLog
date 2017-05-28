package basilliy.gymlog.application;

import android.app.Application;
import android.content.Context;

import basilliy.gymlog.application.service.ApproachService;
import basilliy.gymlog.application.service.DayService;
import basilliy.gymlog.application.service.ExerciseService;
import basilliy.gymlog.application.service.MeasureService;
import basilliy.gymlog.application.service.ProgramService;
import basilliy.gymlog.application.service.Service;
import basilliy.gymlog.domain.entity.Approach;
import basilliy.gymlog.domain.entity.Day;
import basilliy.gymlog.domain.entity.Exercise;
import basilliy.gymlog.domain.entity.Measure;
import basilliy.gymlog.domain.entity.Program;
import basilliy.gymlog.domain.repository.Repository;
import basilliy.gymlog.utils.Config;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
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

    public static <T extends RealmObject> Repository<T> getRepository(Class<T> clazz) {
        return new Repository<>(clazz);
    }

    public static Service<Program> getProgramService() {
        return new ProgramService();
    }

    public static Service<Day> getDayService() {
        return new DayService();
    }

    public static Service<Exercise> getExerciseService() {
        return new ExerciseService();
    }

    public static Service<Approach> getApproachService() {
        return new ApproachService();
    }

    public static Service<Measure> getMeasureService() {
        return new MeasureService();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Realm.init(this);

        configureRealm();
        LoadData.load();
    }

    private void configureRealm() {
        Realm.setDefaultConfiguration(getRealmConfiguration());
    }

    private RealmConfiguration getRealmConfiguration() {
        return new RealmConfiguration.Builder()
                .name(Config.database.name)
                .schemaVersion(Config.database.version)
                .build();
    }



}
