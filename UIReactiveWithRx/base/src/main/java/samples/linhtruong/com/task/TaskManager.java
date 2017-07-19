package samples.linhtruong.com.task;


import rx.Observable;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import samples.linhtruong.com.utils.LogUtils;

import javax.inject.Inject;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 6/29/17 - 10:54.
 * @organization VED
 */

public class TaskManager {

    public static final BaseObserver<Object> EMPTY = new BaseObserver() {
        @Override
        public void onNext(Object o) {
        }
    };

    @Inject
    TaskResources mTaskResources;

    /**
     * The task will be deferred so that all of its internal logic will be
     * executed in {@link TaskSchedulers#COMPUTE} scheduler by default.
     *
     * @param task task to be executed
     * @param <T>  return type
     * @return an observable task that can be subscribed to
     */
    public <T> Observable<T> execute(final BaseTask<T> task) {
        final String taskName = task.getClass().getSimpleName();
        return Observable.defer(new Func0<Observable<T>>() {
            @Override
            public Observable<T> call() {
                return task.execute(mTaskResources)
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                LogUtils.d("[%s] start executure", taskName);
                            }
                        })
                        .onBackpressureDrop(new Action1<T>() {
                            @Override
                            public void call(T t) {
                                LogUtils.d("[%s] drop items on backpressure", taskName);
                            }
                        });
            }
        }).subscribeOn(TaskSchedulers.COMPUTE);
    }

    /**
     * The task will be deferred so that all of its internal logic will be
     * executed in {@link TaskSchedulers#COMPUTE} scheduler by default.
     *
     * @param task task to be executed
     * @param <T> return type
     * @return an subscription of a task in case a future disposal is necessary
     */
    public <T> Subscription executeAndTrigger(final BaseTask<T> task) {
        final String taskName = task.getClass().getSimpleName();
        return Observable.defer(new Func0<Observable<T>>() {
            @Override
            public Observable<T> call() {
                return task.execute(mTaskResources)
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                LogUtils.d("[%s] start executure", taskName);
                            }
                        })
                        .onBackpressureDrop(new Action1<T>() {
                            @Override
                            public void call(T t) {
                                LogUtils.d("[%s] drop items on backpressure", taskName);
                            }
                        });
            }
        }).subscribeOn(TaskSchedulers.COMPUTE).subscribe(EMPTY);
    }
}
