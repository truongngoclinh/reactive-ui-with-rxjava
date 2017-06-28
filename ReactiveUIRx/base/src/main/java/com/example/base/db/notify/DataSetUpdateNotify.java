package com.example.base.db.notify;

import java.util.LinkedHashSet;
import java.util.Set;

import io.realm.RealmObject;

/**
 * Created by ngoclinh.truong on 8/15/16 - 12:05 PM.
 * Description:
 */
public class DataSetUpdateNotify<T> extends UpdateNotify {

    private final Set<T> mIds;

    public DataSetUpdateNotify(Class<? extends RealmObject> schema) {
        super(schema);
        mIds = new LinkedHashSet<>();
    }

    public void add(T id) {
        mIds.add(id);
    }

    public Set<T> getIds() {
        return mIds;
    }

    public void merge(DataSetUpdateNotify<T> duplciate) {
        if (duplciate == null) {
            return;
        }

        mIds.addAll(duplciate.getIds());
    }

}
