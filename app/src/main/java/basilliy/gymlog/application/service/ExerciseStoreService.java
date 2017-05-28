package basilliy.gymlog.application.service;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

import basilliy.gymlog.application.App;
import basilliy.gymlog.domain.entity.Exercise;
import basilliy.gymlog.domain.entity.ExerciseStore;
import basilliy.gymlog.utils.Config;
import io.realm.Realm;
import io.realm.RealmResults;

public class ExerciseStoreService {
    private Class<ExerciseStore> clazz = ExerciseStore.class;

    public ExerciseStoreService() {}

    public RealmResults<ExerciseStore> getAll() {
        return App.getRepository(clazz).findAll();
    }

    public RealmResults<ExerciseStore> getByMusle(String muscle) {
        Realm realm = App.getRealm();
        realm.beginTransaction();
        RealmResults<ExerciseStore> results = realm.where(ExerciseStore.class).equalTo("targetMuscle", muscle).findAll();
        realm.commitTransaction();
        return results;
    }

    public void persist(ExerciseStore store) {
        if (store.getId() <= 0)
            store.setId(generateID());

        App.getRepository(clazz).persist(store);
    }

    private long generateID() {
        SharedPreferences pref = App.getContext().getSharedPreferences(Config.pref.name, Context.MODE_PRIVATE);
        String key = clazz.getName() + "_id";
        long id = pref.getLong(key, 1);
        pref.edit().putLong(key, id + 1).apply();
        return id;
    }

    public ArrayList<String> getMuscleList() {
        ArrayList<String> list = new ArrayList<>();


        RealmResults<ExerciseStore> stores = App.getRepository(clazz).findAll();

        m:
        for (ExerciseStore store : stores) {
            for (String muscle : list)
                if (store.getTargetMuscle().equals(muscle))
                    continue m;
            list.add(store.getTargetMuscle());
        }

        return list;
    }

}
