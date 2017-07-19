package samples.linhtruong.com.uireactivewithrx.app;

import android.content.res.Configuration;
import com.facebook.stetho.Stetho;
import samples.linhtruong.com.BaseApplication;
import samples.linhtruong.com.helper.AppLogger;
import samples.linhtruong.com.uireactivewithrx.BuildConfig;
import samples.linhtruong.com.uireactivewithrx.di.component.AppComponent;
import samples.linhtruong.com.utils.ScreenUtils;

import javax.inject.Inject;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 3/4/17 - 00:32.
 * @organization VED
 */

public class App extends BaseApplication {

    @Inject
    AppLifeCycleMonitor lifeCycleMonitor;

    @Inject
    AppLifeCycleManager lifeCycleManager;

    @Override
    public void onCreate() {
        super.onCreate();

        initDependency();
        initUtils();
        initLogger();
        initModules();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //
    }

    private void initLogger() {
        AppLogger.RELEASE = !BuildConfig.DEBUG;
    }

    private void initModules() {
        registerActivityLifecycleCallbacks(lifeCycleMonitor);

//        AppComponent component = (AppComponent) getComponent();
        if (isMainProcess()) {
            lifeCycleManager.onCreate();
        }
    }

    private void initDependency() {
        AppComponent component = AppComponent.Initialiazer.init(this);
        setComponent(component);

        component.inject(component.taskManager());
        component.inject(component.taskResources());

        component.inject(this);
        component.inject(lifeCycleManager);
    }

    private void initUtils() {
        ScreenUtils.init(this);

        // 3rd lib
        Stetho.initializeWithDefaults(this);
    }
}
