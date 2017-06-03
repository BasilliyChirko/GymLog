package basilliy.gymlog.utils;

public interface Config {
    interface database {
        String name = "main_db";
        int version = 16;
    }

    interface pref {
        String name = "preferences";
        String firstLoad = "first_load";
        String approachReps = "approach_reps";
        String approachValue = "approach_value";
    }
}
