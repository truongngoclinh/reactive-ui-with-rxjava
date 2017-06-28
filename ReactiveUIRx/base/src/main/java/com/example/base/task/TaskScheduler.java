package com.example.base.task;

import android.os.HandlerThread;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ngoclinh.truong on 6/14/16.
 */
public final class TaskScheduler {


    private static final int POOL_SIZE_IO = 8;
    private static final int KEEP_ALIVE_TIME = 30; // seconds

    // common IO i.e. network, db write
    private static final ThreadPoolExecutor sIOExecutor = new ThreadPoolExecutor(POOL_SIZE_IO, POOL_SIZE_IO, KEEP_ALIVE_TIME,
            TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(), new IoThreadFactory(), new ThreadPoolExecutor.DiscardPolicy());

    static {
        sIOExecutor.allowsCoreThreadTimeOut();
    }

    // db read
    private static final HandlerThread sDbReadThread = new HandlerThread("db-r-1");

    static {
        sDbReadThread.start();
    }

    public static final Scheduler IO = Schedulers.from(sIOExecutor);
    public static final Scheduler DB_READ = AndroidSchedulers.from(sDbReadThread.getLooper());
    public static final Scheduler UI = AndroidSchedulers.mainThread();
    public static final Scheduler COMPUTE = Schedulers.computation();

    private static final class IoThreadFactory implements ThreadFactory {

        private final AtomicInteger index = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "io-" + index.getAndIncrement());
        }
    }
}
