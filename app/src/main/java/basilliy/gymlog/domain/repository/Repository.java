package basilliy.gymlog.domain.repository;

import basilliy.gymlog.application.App;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class Repository<T extends RealmObject> {

    private Class<T> clazz;

    public Repository(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T find(long id) {
        return App.getRealm().where(clazz).equalTo("id", id).findFirst();
    }

    public RealmResults<T> findAll() {
        return App.getRealm().where(clazz).findAll();
    }

    public void persist(T object) {
        Realm realm = App.getRealm();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(object);
        realm.commitTransaction();
    }

    public void delete(long id) {
        Realm realm = App.getRealm();
        realm.beginTransaction();
        realm.where(clazz).equalTo("id", id).findFirst().deleteFromRealm();
        realm.commitTransaction();
    }

    public void deleteAll() {
        Realm realm = App.getRealm();
        realm.beginTransaction();
        App.getRealm().where(clazz).findAll().deleteAllFromRealm();
        realm.commitTransaction();
    }

}
