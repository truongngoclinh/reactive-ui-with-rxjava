package com.example.base.task;

import rx.Observable;

/**
 * Created by ngoclinh.truong on 6/14/16.
 */
public abstract class BaseTask<T> {

    public abstract Observable<T> execute(TaskResource res);
}
