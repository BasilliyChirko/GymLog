package basilliy.gymlog.domain.entity;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Measure extends RealmObject{

    @PrimaryKey
    private long id;
    private String name;

    @Override
    public String toString() {
        return "Measure{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

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
}