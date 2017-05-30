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
        RealmResults<ActiveProgram> all = repository.findAll();
        if (all.size() > 0) return all.get(0);
        return null;
    }

    public ActiveProgram set(Program program, Date dateStart) {
        if (program.getId() <= 0) throw new RuntimeException("Program without id");
        ActiveProgram activeProgram = new ActiveProgram();
        activeProgram.setProgram(program);
        activeProgram.setDateStart(dateStart);

        repository.deleteAll();
        repository.persist(activeProgram);
        return get();
    }

    public void remove() {
        repository.deleteAll();
    }
}
