package basilliy.gymlog.utils;

public interface Config {
    interface database {
        String name = "main_db";
        int version = 11;
    }

    interface pref {
        String name = "preferences";
        String firstLoad = "first_load";
    }
}
