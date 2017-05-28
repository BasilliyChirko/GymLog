package basilliy.gymlog.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;

import basilliy.gymlog.R;
import basilliy.gymlog.application.service.Service;
import basilliy.gymlog.domain.entity.Approach;
import basilliy.gymlog.domain.entity.Day;
import basilliy.gymlog.domain.entity.Exercise;
import basilliy.gymlog.domain.entity.Measure;
import basilliy.gymlog.domain.entity.Program;
import basilliy.gymlog.domain.repository.Repository;
import basilliy.gymlog.utils.Config;
import basilliy.gymlog.utils.D;
import io.realm.RealmResults;

public class LoadData {

    public static void load() {
        SharedPreferences preferences = App.getContext().getSharedPreferences(Config.pref.name, Context.MODE_PRIVATE);

        if (preferences.getBoolean(Config.pref.firstLoad, true)) {
            App.getRepository(Program.class).deleteAll();
            App.getRepository(Day.class).deleteAll();
            App.getRepository(Exercise.class).deleteAll();
            App.getRepository(Approach.class).deleteAll();
            App.getRepository(Measure.class).deleteAll();

            loadMeasure();
            loadExerciseStore();
            loadTestData();

            preferences.edit().putBoolean(Config.pref.firstLoad, false).apply();
        }
    }

    private static void loadMeasure() {
        Resources res = App.getContext().getResources();
        Service<Measure> service = App.getMeasureService();
        service.removeAll();

        addMeasure(service, res, R.string.meter);
        addMeasure(service, res, R.string.centimeter);
        addMeasure(service, res, R.string.kilometer);
        addMeasure(service, res, R.string.kilogram);
        addMeasure(service, res, R.string.second);
    }

    private static void addMeasure(Service<Measure> service, Resources res, int nameID) {
        Measure measure = new Measure();
        measure.setName(res.getString(nameID));
        service.persist(measure);
    }

    private static void loadExerciseStore() {

    }

    private static void loadTestData() {
        Service<Program> service = App.getProgramService();
        Program program;

        program = new Program();
        program.setName("Hay");
        service.persist(program);

        program = new Program();
        program.setName("Gay");
        service.persist(program);

        RealmResults<Program> all = service.getAll();
        all.isEmpty();
    }
}
