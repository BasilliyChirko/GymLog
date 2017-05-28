package basilliy.gymlog.domain.entity;


import android.os.Parcel;
import android.os.Parcelable;

import basilliy.gymlog.domain.repository.ID;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ExerciseStore extends RealmObject implements ID, Parcelable {

    @PrimaryKey
    protected long id;
    private String name;
    private Measure measure;

    private int level;
    private String description;
    private String targetMuscle;
    private String inventory;
    private String instruction;
    private String involvedMuscle;
    private String advice;

    public ExerciseStore() {}


    protected ExerciseStore(Parcel in) {
        id = in.readLong();
        name = in.readString();
        measure = in.readParcelable(Measure.class.getClassLoader());
        level = in.readInt();
        description = in.readString();
        targetMuscle = in.readString();
        inventory = in.readString();
        instruction = in.readString();
        involvedMuscle = in.readString();
        advice = in.readString();
    }

    public static final Creator<ExerciseStore> CREATOR = new Creator<ExerciseStore>() {
        @Override
        public ExerciseStore createFromParcel(Parcel in) {
            return new ExerciseStore(in);
        }

        @Override
        public ExerciseStore[] newArray(int size) {
            return new ExerciseStore[size];
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

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTargetMuscle() {
        return targetMuscle;
    }

    public void setTargetMuscle(String targetMuscle) {
        this.targetMuscle = targetMuscle;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getInvolvedMuscle() {
        return involvedMuscle;
    }

    public void setInvolvedMuscle(String involvedMuscle) {
        this.involvedMuscle = involvedMuscle;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    @Override
    public String toString() {
        return "ExerciseStore{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", measure=" + measure +
                ", level=" + level +
                ", description='" + description + '\'' +
                ", targetMuscle='" + targetMuscle + '\'' +
                ", inventory='" + inventory + '\'' +
                ", instruction='" + instruction + '\'' +
                ", involvedMuscle='" + involvedMuscle + '\'' +
                ", advice='" + advice + '\'' +
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
        dest.writeParcelable(measure, flags);
        dest.writeInt(level);
        dest.writeString(description);
        dest.writeString(targetMuscle);
        dest.writeString(inventory);
        dest.writeString(instruction);
        dest.writeString(involvedMuscle);
        dest.writeString(advice);
    }
}
