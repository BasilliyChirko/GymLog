package basilliy.gymlog.utils;

import android.util.Log;

import basilliy.gymlog.application.App;
import basilliy.gymlog.domain.Program;
import basilliy.gymlog.domain.ProgramRepository;
import io.realm.Realm;
import io.realm.RealmResults;

public final class D {

    public final class constant {
        public static final int DATABASE_VERSION = 3;
    }



    public static final String TAG = "GymLog";

    public static void clearDatabase() {
        Realm realm = App.getRealm();
        realm.beginTransaction();
        realm.where(Program.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();
    }

    public static void test() {
        ProgramRepository repository = App.getProgramRepository();

        Program program = new Program();
        program.setId(3);
        program.setName("sefse");

        repository.insert(program);

        RealmResults<Program> all = repository.findAll();
        int size = all.size();
        log("------ " + size + " program");
        for (Program p : all) log(p);
        log("------");

//        Program program = Program.create();
//        log(program);
//        Program program = repository.create();
//        program.setName("News program");
//        log(program);
//        repository.insert(program);


//        all = repository.findAll();
//        size = all.size();
//        log("------ " + size + " program");
//        for (Program p : all) {
//            log(p);
//        }
//        log("------");

        repository.nextID();
    }

    public static void log(Object object) {
        if (object == null) {
            Log.d(TAG, "null");
        } else {
            Log.d(TAG, String.valueOf(object));
        }
    }
}
