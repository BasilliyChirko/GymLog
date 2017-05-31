package basilliy.gymlog.application.service;

import java.util.Date;

import basilliy.gymlog.application.App;
import basilliy.gymlog.domain.entity.ActiveProgram;
import basilliy.gymlog.domain.entity.Program;
import basilliy.gymlog.domain.repository.Repository;
import io.realm.RealmResults;

public class ActiveProgramService {

    private Repository<ActiveProgram> repository = App.getRepository(ActiveProgram.class);

    public ActiveProgram get() {
        try {
            RealmResults<ActiveProgram> all = repository.findAll();
            if (all.size() > 0) {
                ActiveProgram activeProgram = all.get(0);
                if (activeProgram.getProgram() == null)
                    throw new Exception();
                return activeProgram;
            }
        } catch (Exception e) {
            remove();
        }
        return null;
    }

    public ActiveProgram set(Program program, Date dateStart, boolean changeable) {
        if (program.getId() <= 0) throw new RuntimeException("Program without id");
        ActiveProgram activeProgram = new ActiveProgram();
        activeProgram.setProgram(program);
        activeProgram.setDateStart(dateStart);
        activeProgram.setChangeable(changeable);

        repository.deleteAll();
        repository.persist(activeProgram);
        return get();
    }

    public void remove() {
        repository.deleteAll();
    }
}
