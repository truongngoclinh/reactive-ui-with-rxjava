package com.example.base.db.notify;

import com.example.databases.schema.User;

/**
 * Created by ngoclinh.truong on 8/15/16 - 12:15 PM.
 * Description:
 */
public class UserUpdateNotify extends DataSetUpdateNotify<Long> {

    public UserUpdateNotify() {
        super(User.class);
    }

    public UserUpdateNotify(long id) {
        this();
        add(id);
    }
}
