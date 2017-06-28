package com.example.base.db;

import com.example.base.BaseUpdater;
import com.example.databases.schema.User;
import com.example.utils.LogUtils;

import io.realm.Realm;

/**
 * Created by ngoclinh.truong on 8/15/16 - 11:49 AM.
 * Description:
 */
public class UserUpdater extends BaseUpdater {

    public UserUpdater(DbManager db) {
        super(db);
    }

    private User get(Realm db, long uid) {
        return db.where(User.class).equalTo(User.COL_UID, uid).findFirst();
    }

    private User getOrCreate(Realm db, long uid) {
        User user = get(db, uid);
        if (user == null) {
            LogUtils.d("creating user record for " + uid);
            user = db.copyToRealm(new User(uid));
        }

        return user;
    }

/*    public void simpleUpdate(final User user) {
        execute(new RxUpdate<Void>() {
            @Override
            protected Void execute(Realm db) {
                User user = get(db, user.getUid());
                user.setName(user.getName());

                UserUpdateNotify notify = new UserUpdateNotify(user.getUid());
                notify.add(user.getUid());
                addNotify(notify);

                return null;
            }
        });
    }*/

    @Override
    protected <T> T execute(RxUpdate<T> update) {
        return null;
    }
}
