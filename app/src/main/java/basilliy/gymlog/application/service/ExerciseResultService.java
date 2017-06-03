package basilliy.gymlog.application.service;

import basilliy.gymlog.domain.entity.ExerciseResult;
import io.realm.RealmList;

public class ExerciseResultService extends Service<ExerciseResult> {

    public ExerciseResultService() {
        super(ExerciseResult.class);
    }

    @Override
    public long getId(ExerciseResult object) {
        return object.getId();
    }

    @Override
    public void setId(ExerciseResult object, long id) {
        object.setId(id);
    }

    @Override
    public RealmList getInnerList(ExerciseResult object) {
        return null;
    }

    @Override
    public Service getInnerDataService() {
        return null;
    }
}
