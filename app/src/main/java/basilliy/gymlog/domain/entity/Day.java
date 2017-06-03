package basilliy.gymlog.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;

import basilliy.gymlog.domain.repository.ID;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Day extends RealmObject implements ID, Parcelable {

    @PrimaryKey
    protected long id;
    private String name;
    private RealmList<Exercise> exerciseList = new RealmList<>();

    public Day() {}

    protected Day(Parcel in) {
        id = in.readLong();
        name = in.readString();
        exerciseList = new RealmList<>();
        in.readTypedList(exerciseList, Exercise.CREATOR);
    }

    public static final Creator<Day> CREATOR = new Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel in) {
            return new Day(in);
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(RealmList<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    @Override
    public String toString() {
        return "CalendarDay{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", exerciseList=" + exerciseList +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeTypedList(exerciseList);
    }
}
