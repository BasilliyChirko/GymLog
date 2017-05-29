package basilliy.gymlog.domain.entity;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

import basilliy.gymlog.application.App;
import basilliy.gymlog.domain.repository.ID;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Exercise extends RealmObject implements ID, Parcelable {

    @PrimaryKey
    protected long id;private ExerciseStore store;
    private RealmList<Approach> approachList = new RealmList<>();

    public Exercise(){}

    protected Exercise(Parcel in) {
        id = in.readLong();
        store = in.readParcelable(ExerciseStore.class.getClassLoader());
        approachList = new RealmList<>();
        in.readTypedList(approachList, Approach.CREATOR);
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ExerciseStore getStore() {
        return store;
    }

    public void setStore(ExerciseStore store) {
        this.store = store;
    }

    public RealmList<Approach> getApproachList() {
        return approachList;
    }

    public void setApproachList(RealmList<Approach> approachList) {
        this.approachList = approachList;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", store=" + store +
                ", approachList=" + approachList +
                '}';
    }

    public String getRepsString() {
        long min = 999;
        long max = -1;
        for (Approach approach : approachList) {
            if (approach.getReps() > max)
                max = approach.getReps();
            if (approach.getReps() < min)
                min = approach.getReps();
        }

        if (max == min)
            return String.valueOf(max);

        if (max == 999 || min == -1)
            return "";

        return "~" + ((max + min) / 2);
    }

    public String getValueString() {
        long min = 999;
        long max = -1;
        for (Approach approach : approachList) {
            if (approach.getValue() > max)
                max = approach.getValue();
            if (approach.getValue() < min)
                min = approach.getValue();
        }

        if (max == min)
            return String.valueOf(max);

        if (max == 999 || min == -1)
            return "";

        return "~" + ((max + min) / 2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeParcelable(store, flags);
        dest.writeTypedList(approachList);
    }
}
