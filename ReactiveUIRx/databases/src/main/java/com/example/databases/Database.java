package com.example.databases;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;

/**
 * Created by ngoclinh.truong on 6/14/16.
 */
public abstract class Database {

    private final Context mContext;
    private volatile RealmConfiguration mConfig;

    public Database(Context context) {
        mContext = context;
    }

    public Realm instance() {
        if (mConfig == null) {
            synchronized (this) {
                if (mConfig == null) {
                    RealmConfiguration.Builder builder = new RealmConfiguration.Builder(mContext);
                    builder.name(getName());
                    builder.schemaVersion(getVersion());
                    builder.migration(getMigration());
                    builder.modules(getModule());

                    mConfig = builder.build();
                }
            }
        }

        return Realm.getInstance(mConfig);
    }

    protected abstract int getVersion();

    protected abstract String getName();

    protected abstract Object getModule();

    protected abstract RealmMigration getMigration();

    /**
     * {@link Realm} instance will be closed immediately after the execution, so be careful to
     * create copies if return type is {@link io.realm.RealmObject} or {@link io.realm.RealmResults}
     * @param task
     * @param <T>
     * @return
     */
    public <T> T run(DbTask<T> task) {
        Realm db = instance();
        T result = null;
        try {
            result = task.onExecute(db);
        } finally {
            // Realm uses reference count to determine the timing to release resources,
            // so make sure to close it when not in use.
            db.close();
        }

        return  result;
    }

}
