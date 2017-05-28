package basilliy.gymlog.application.service;

import basilliy.gymlog.domain.entity.Measure;
import io.realm.RealmList;

public class MeasureService extends Service<Measure> {

    public MeasureService() {
        super(Measure.class);
    }

    @Override
    public long getId(Measure object) {
        return object.getId();
    }

    @Override
    public void setId(Measure object, long id) {
        object.setId(id);
    }

    @Override
    public RealmList getInnerList(Measure object) {
        return null;
    }

    @Override
    public Service getInnerDataService() {
        return null;
    }
}
