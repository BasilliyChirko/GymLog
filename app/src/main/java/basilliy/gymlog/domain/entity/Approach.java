package basilliy.gymlog.domain.entity;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Approach extends RealmObject  {

    @PrimaryKey
    protected long id;
    private long reps;
    private long value;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getReps() {
        return reps;
    }

    public void setReps(long reps) {
        this.reps = reps;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Approach{" +
                "id=" + id +
                ", reps=" + reps +
                ", value=" + value +
                '}';
    }
}
