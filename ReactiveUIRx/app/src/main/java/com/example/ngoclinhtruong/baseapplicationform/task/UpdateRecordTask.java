package com.example.ngoclinhtruong.baseapplicationform.task;

import com.example.base.db.RxUpdate;
import com.example.base.db.notify.UserUpdateNotify;
import com.example.base.task.BaseTask;
import com.example.base.task.TaskResource;
import com.example.databases.schema.User;

import io.realm.Realm;
import rx.Observable;

/**
 * Created by ngoclinh.truong on 8/15/16 - 12:37 PM.
 * Description:
 */
public class UpdateRecordTask extends BaseTask<User> {

    private String mName;
    private long mId;

    public UpdateRecordTask(String name, long id) {
        mName = name;
        mId = id;
    }

    @Override
    public Observable<User> execute(TaskResource res) {
        return res.dbManager.update(new RxUpdate<User>() {

            @Override
            protected User execute(Realm db) {
                User user = db.where(User.class).equalTo(User.COL_UID, mId).findFirst();
                user.setName(mName);

                UserUpdateNotify notify = new UserUpdateNotify(mId);
                notify.add(mId);
                addNotify(notify);

                return null;
            }
        });
    }
}
