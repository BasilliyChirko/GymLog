package basilliy.gymlog.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

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

    public static void testA() {
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

    public static void testB() {
        Program program = getMockProgram();
        log(program);
        for (Day day : program.getDayList()) log(day);

        App.getProgramService().persist(program);
        program = App.getProgramService().getSinge(program.getId());

        log(program);
        for (Day day : program.getDayList()) log(day);

        String key = "sef";
        Bundle bundle = new Bundle();
        bundle.putParcelable(key, program);
        program = bundle.getParcelable(key);

        log(program);
        for (Day day : program.getDayList()) log(day);

    }

    public static void restoreTest() {
        clearDatabase();
        Service<Program> service = App.getProgramService();
        service.persist(getMockProgram());
    }

    public static Program getMockProgram() {
        Program program;
        Day day;
        Exercise exercise;
        Approach approach;


        program = new Program();
        program.setName("Program");

        day = new Day();
        day.setName("Day1");
        program.getDayList().add(day);

//        exercise = new Exercise();
//        day.getExerciseList().add(exercise);
//
//        exercise = new Exercise();
//        day.getExerciseList().add(exercise);
//
//        approach = new Approach();
//        approach.setReps(55);
//        approach.setValue(89);
//        exercise.getApproachList().add(approach);

        day = new Day();
        day.setName("Day2");
        program.getDayList().add(day);

        day = new Day();
        day.setName("Day3");
        program.getDayList().add(day);

        return program;
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
            Log.i(TAG, "null");
        } else {
            Log.i(TAG, String.valueOf(object));
        }
    }

    public static void toast(String text) {
        Toast.makeText(App.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public static String stroke(String text) {
        return String.valueOf(Html.fromHtml("<s>" + text + "</s>"));
    }
}
