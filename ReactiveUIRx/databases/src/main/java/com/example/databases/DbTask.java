package com.example.databases;


import io.realm.Realm;

/**
 * Created by ngoclinh.truong on 6/14/16.
 */
public interface DbTask<T> {
    T onExecute(Realm db);
}
