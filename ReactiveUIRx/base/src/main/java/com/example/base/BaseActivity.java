package com.example.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.base.task.BaseTask;
import com.example.base.task.DataObserver;
import com.example.base.task.TaskExecutable;
import com.example.base.task.TaskManager;
import com.example.base.task.TaskScheduler;
import com.example.base.task.WeakDelegateObserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BaseActivity extends AppCompatActivity implements TaskExecutable {

    @Inject
    protected TaskManager taskManager;

    private final CompositeSubscription mSubscriptions = new CompositeSubscription();

    /**
     * Used to keep non-tracked data observer alive until destroyed
     */
    private List<DataObserver> mWeakObserverHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication application = (BaseApplication) getApplication();
        application.getComponent().inject(this);
    }

    @Override
    public <T> void executeTask(BaseTask<T> task, DataObserver<? super T> callback, boolean trackSubscription) {
        Subscription subscription;
        if (callback != null) {
            Observer<? super T> observer = callback;
            if (!trackSubscription) {
                if (mWeakObserverHolder == null) {
                    mWeakObserverHolder = new ArrayList<>();
                }
                mWeakObserverHolder.addAll((Collection<? extends DataObserver>) callback);
                // use a weak reference to avoid leaking activity if not tracking subscription
                observer = new WeakDelegateObserver<>(callback);
            }
            subscription = taskManager.execute(task).observeOn(TaskScheduler.UI).subscribe(observer);
        } else {
            subscription = taskManager.executeAndTrigger(task);
        }

        if (trackSubscription) {
            mSubscriptions.add(subscription);
        }
    }

    @Override
    protected void onDestroy() {
        if (mSubscriptions != null) {
            mSubscriptions.clear();
        }

        if (mWeakObserverHolder != null) {
            mWeakObserverHolder.clear();
        }
        super.onDestroy();
    }
}
