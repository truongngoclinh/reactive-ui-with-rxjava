package com.example.base.updater;

import com.example.base.BaseUpdater;
import com.example.base.db.DbManager;
import com.example.base.db.RxUpdate;

/**
 * Created by ngoclinh.truong on 6/28/16 - 4:53 PM.
 * Description:
 */
public class UserUpdater extends BaseUpdater {

    public UserUpdater(DbManager db) {
        super(db);
    }

    // TODO: working on realm sample, cant reference realm classes here

    @Override
    protected <T> T execute(RxUpdate<T> update) {
        return null;
    }
}
