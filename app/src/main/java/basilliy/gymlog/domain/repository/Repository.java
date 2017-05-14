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

    public void delete(T object) {
        if (object instanceof ID) {
            ID id = (ID) object;
            Realm realm = App.getRealm();
            realm.beginTransaction();
            realm.where(clazz).equalTo("id", id.getId()).findFirst().deleteFromRealm();
            realm.commitTransaction();
        } else {
            throw new RuntimeException(clazz.getName() + " should implement ID");
        }
    }

    public long nextID(){
        Realm realm = App.getRealm();
        Long id = (Long) realm.where(clazz).max("id");
        if (id == null)
            id = 0L;
        return id + 1;
    }

    public void deleteAll() {
        Realm realm = App.getRealm();
        realm.beginTransaction();
        App.getRealm().where(clazz).findAll().deleteAllFromRealm();
        realm.commitTransaction();
    }

}
