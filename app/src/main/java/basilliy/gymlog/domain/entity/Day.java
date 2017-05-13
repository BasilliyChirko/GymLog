package basilliy.gymlog.domain.entity;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Day extends RealmObject {

    @PrimaryKey
    protected long id;private String name;
    private RealmList<Exercise> exerciseList = new RealmList<>();


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(RealmList<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    @Override
    public String toString() {
        return "Day{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", exerciseList=" + exerciseList +
                '}';
    }
}
