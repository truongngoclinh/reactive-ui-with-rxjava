package com.example.ngoclinhtruong.baseapplicationform.task;

import com.example.base.db.RxUpdate;
import com.example.base.task.BaseTask;
import com.example.base.task.TaskResource;
import com.example.databases.schema.User;

import io.realm.Realm;
import rx.Observable;

/**
 * Created by ngoclinh.truong on 6/28/16 - 4:48 PM.
 * Description: add new record with
 */
public class AddRecordTask extends BaseTask<Boolean> {
    private long mId;

    public void AddRecordTask(long id) {
        mId = id;
    }

    @Override
    public Observable<Boolean> execute(TaskResource res) {
        return res.dbManager.update(new RxUpdate<Boolean>() {
            @Override
            protected Boolean execute(Realm db) {
                db.copyFromRealm(new User(mId));
                return true;
            }
        });
    }
}
