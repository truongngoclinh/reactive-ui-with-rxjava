package com.example.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.base.task.BaseTask;
import com.example.base.task.DataObserver;
import com.example.base.task.TaskExecutable;
import com.example.base.task.TaskManager;

import java.util.List;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by ngoclinh.truong on 6/14/16.
 */
public class BaseFragment extends Fragment implements TaskExecutable {


    @Inject
    protected TaskManager taskManager;

    private final CompositeSubscription mSubscriptions = new CompositeSubscription();

    /**
     * Used to keep non-tracked data observer alive until destroyed
     */
    private List<DataObserver> mWeakObserverHolder;

    private BaseProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication application = (BaseApplication) getActivity().getApplication();
        application.getComponent().inject(this);
    }

    @Override
    public void onDestroy() {
        mSubscriptions.clear();
        if (mWeakObserverHolder != null) {
            mWeakObserverHolder.clear();
        }
        //  in case of dialog is forgotten to close
        if (mProgressDialog != null) {
            mProgressDialog.close();
        }
        super.onDestroy();
    }

    @Override
    public <T> void executeTask(BaseTask<T> task, DataObserver<? super T> callback, boolean trackSubscription) {

    }
}
