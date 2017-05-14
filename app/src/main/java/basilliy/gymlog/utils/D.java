package basilliy.gymlog.utils;

import android.content.Context;
import android.util.Log;

import basilliy.gymlog.application.App;
import basilliy.gymlog.application.service.Service;
import basilliy.gymlog.domain.entity.Approach;
import basilliy.gymlog.domain.entity.Day;
import basilliy.gymlog.domain.entity.Exercise;
import basilliy.gymlog.domain.entity.ExerciseStore;
import basilliy.gymlog.domain.entity.Measure;
import basilliy.gymlog.domain.entity.Program;
import io.realm.RealmObject;
import io.realm.RealmResults;

public final class D {

    public static final String TAG = "GymLog";

    public static void test() {
        restoreTest();
        print(Program.class);
        print(Day.class);
        print(Exercise.class);
        print(Approach.class);
        log("--");

        Service<Program> service = App.getProgramService();
        Program singe = service.getSinge(1);
        service.remove(singe);

        log("--");
        print(Program.class);
        print(Day.class);
        print(Exercise.class);
        print(Approach.class);
    }

    public static void restoreTest() {
        clearDatabase();

        Program program;
        Day day;
        Exercise exercise;
        Approach approach;


        program = new Program();
        program.setName("Program");

        day = new Day();
        day.setName("Day1");
        program.getDayList().add(day);

        exercise = new Exercise();
        day.getExerciseList().add(exercise);

        exercise = new Exercise();
        day.getExerciseList().add(exercise);

        approach = new Approach();
        approach.setReps(55);
        approach.setValue(89);
        exercise.getApproachList().add(approach);

        day = new Day();
        day.setName("Day2");
        program.getDayList().add(day);

        day = new Day();
        day.setName("Day3");
        day.setId(5);
        program.getDayList().add(day);


        Service<Program> service = App.getProgramService();
        service.persist(program);
    }

    public static <E extends RealmObject> void print(Class<E> eClass) {
        RealmResults<E> list = App.getRepository(eClass).findAll();
        log("----- " + eClass.getSimpleName() + " " + list.size() + " item");
        for (E e : list)
            log(e);
    }


    public static void clearDatabase() {
        App.getRepository(Program.class).deleteAll();
        App.getRepository(Day.class).deleteAll();
        App.getRepository(Exercise.class).deleteAll();
        App.getRepository(ExerciseStore.class).deleteAll();
        App.getRepository(Approach.class).deleteAll();
        App.getRepository(Measure.class).deleteAll();

        App.getContext().getSharedPreferences(Config.pref.name, Context.MODE_PRIVATE)
                .edit().clear().apply();
    }

    public static void log(Object object) {
        if (object == null) {
            Log.d(TAG, "null");
        } else {
            Log.d(TAG, String.valueOf(object));
        }
    }
}
