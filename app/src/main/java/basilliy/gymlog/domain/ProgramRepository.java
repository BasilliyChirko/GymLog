package basilliy.gymlog.domain;


import io.realm.RealmResults;

public interface ProgramRepository {
    Program find(long id);
    RealmResults<Program> findAll();
    void update(Program program);
    void remove(Program program);
    void insert(Program program);
    long nextID();
}
