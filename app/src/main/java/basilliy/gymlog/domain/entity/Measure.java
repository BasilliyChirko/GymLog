package basilliy.gymlog.domain.entity;


import android.os.Parcel;
import android.os.Parcelable;

import basilliy.gymlog.domain.repository.ID;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Measure extends RealmObject implements ID, Parcelable {

    @PrimaryKey
    private long id;
    private String name;

    public Measure() {

    }

    protected Measure(Parcel in) {
        id = in.readLong();
        name = in.readString();
    }

    public static final Creator<Measure> CREATOR = new Creator<Measure>() {
        @Override
        public Measure createFromParcel(Parcel in) {
            return new Measure(in);
        }

        @Override
        public Measure[] newArray(int size) {
            return new Measure[size];
        }
    };

    @Override
    public String toString() {
        return "Measure{" +
                "id=" + id +
                ", name='" + name + '\'' +
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
    }
}
