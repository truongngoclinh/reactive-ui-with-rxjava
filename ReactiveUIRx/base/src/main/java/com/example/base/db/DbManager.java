package com.example.base.db;

import android.content.Context;

import com.example.base.task.TaskScheduler;
import com.example.base.db.notify.UpdateNotify;
import com.example.databases.Database;
import com.example.databases.DbTask;
import com.example.databases.schema.UserDatabase;
import com.example.utils.ClassUtil;
import com.example.utils.LogUtils;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action0;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;
import rx.subscriptions.Subscriptions;

/**
 * Created by ngoclinh.truong on 6/14/16.
 */
public class DbManager {

    private final Context mContext;
    private final AtomicInteger mRefCount;
    private final Subject<UpdateNotify, UpdateNotify> mUpdateNotify;
    private volatile UserDatabase mDb;

    public DbManager(Context context) {
        mContext = context.getApplicationContext();
        mRefCount = new AtomicInteger();
        mUpdateNotify = new SerializedSubject<>(PublishSubject.<UpdateNotify>create());
    }

    public Observable<UpdateNotify> dbNotifies() {
        return mUpdateNotify.observeOn(TaskScheduler.COMPUTE);
    }

    public UserDatabase db() {
        if (mDb == null) {
            throw new  IllegalStateException("db was not initialized yet...");
        }

        return mDb;
    }

    public synchronized void initDb(long id) {
        if (mDb != null && mDb.getmUid() == id) {
            return;
        }

        mDb = new UserDatabase(mContext, id);
    }

    private <T> Observable<T> execute(final Database database, final DbTask<T> task, Scheduler scheduler) {
        if (database == null) {
            throw new IllegalStateException("database is null");
        }

        final String taskName = ClassUtil.getName(task);
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                final Realm realm = database.instance();
                int count = mRefCount.getAndIncrement();
                if (count == 0) {
                    LogUtils.d(taskName + " open realm (%s)", count);
                }

                boolean closeImmediate = true;
                if (task instanceof Query) {
                    // in queries we normally want to use the obtained result
                    // which might be realm objects, so instead of closing realm
                    // right away, we allow access to realm objects by deferring
                    // close of the ream instance (which will invalidate all realm objects)
                    closeImmediate = false;
                    subscriber.add(Subscriptions.create(new Action0() {
                        @Override
                        public void call() {
                            realm.close();

                            int count = mRefCount.decrementAndGet();
                        }
                    }));
                }

                T result = task.onExecute(realm);
                subscriber.onNext(result);
                subscriber.onCompleted();

                if (closeImmediate) {
                    realm.close();
                }
            }
        }).subscribeOn(scheduler);
    }

    /**
     * Return an observable that updates common database upon subscribed. Note that if the emitted object
     * is a live realm object, make sure to copy it from Realm before emit because the Realm instance
     * will closed immediately after the update.
     *
     * @param update
     * @param <T>
     * @return
     */
    public <T> Observable<T> update(RxUpdate<T> update) {
        update.setNotifySubject(mUpdateNotify);
        return execute(mDb, update, TaskScheduler.IO);
    }


    /**
     * Run the update immediately in current thread and return whatever returned by the update. This is mainly used in
     * notify processors.
     *
     * @param update
     * @param <T>
     * @return
     */
    public <T> T updateImmediately(RxUpdate<T> update) {
        update.setNotifySubject(mUpdateNotify);
        return mDb.run(update);
    }


    /**
     * Return an observable that queries user database upon subscribed. Note that if the emitted object
     * is a live realm object, it'll be accessible until unsubscribed.
     *
     * @param query
     * @param <T>
     * @return
     */
    public <T> Observable<T> query(Query<T> query) {
        return execute(mDb, query, TaskScheduler.DB_READ);
    }

}
