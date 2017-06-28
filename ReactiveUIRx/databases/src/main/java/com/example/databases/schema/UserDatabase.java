package com.example.databases.schema;

import android.content.Context;

import com.example.databases.Database;
import com.example.utils.LogUtils;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;

/**
 * Created by ngoclinh.truong on 6/28/16 - 4:32 PM.
 * Description: create new database for each user with particular name = "user_" + uid
 */
public class UserDatabase extends Database {

    private final static int VERSION = 0;
    private final static String PREFIX = "user_";
    private final long mUid;

    public UserDatabase(Context context, long uid) {
        super(context);
        mUid = uid;
    }

    public long getmUid() {
        return mUid;
    }

    @Override
    protected int getVersion() {
        return VERSION;
    }

    @Override
    protected String getName() {
        return PREFIX + mUid;
    }

    @Override
    protected Object getModule() {
        return new UserDbModule();
    }

    @Override
    protected RealmMigration getMigration() {
        return new Migration();
    }

    final class Migration implements RealmMigration {

        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
            LogUtils.d("migrate user db(" + mUid + ") old = " + oldVersion + " new = " + newVersion);
        }
    }
}
