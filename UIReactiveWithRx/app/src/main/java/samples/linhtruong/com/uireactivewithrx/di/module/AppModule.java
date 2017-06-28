package samples.linhtruong.com.uireactivewithrx.di.module;

import dagger.Module;
import dagger.Provides;
import samples.linhtruong.com.uireactivewithrx.app.App;
import samples.linhtruong.com.uireactivewithrx.di.scope.ApplicationScope;
import samples.linhtruong.com.uireactivewithrx.storage.DbManager;
import samples.linhtruong.com.uireactivewithrx.storage.LoginSession;
import samples.linhtruong.com.utils.LogUtils;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 3/4/17 - 00:33.
 * @organization VED
 */

@Module
public class AppModule {

    private final App mApp;

    public AppModule(App app) {
        mApp = app;
    }

    @ApplicationScope
    @Provides
    App provideApplicationContext() {
        return mApp;
    }

    @Provides
    @ApplicationScope
    DbManager provideDBManager() {
        LogUtils.d("[test scope] Appmodule: provideDBManager()");
        return new DbManager(mApp);
    }

    @ApplicationScope
    @Provides
    LoginSession provideLoginSession() {
        return new LoginSession(mApp);
    }
}
