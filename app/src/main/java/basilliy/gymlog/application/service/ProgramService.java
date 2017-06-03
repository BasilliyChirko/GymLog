package basilliy.gymlog.application.service;

import basilliy.gymlog.application.App;
import basilliy.gymlog.domain.entity.ActiveProgram;
import basilliy.gymlog.domain.entity.Program;
import io.realm.RealmList;
import io.realm.RealmObject;

public class ProgramService extends Service<Program> {

    public ProgramService() {
        super(Program.class);
    }

    @Override
    public <E extends RealmObject> void persist(Program program) {
        try {
            ActiveProgramService service = App.getActiveProgramService();
            if (service.get().getProgram().getId() == program.getId())
                service.remove();
        } catch (Exception ignored){}
        super.persist(program);
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
