package com.example.base.task;

import java.lang.ref.WeakReference;

import rx.Observer;

/**
 * Created by ngoclinh.truong on 6/16/16.
 */
public class WeakDelegateObserver<T> implements Observer<T> {

    private final WeakReference<DataObserver<T>> mClient;

    public WeakDelegateObserver(DataObserver<T> observer) {
        mClient = new WeakReference<>(observer);
    }

    @Override
    public void onCompleted() {
        DataObserver<T> observer = mClient.get();
        if (observer != null) {
            observer.onCompleted();
        }
    }

    @Override
    public void onError(Throwable e) {
        DataObserver<T> observer = mClient.get();
        if (observer != null) {
            observer.onError(e);
        }
    }

    @Override
    public void onNext(T t) {
        DataObserver<T> observer = mClient.get();
        if (observer != null) {
            observer.onCompleted();
        }
    }
}
