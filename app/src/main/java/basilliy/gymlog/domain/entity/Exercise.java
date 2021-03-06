package basilliy.gymlog.domain.entity;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

import basilliy.gymlog.application.App;
import basilliy.gymlog.domain.repository.ID;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Exercise extends RealmObject implements ID, Parcelable {

    @PrimaryKey
    protected long id;
    private ExerciseStore store;
    private RealmList<Approach> approachList = new RealmList<>();
    private boolean done;

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
        if (getApproachList().size() == 0)
            return "";
        StringBuilder builder = new StringBuilder();
        for (Approach approach : getApproachList())
            builder.append((long)approach.getReps()).append("-");
        String s = builder.toString();
        return s.substring(0, s.length() - 1);
    }

    public String getValueString() {
        if (getApproachList().size() == 0)
            return "";
        StringBuilder builder = new StringBuilder();
        for (Approach approach : getApproachList())
            builder.append((long)approach.getValue()).append("-");
        String s = builder.toString();
        return s.substring(0, s.length() - 1);
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

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        Realm realm = App.getRealm();
        realm.beginTransaction();
        this.done = done;
        realm.commitTransaction();
    }

    public void increaseApproachValue() {
        Realm realm = App.getRealm();
        realm.beginTransaction();
        for (Approach approach : getApproachList())
            approach.setValue(approach.getValue() * 1.04);
        realm.commitTransaction();
    }

    public void decreaseApproachValue() {
        Realm realm = App.getRealm();
        realm.beginTransaction();
        for (Approach approach : getApproachList())
            approach.setValue(approach.getValue() * 0.96);
        realm.commitTransaction();
    }

}
