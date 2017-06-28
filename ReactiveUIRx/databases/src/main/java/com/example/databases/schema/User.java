package com.example.databases.schema;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ngoclinh.truong on 6/28/16 - 4:29 PM.
 * Description:
 */
public class User extends RealmObject {

    public static final String COL_UID = "uid";

    public User(){}

    public User(long uid) {
        this.uid = uid;
    }

    @PrimaryKey
    private long uid;

    private String name;

    public static String getColUid() {
        return COL_UID;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
