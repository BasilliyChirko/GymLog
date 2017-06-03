package basilliy.gymlog.domain.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ExerciseResult extends RealmObject {

    @PrimaryKey
    private long id;
    private ExerciseStore store;
    private long value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ExerciseStore getStore() {
        return store;
    }

    public void setStore(ExerciseStore store) {
        this.store = store;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
