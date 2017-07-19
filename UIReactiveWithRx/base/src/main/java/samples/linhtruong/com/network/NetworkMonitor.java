package samples.linhtruong.com.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.PowerManager;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;
import samples.linhtruong.com.task.BaseObserver;
import samples.linhtruong.com.task.TaskSchedulers;
import samples.linhtruong.com.utils.LogUtils;
import samples.linhtruong.com.utils.NetworkUtil;

import java.util.concurrent.TimeUnit;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 7/18/17 - 17:21.
 * @organization VED
 */

public class NetworkMonitor extends BroadcastReceiver {

    private static final int MAX_CHECK_ATTEMPTS = 10;

    private Subscription mSubscription;
    private final BehaviorSubject<Boolean> mStatus;

    private static final Action1<Boolean> ACTION_LOG = new Action1<Boolean>() {
        @Override
        public void call(Boolean hasInternet) {
            LogUtils.d("NetworkMonitor emitting internet availability: %s", hasInternet);
        }
    };

    public NetworkMonitor(Context context) {
        initReceiver(context);

        NetworkUtil.Result result = NetworkUtil.check();
        mStatus = BehaviorSubject.create(result.isConnected());

        if (!result.isConnected() && !result.isDozing()) {
            checkNetworkExponentialCoolDown(context);
        }
    }

    public Observable<Boolean> getStatus() {
        return mStatus.distinctUntilChanged().doOnNext(ACTION_LOG);
    }

    private void checkNetworkExponentialCoolDown(Context context) {
       mSubscription = Observable.range(1, MAX_CHECK_ATTEMPTS).map(new Func1<Integer, Integer>() {
           @Override
           public Integer call(Integer powOf) {
               return (int) Math.pow(2, powOf);
           }
       }).concatMap(new Func1<Integer, Observable<?>>() {
           @Override
           public Observable<?> call(Integer delay) {
               LogUtils.d("schedule network check in %d seconds", delay);
               return Observable.timer(delay, TimeUnit.SECONDS);
           }
       }).subscribeOn(TaskSchedulers.COMPUTE).observeOn(TaskSchedulers.UI).takeUntil(new Func1<Object, Boolean>() {
           @Override
           public Boolean call(Object o) {
               if (NetworkUtil.check().isConnected()) {
                  LogUtils.d("network check pass: connected, stop network check schedules") ;
                   mStatus.onNext(true);
               }

               return false;
           }
       }).subscribe(new BaseObserver<Object>() {

           @Override
           public void onNext(Object o) {
           }

           @Override
           public void onCompleted() {
               super.onCompleted();
               LogUtils.d("gave up network check");
           }
       });
    }

    private void initReceiver(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        // to support DOZE mode in Android M
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            filter.addAction(PowerManager.ACTION_DEVICE_IDLE_MODE_CHANGED);
        }
        context.registerReceiver(this, filter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }

        LogUtils.d("NetworkMonitor onReceived: " + intent.getAction());
        switch (intent.getAction()) {
            case ConnectivityManager.CONNECTIVITY_ACTION:
            case PowerManager.ACTION_DEVICE_IDLE_MODE_CHANGED:
                onNetworkChanged(context);
                break;
        }
    }

    private void onNetworkChanged(Context context) {
        NetworkUtil.Result result = NetworkUtil.check();
        LogUtils.d("NetworkMonitor onNetworkChanged connected=%s", result.isConnected());
        mStatus.onNext(result.isConnected());

        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            LogUtils.d("canceled previous network check");
            mSubscription.unsubscribe();
        }

        if (!result.isConnected() && !result.isDozing()) {
            checkNetworkExponentialCoolDown(context);
        }
    }
}
