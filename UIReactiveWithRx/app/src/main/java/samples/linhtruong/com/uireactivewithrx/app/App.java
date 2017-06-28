package samples.linhtruong.com.uireactivewithrx.app;

import android.app.Application;

import com.facebook.stetho.Stetho;
import io.realm.Realm;
import samples.linhtruong.com.uireactivewithrx.di.component.AppComponent;
import samples.linhtruong.com.uireactivewithrx.storage.DbManager;
import samples.linhtruong.com.utils.LogUtils;
import samples.linhtruong.com.utils.ScreenUtils;

import javax.inject.Inject;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 3/4/17 - 00:32.
 * @organization VED
 */

public class App extends Application {

    private static volatile AppComponent mAppComponent;

    @Inject
    DbManager mDbManager;

    @Override
    public void onCreate() {
        super.onCreate();

        if (mDbManager == null) {
            LogUtils.d("[test scope] before ... db manager is NULL");
        } else {
            LogUtils.d("[test scope] before ... db manager is OK");
        }

        initDependency();
        initUtils();

        if (mDbManager == null) {
            LogUtils.d("[test scope] after component created db manager is NULL");
        } else {
            LogUtils.d("[test scope] after component created db manager is OK");
        }
    }

    private void initDependency() {
        mAppComponent = AppComponent.Initialiazer.init(this);
    }

    private void initUtils() {
        ScreenUtils.init(this);
        Realm.init(this);

        Stetho.initializeWithDefaults(this);
    }

    public static AppComponent getAppcomponent() {
        return mAppComponent;
    }
}
