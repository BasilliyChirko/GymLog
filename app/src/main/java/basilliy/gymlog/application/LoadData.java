package basilliy.gymlog.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import basilliy.gymlog.R;
import basilliy.gymlog.application.service.Service;
import basilliy.gymlog.domain.entity.Measure;
import basilliy.gymlog.domain.repository.Repository;
import basilliy.gymlog.utils.Config;
import basilliy.gymlog.utils.D;

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
}
