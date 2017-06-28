package com.example.base.db.notify;

import io.realm.RealmObject;

/**
 * Created by ngoclinh.truong on 6/14/16.
 *
 * Notify of updates of a particular table
 */
public class UpdateNotify {

    private final String TAG = getClass().getSimpleName();

    public final Class<? extends RealmObject> schema;

    public UpdateNotify(Class<? extends RealmObject> schema) {
        this.schema = schema;
    }

    @Override
    public String toString() {
        return TAG + " table[" + schema.getSimpleName() + "]";
    }
}
