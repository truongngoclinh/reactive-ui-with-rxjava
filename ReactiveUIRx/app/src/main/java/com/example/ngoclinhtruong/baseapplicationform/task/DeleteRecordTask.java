package com.example.ngoclinhtruong.baseapplicationform.task;

import com.example.base.db.RxUpdate;
import com.example.base.task.BaseTask;
import com.example.base.task.TaskResource;
import com.example.databases.schema.User;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by ngoclinh.truong on 8/15/16 - 12:28 PM.
 * Description:
 */
public class DeleteRecordTask extends BaseTask<Boolean> {

    private long mId;

    public DeleteRecordTask(long id) {
        mId = id;
    }

    @Override
    public Observable<Boolean> execute(TaskResource res) {
        return res.dbManager.update(new RxUpdate<Boolean>() {
            @Override
            protected Boolean execute(Realm db) {
                RealmResults<User> user = db.where(User.class).equalTo(User.COL_UID, mId).findAll();
                user.deleteAllFromRealm();

                return true;
            }
        });
    }
}
