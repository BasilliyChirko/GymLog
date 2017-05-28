package basilliy.gymlog.domain.entity;


import android.os.Parcel;
import android.os.Parcelable;

import basilliy.gymlog.domain.repository.ID;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Approach extends RealmObject implements ID, Parcelable {

    @PrimaryKey
    protected long id;
    private long reps;
    private long value;

    public Approach() {}


    protected Approach(Parcel in) {
        id = in.readLong();
        reps = in.readLong();
        value = in.readLong();
    }

    public static final Creator<Approach> CREATOR = new Creator<Approach>() {
        @Override
        public Approach createFromParcel(Parcel in) {
            return new Approach(in);
        }

        @Override
        public Approach[] newArray(int size) {
            return new Approach[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getReps() {
        return reps;
    }

    public void setReps(long reps) {
        this.reps = reps;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Approach{" +
                "id=" + id +
                ", reps=" + reps +
                ", value=" + value +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(reps);
        dest.writeLong(value);
    }
}
