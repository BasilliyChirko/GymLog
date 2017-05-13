package basilliy.gymlog.domain;

import basilliy.gymlog.application.App;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Program extends RealmObject {

    @Override
    public String toString() {
        return "Program{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @PrimaryKey
    private long id;
    private String name;

    public static Program create(){
        return App.getRealm().createObject(Program.class);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
