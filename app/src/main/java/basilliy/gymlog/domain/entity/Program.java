package basilliy.gymlog.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;

import basilliy.gymlog.domain.repository.ID;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Program extends RealmObject implements ID, Parcelable {

    @PrimaryKey
    protected long id;
    private String name;
    private RealmList<Day> dayList = new RealmList<>();

    public Program() {}


    public static final Creator<Program> CREATOR = new Creator<Program>() {
        @Override
        public Program createFromParcel(Parcel in) {
            return new Program(in);
        }

        @Override
        public Program[] newArray(int size) {
            return new Program[size];
        }
    };

    @Override
    public String toString() {
        return "Program{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dayList=" + dayList +
                '}';
    }

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

    public RealmList<Day> getDayList() {
        return dayList;
    }

    public void setDayList(RealmList<Day> dayList) {
        this.dayList = dayList;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    protected Program(Parcel in) {
        id = in.readLong();
        name = in.readString();
        dayList = new RealmList<>();
        in.readTypedList(dayList, Day.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeTypedList(dayList);
    }
}
