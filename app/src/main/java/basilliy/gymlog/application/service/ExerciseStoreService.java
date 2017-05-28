package basilliy.gymlog.application.service;

import java.util.ArrayList;

import basilliy.gymlog.application.App;
import basilliy.gymlog.domain.entity.ExerciseStore;
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

    public ArrayList<String> getMuscleList() {
        ArrayList<String> list = new ArrayList<>();

        Realm realm = App.getRealm();
        realm.beginTransaction();
        RealmResults<ExerciseStore> results = realm.where(ExerciseStore.class).distinct("targetMuscle");
        realm.commitTransaction();

        for (ExerciseStore result : results) list.add(result.getTargetMuscle());

        return list;
    }

    public void persist(ExerciseStore exerciseStore) {
        App.getRepository(clazz).persist(exerciseStore);
    }
}
