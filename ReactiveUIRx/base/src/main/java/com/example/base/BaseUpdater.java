package com.example.base;

import com.example.base.db.DbManager;
import com.example.base.db.RxUpdate;

import rx.functions.Action1;

/**
 * Created by ngoclinh.truong on 6/28/16 - 4:53 PM.
 * Description:
 * Yet another way to update data in the database, but differs from {@link com.example.base.db.DbManager#update(RxUpdate)} or
 * in that it, instead of returning an observable, executes the update logic immediately
 * and provides the convenience of updating db from places like {@link rx.Observable#doOnNext(Action1)}
 */
public abstract class BaseUpdater {

    protected final DbManager dbManager;

    public BaseUpdater(DbManager db) {
        dbManager = db;
    }

    protected abstract <T> T execute(RxUpdate<T> update);

}
