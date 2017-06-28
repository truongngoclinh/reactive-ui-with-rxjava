package com.example.base.task;

/**
 * Created by ngoclinh.truong on 6/14/16.
 */
public interface TaskExecutable {
    /**
     * execute a task and subscribe immediately to the returned observable
     *
     * @param task              task to be executed
     * @param callback          callback for the observable
     * @param trackSubscription true if subscription will be disposed upon destroy of current activity/fragment
     * @param <T>               return type
     */
    <T> void executeTask(BaseTask<T> task, final DataObserver<? super T> callback, boolean trackSubscription);


}
