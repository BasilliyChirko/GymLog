package basilliy.gymlog.domain.entity;


import basilliy.gymlog.domain.repository.ID;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Exercise extends RealmObject implements ID {

    @PrimaryKey
    protected long id;private ExerciseStore store;
    private RealmList<Approach> approachList = new RealmList<>();


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

    public RealmList<Approach> getApproachList() {
        return approachList;
    }

    public void setApproachList(RealmList<Approach> approachList) {
        this.approachList = approachList;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", store=" + store +
                ", approachList=" + approachList +
                '}';
    }
}
