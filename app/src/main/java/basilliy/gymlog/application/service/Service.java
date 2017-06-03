package basilliy.gymlog.application.service;

import android.content.Context;
import android.content.SharedPreferences;

import basilliy.gymlog.application.App;
import basilliy.gymlog.domain.entity.Exercise;
import basilliy.gymlog.utils.Config;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

public abstract class Service<T extends RealmObject> {
    private Class<T> clazz;

    public Service(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T getSinge(long id) {
        return App.getRepository(clazz).find(id);
    }

    public RealmResults<T> getAll() {
        return App.getRepository(clazz).findAll();
    }

    public <E extends RealmObject> void persist(T object) {
        long id = getId(object);

        if (id <= 0)
            setId(object, generateID());

        RealmList<E> innerList = getInnerList(object);
        if (innerList != null && innerList.size() > 0) {
            Service<E> service = getInnerDataService();
            for (E e : innerList) {
                service.persist(e);
            }
        }

        App.getRepository(clazz).persist(object);
    }

    private long generateID() {
        SharedPreferences pref = App.getContext().getSharedPreferences(Config.pref.name, Context.MODE_PRIVATE);
        String key = clazz.getName() + "_id";
        long id = pref.getLong(key, 1);
        pref.edit().putLong(key, id + 1).apply();
        return id;
    }

    public abstract long getId(T object);
    public abstract void setId(T object, long id);

    public <E extends RealmObject> void remove(T object) {
        try {
            App.getRepository(clazz).delete(getId(object));
        } catch (Exception e) {
            throw new RuntimeException("Exception in GenericService " + clazz.getSimpleName());
        }
    }

    public void removeAll() {
        for (T t : getAll()) remove(t);
    }

    public abstract RealmList getInnerList(T object);

    public abstract Service getInnerDataService();

}
