package basilliy.gymlog.domain;

import basilliy.gymlog.application.App;
import basilliy.gymlog.utils.D;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ProgramRepositoryImpl implements ProgramRepository {
    @Override
    public Program find(long id) {
        Realm realm = App.getRealm();
        realm.beginTransaction();
        Program program = realm.where(Program.class).equalTo("id", id).findFirst();
        realm.commitTransaction();
        return program;
    }

    @Override
    public RealmResults<Program> findAll() {
        Realm realm = App.getRealm();
        realm.beginTransaction();
        RealmResults<Program> results = realm.where(Program.class).findAll();
        realm.commitTransaction();
        return results;
    }

    @Override
    public void update(Program program) {
        Realm realm = App.getRealm();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(program);
        realm.commitTransaction();
    }

    @Override
    public void insert(Program program) {
        Realm realm = App.getRealm();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(program);
        realm.commitTransaction();
    }

    @Override
    public void remove(Program program) {
        Realm realm = App.getRealm();
        realm.beginTransaction();
        realm.where(Program.class).equalTo("id", program.getId()).findFirst().deleteFromRealm();
        realm.commitTransaction();
    }

    @Override
    public long nextID(){
        Realm realm = App.getRealm();
        Number id = realm.where(Program.class).max("id");
        return ((long) id) + 1;
    }



}
