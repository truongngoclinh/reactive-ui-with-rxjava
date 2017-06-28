package com.example.base.task;

import com.example.utils.LogUtils;

import rx.Observer;

/**
 * Created by ngoclinh.truong on 6/14/16.
 */
public class DataObserver<T> implements Observer<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        LogUtils.d(e);
    }

    @Override
    public void onNext(T t) {

    }
}
