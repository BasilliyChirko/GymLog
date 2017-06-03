package basilliy.gymlog.domain.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import basilliy.gymlog.application.App;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ActiveProgram extends RealmObject {

    @PrimaryKey
    private long id = 13;
    private Program program;
    private Date dateStart;
    private boolean changeable;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isChangeable() {
        return changeable;
    }

    public void setChangeable(boolean changeable) {
        Realm realm = App.getRealm();
        realm.beginTransaction();
        this.changeable = changeable;
        realm.commitTransaction();
    }

    public Day getWorkDay(Calendar current) {
        current.set(Calendar.HOUR_OF_DAY, 0);
        current.set(Calendar.MINUTE, 0);
        current.set(Calendar.SECOND, 0);
        current.set(Calendar.MILLISECOND, 0);

        long diff = current.getTimeInMillis() - dateStart.getTime();
        if (diff < 0) return null;

        long countDay = diff / (1000 * 60 * 60 * 24);
        int number = (int) (countDay % program.getDayList().size());
        return program.getDayList().get(number);
    }

    public Date getWorkDate(Calendar current) {
        current.set(Calendar.HOUR_OF_DAY, 0);
        current.set(Calendar.MINUTE, 0);
        current.set(Calendar.SECOND, 0);
        current.set(Calendar.MILLISECOND, 0);

        long diff = current.getTimeInMillis() - dateStart.getTime();
        RealmList<Day> dayList = program.getDayList();

        if (diff < 0) {
            int i = 0;
            for (Day day : dayList) {
                if (day.getExerciseList().isEmpty()) i++;
                else break;
            }

            Calendar calendar = new GregorianCalendar();
            calendar.setTime(dateStart);
            calendar.add(Calendar.DAY_OF_MONTH, i);
            return calendar.getTime();
        }

        int number = (int) (diff / (1000 * 60 * 60 * 24) % dayList.size());

        int count = 0;
        m:
        for (int k = 0; k < 1; k++) {
            for (int i = number; i < dayList.size(); i++) {
                if (dayList.get(i).getExerciseList().isEmpty()) {
                    count++;
                } else break m;
            }
            for (int i = 0; i < number; i++) {
                if (dayList.get(i).getExerciseList().isEmpty()) {
                    count++;
                } else break m;
            }
        }

        current.add(Calendar.DAY_OF_MONTH, count);
        return current.getTime();
    }
}
