package basilliy.gymlog.application;

import basilliy.gymlog.domain.entity.Approach;
import io.realm.RealmList;

public class ApproachService extends Service<Approach> {

    public ApproachService() {
        super(Approach.class);
    }

    @Override
    public long getId(Approach object) {
        return object.getId();
    }

    @Override
    public void setId(Approach object, long id) {
        object.setId(id);
    }

    @Override
    public RealmList getInnerList(Approach object) {
        return null;
    }

    @Override
    public Service getInnerDataService() {
        return null;
    }
}
