package samples.linhtruong.com.uireactivewithrx.di.component;

import dagger.Component;
import samples.linhtruong.com.uireactivewithrx.app.App;
import samples.linhtruong.com.uireactivewithrx.login.SplashActivity;
import samples.linhtruong.com.uireactivewithrx.di.module.AppModule;
import samples.linhtruong.com.uireactivewithrx.di.module.NetworkModule;
import samples.linhtruong.com.uireactivewithrx.network.APIService;
import samples.linhtruong.com.uireactivewithrx.di.scope.ApplicationScope;
import samples.linhtruong.com.uireactivewithrx.storage.DbManager;
import samples.linhtruong.com.uireactivewithrx.storage.LoginSession;
import samples.linhtruong.com.utils.LogUtils;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 3/4/17 - 01:36.
 * @organization VED
 */

@ApplicationScope
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

    final class Initialiazer {
        public static AppComponent init(App app) {
            LogUtils.d("[test scope] init AppComponent");
            return DaggerAppComponent.builder().appModule(new AppModule(app)).build();
        }
    }

    void inject(SplashActivity activity);

    APIService exposeAPIService();
    DbManager exposeDBManager();
    LoginSession exposeLoginSession();
}
