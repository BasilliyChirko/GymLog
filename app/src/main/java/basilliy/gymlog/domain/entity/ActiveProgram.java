package basilliy.gymlog.domain.entity;

import java.util.Date;

import io.realm.RealmObject;

public class ActiveProgram extends RealmObject {
    private Program program;
    private Date dateStart;

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }
}
