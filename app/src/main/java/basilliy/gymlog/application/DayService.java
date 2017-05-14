package basilliy.gymlog.application;

import basilliy.gymlog.domain.entity.Day;
import io.realm.RealmList;

public class DayService extends Service<Day> {

    public DayService() {
        super(Day.class);
    }

    @Override
    public long getId(Day object) {
        return object.getId();
    }

    @Override
    public void setId(Day object, long id) {
        object.setId(id);
    }

    @Override
    public RealmList getInnerList(Day object) {
        return object.getExerciseList();
    }

    @Override
    public Service getInnerDataService() {
        return App.getExerciseService();
    }
}
