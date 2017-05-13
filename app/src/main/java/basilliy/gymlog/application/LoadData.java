package basilliy.gymlog.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import basilliy.gymlog.R;
import basilliy.gymlog.domain.entity.Measure;
import basilliy.gymlog.domain.repository.Repository;
import basilliy.gymlog.utils.Config;

public class LoadData {

    public static void load() {
        SharedPreferences preferences = App.getContext().getSharedPreferences(Config.pref.name, Context.MODE_PRIVATE);

        if (preferences.getBoolean(Config.pref.firstLoad, true)) {
            loadMeasure();
            loadExerciseStore();

            preferences.edit().putBoolean(Config.pref.firstLoad, false).apply();
        }
    }

    private static void loadMeasure() {
        Resources res = App.getContext().getResources();
        Repository<Measure> repository = App.getMeasureRepository();
        repository.deleteAll();

        addMeasure(repository, res, R.string.meter);
        addMeasure(repository, res, R.string.centimeter);
        addMeasure(repository, res, R.string.kilometer);
        addMeasure(repository, res, R.string.kilogram);
        addMeasure(repository, res, R.string.second);
    }

    private static void addMeasure(Repository<Measure> repository, Resources res,  int nameID) {
        Measure measure = new Measure();
        measure.setName(res.getString(nameID));
        measure.setId(repository.nextID());
        repository.persist(measure);
    }

    private static void loadExerciseStore() {

    }
}
