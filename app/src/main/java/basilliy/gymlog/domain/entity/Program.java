package basilliy.gymlog.domain.entity;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Program extends RealmObject  {

    @PrimaryKey
    protected long id;
    private String name;
    private RealmList<Day> dayList = new RealmList<>();


    @Override
    public String toString() {
        return "Program{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dayList=" + dayList +
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

    public RealmList<Day> getDayList() {
        return dayList;
    }

    public void setDayList(RealmList<Day> dayList) {
        this.dayList = dayList;
    }
}
