package basilliy.gymlog.application;

import basilliy.gymlog.domain.entity.Exercise;
import io.realm.RealmList;

public class ExerciseService extends Service<Exercise> {

    public ExerciseService() {
        super(Exercise.class);
    }

    @Override
    public long getId(Exercise object) {
        return object.getId();
    }

    @Override
    public void setId(Exercise object, long id) {
        object.setId(id);
    }

    @Override
    public RealmList getInnerList(Exercise object) {
        return object.getApproachList();
    }

    @Override
    public Service getInnerDataService() {
        return App.getApproachService();
    }
}
