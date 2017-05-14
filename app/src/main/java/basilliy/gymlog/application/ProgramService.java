package basilliy.gymlog.application;

import basilliy.gymlog.domain.entity.Program;
import io.realm.RealmList;

public class ProgramService extends Service<Program> {

    public ProgramService() {
        super(Program.class);
    }

    @Override
    public long getId(Program object) {
        return object.getId();
    }

    @Override
    public void setId(Program object, long id) {
        object.setId(id);
    }

    @Override
    public RealmList getInnerList(Program object) {
        return object.getDayList();
    }

    @Override
    public Service getInnerDataService() {
        return App.getDayService();
    }
}
