package samples.linhtruong.com.uireactivewithrx.di.component;

import dagger.Component;
import samples.linhtruong.com.uireactivewithrx.login.LoginActivity;
import samples.linhtruong.com.uireactivewithrx.login.LoginPresenter;
import samples.linhtruong.com.uireactivewithrx.di.module.LoginModule;
import samples.linhtruong.com.uireactivewithrx.network.request.LoginRequest;
import samples.linhtruong.com.uireactivewithrx.di.scope.LoginScope;
import samples.linhtruong.com.utils.LogUtils;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 3/4/17 - 01:50.
 * @organization VED
 */

@LoginScope
@Component(
        modules = LoginModule.class,
        dependencies = AppComponent.class )
public interface LoginComponent {

    final class Initializer {
        public static LoginComponent init(AppComponent appComponent) {
            LogUtils.d("[test scope] init LoginComponent");
            return DaggerLoginComponent.builder().appComponent(appComponent).build();
        }
    }

    void inject(LoginActivity activity);
    void inject(LoginPresenter presenter);
    void inject(LoginRequest request);
}
