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
        Realm realm = App.getRealm();
        realm.beginTransaction();
        realm.where(clazz).equalTo("id", getID(object)).findFirst().deleteFromRealm();
        realm.commitTransaction();
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


    private long getID(T object) {
        String s = object.toString();
        s = s.substring(s.indexOf("id="));
        s = s.substring(0, s.indexOf(","));

        try {
            return Long.valueOf(s);
        } catch (Exception e) {
            throw new RuntimeException("Object should have param \"id=\" in toString()");
        }
    }
}
