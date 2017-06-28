package com.example.base.db;

import com.example.databases.DbTask;
import com.example.utils.ClassUtil;
import com.example.utils.LogUtils;

import io.realm.Realm;

/**
 * Created by ngoclinh.truong on 6/16/16.
 */
public abstract class Update<T> implements DbTask<T> {
    protected final String TAG;

    public Update() {
        TAG = ClassUtil.getName(this);
    }

    @Override
    public T onExecute(Realm db) {
        T result = null;

        LogUtils.d(TAG + " about to being transaction");
        db.beginTransaction();
        LogUtils.d(TAG + " entered transaction");
        try {
            result = execute(db);
            db.commitTransaction();
            onPostCommit();
            LogUtils.d(TAG + " commited transaction");
        } catch (Exception e) {
            db.cancelTransaction();
            LogUtils.d(TAG + " canceled transaction");
            LogUtils.e(e);
        }

        return result;
    }

    protected void onPostCommit() {

    }

    protected abstract T execute(Realm db);
}

