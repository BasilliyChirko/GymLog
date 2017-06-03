package basilliy.gymlog.application.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import basilliy.gymlog.domain.entity.ExerciseResult;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ExerciseResultService extends Service<ExerciseResult> {

    public ExerciseResultService() {
        super(ExerciseResult.class);
    }

    @Override
    public long getId(ExerciseResult object) {
        return object.getId();
    }

    @Override
    public void setId(ExerciseResult object, long id) {
        object.setId(id);
    }

    @Override
    public RealmList getInnerList(ExerciseResult object) {
        return null;
    }

    @Override
    public Service getInnerDataService() {
        return null;
    }

    public Map<String, ArrayList<ExerciseResult>> getMapResult() {
        Map<String, ArrayList<ExerciseResult>> map = new HashMap<>();

        for (ExerciseResult result : getAll()) {
            if (!map.containsKey(result.getStore().getName()))
                map.put(result.getStore().getName(), new ArrayList<ExerciseResult>());
            map.get(result.getStore().getName()).add(result);
        }

        return map;
    }
}
