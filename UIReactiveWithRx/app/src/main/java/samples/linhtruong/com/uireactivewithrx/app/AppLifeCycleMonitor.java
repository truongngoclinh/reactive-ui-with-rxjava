package samples.linhtruong.com.uireactivewithrx.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import samples.linhtruong.com.utils.LogUtils;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 7/18/17 - 17:40.
 * @organization VED
 */

public class AppLifeCycleMonitor implements Application.ActivityLifecycleCallbacks {

    private final BehaviorSubject<Boolean> mForegroundStatus = BehaviorSubject.create(false);
    private int mForegroundCount = 0;
    public Observable<Boolean> getForegroundStatus() {
        return mForegroundStatus.distinctUntilChanged();
    }

    public boolean isForeground() {
        return mForegroundCount > 0;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        LogUtils.d(activity.getClass().getSimpleName() + " onActivityCreated" + activity.hashCode());
    }

    @Override
    public void onActivityStarted(Activity activity) {
        mForegroundCount++;
        LogUtils.d(
                activity.getClass().getSimpleName() + " onActivityStarted (foreground count: " + mForegroundCount +
                        ") " + activity.hashCode());
        if (mForegroundCount == 1) {
            onAppEnterForeground(activity);
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        mForegroundCount--;
        LogUtils.d(
                activity.getClass().getSimpleName() + " onActivityStopped (foreground count: " + mForegroundCount +
                        ") " + activity.hashCode());
        if (mForegroundCount == 0) {
            onAppEnterBackground(activity);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        LogUtils.d(activity.getClass().getSimpleName() + " onActivitySaveInstanceState " + activity.hashCode());
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        LogUtils.d(activity.getClass().getSimpleName() + " onActivitySaveInstanceState " + activity.hashCode());
    }

    private void onAppEnterForeground(Activity activity) {
        LogUtils.d("onAppEnterForeground");
        mForegroundStatus.onNext(true);
        // start analytic
    }

    private void onAppEnterBackground(Activity activity) {
        LogUtils.d("onAppEnterBackground");
        mForegroundStatus.onNext(false);
        // start analytic
    }
}
