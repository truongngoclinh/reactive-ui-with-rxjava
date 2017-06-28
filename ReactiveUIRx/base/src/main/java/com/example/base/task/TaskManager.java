package com.example.base.task;


import com.example.utils.LogUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.BuildConfig;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;

/**
 * Created by ngoclinh.truong on 6/14/16.
 */
public class TaskManager {

    @Inject
    TaskResource taskResource;

    public <T> Observable<T> execute(final BaseTask<T> task) {
        final String taskName = task.getClass().getSimpleName();
        return Observable.defer(new Func0<Observable<T>>() {
            @Override
            public Observable<T> call() {
                return task.execute(taskResource).doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        LogUtils.d("[%s] start execution", taskName);
                    }
                }).compose(new FrequencyMonitorTransform<>(task)).onBackpressureDrop(new Action1<T>() {
                    @Override
                    public void call(T t) {
                        LogUtils.d("[%s] drops item on back pressure", taskName);
                    }
                });
            }
        });
    }

    /**
     * The task will be deferred so that all of its internal logic will be
     * executed in {@link TaskSchedulers#COMPUTE} scheduler by default.
     *
     * @param task task to be executed
     * @param <T>  return type
     * @return subscription of the task in case a future disposal is necessary
     */
    public <T> Subscription executeAndTrigger(final BaseTask<T> task) {
        return Observable.defer(new Func0<Observable<T>>() {
            @Override
            public Observable<T> call() {
                return task.execute(taskResource).doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        LogUtils.d("[%s] starts execution (auto-trigger)", task.getClass().getSimpleName());
                    }
                });
            }
        }).subscribeOn(TaskScheduler.COMPUTE).subscribe(new DataObserver<T>() {
            @Override
            public void onNext(T t) {
                //
            }
        });
    }

    private class FrequencyMonitorTransform<T> implements Observable.Transformer<T, T> {

        private final static long MIN_WARN_INTERVAL = 100; // 100ms
        private final String mTaskName;

        FrequencyMonitorTransform(BaseTask<T> task) {
            mTaskName = task.getClass().getSimpleName();
        }

        @Override
        public Observable<T> call(Observable<T> tObservable) {
            if (BuildConfig.DEBUG) {
                return tObservable.doOnNext(new Action1<T>() {

                    private volatile long lastTimestamp = 0;

                    @Override
                    public void call(T t) {
                        long now = System.currentTimeMillis();
                        long diff = now - lastTimestamp;
                        if (diff < MIN_WARN_INTERVAL) {
                            LogUtils.d("WARNING! %s is emitting too frequently: interval=%dms", mTaskName, diff);
                        }
                        lastTimestamp = now;
                    }
                });
            }

            return tObservable;
        }
    }
}
