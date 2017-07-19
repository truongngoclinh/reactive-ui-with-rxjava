package samples.linhtruong.com.uireactivewithrx.app;

import android.content.Context;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import samples.linhtruong.com.NetworkConst;
import samples.linhtruong.com.helper.TimeHelper;
import samples.linhtruong.com.network.NetworkMonitor;
import samples.linhtruong.com.network.NetworkOpsTask;
import samples.linhtruong.com.task.TaskManager;
import samples.linhtruong.com.utils.LogUtils;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 7/18/17 - 17:40.
 * @organization VED
 */

public class AppLifeCycleManager {

    private static final int HEARTBEAT_INTERVAL_FOREGROUND = 30000; // 30s
    private static final int HEARTBEAT_INTERVAL_BACKGROUND = 180000; // 3min

    @Inject
    AppLifeCycleMonitor appLifeCycleMonitor;

    @Inject
    TaskManager taskManager;

    @Inject
    NetworkMonitor networkMonitor;

    private final Context mContext;
    private final AtomicInteger mLastSessionSyncTime;

    public AppLifeCycleManager(Context context) {
        mContext = context;
        mLastSessionSyncTime = new AtomicInteger();
    }

    public void onCreate() {
        initNetworkMonitor();
    }

    /**
     * Sync our network manager with network connectivity. When network connectivity is down,
     * network manager will be notified and disconnects itself; similarly when network resumes,
     * network manager will be started again.
     *
     * @return
     */
    private void initNetworkMonitor() {
        final Observable<Boolean> networkConn = networkMonitor.getStatus().debounce(500, TimeUnit.MILLISECONDS);
        // update network manager about network connectivity changes
        Observable.combineLatest(getForegroundStatus(), networkConn, new Func2<Boolean, Boolean, Integer>() {
            @Override
            public Integer call(Boolean isForeground, Boolean hasInternet) {
                if (hasInternet) {
                    if (isForeground) {
                        return NetworkOpsTask.OPS_INIT_NETWORK;
                    } else {
                        return NetworkOpsTask.OPS_ENTER_BACKGROUND;
                    }
                } else {
                    return NetworkOpsTask.OPS_DISCONNECT_NETWORK;
                }
            }
        }).distinctUntilChanged().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer ops) {
                taskManager.executeAndTrigger(new NetworkOpsTask(ops));
            }
        });

        // hook up with operations to do when network is available and app is in foreground
        getForegroundStatus().switchMap(new Func1<Boolean, Observable<?>>() {
            @Override
            public Observable<?> call(Boolean isForeground) {
                if (isForeground) {
                    Observable<?> networkTaskToggle = networkConn.switchMap(new Func1<Boolean, Observable<?>>() {
                        @Override
                        public Observable<?> call(Boolean hasInternet) {
                            if (hasInternet) {
                                onNetworkConnAvailOnce();

                                return Observable.interval(NetworkConst.TIME.SECONDS.MIN, TimeUnit.MILLISECONDS)
                                        .doOnNext(new Action1<Long>() {
                                            @Override
                                            public void call(Long aLong) {
                                                onNetworkConnAvailEveryMinute();
                                            }
                                        })
                                        .onBackpressureDrop();
                            }

                            LogUtils.d("no internet, stop network tasks");
                            return Observable.empty();
                        }
                    });

                    return networkTaskToggle;
                }

                LogUtils.d("app is in background, stop network tasks");
                return Observable.empty();
            }

        }).subscribe(TaskManager.EMPTY);
    }

    public Observable<Boolean> getForegroundStatus() {
        return appLifeCycleMonitor.getForegroundStatus();
    }

    /**
     * one-off task when there is network and app is in foreground
     */
    private void onNetworkConnAvailOnce() {
        LogUtils.d("network available - start one-off tasks");
    }

    /**
     * runs every minute when there is network and app is in foreground
     */
    private void onNetworkConnAvailEveryMinute() {
        LogUtils.d("network available - repeat periodic tasks every minute %d", TimeHelper.now());
    }
}
