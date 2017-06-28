package samples.linhtruong.com.uireactivewithrx.di.component;

import android.content.Context;

import dagger.Component;
import samples.linhtruong.com.uireactivewithrx.home.HomeTabActivity;
import samples.linhtruong.com.uireactivewithrx.home.tabs.HomeMePresenter;
import samples.linhtruong.com.uireactivewithrx.home.tabs.HomeReportPresenter;
import samples.linhtruong.com.uireactivewithrx.di.module.UserModule;
import samples.linhtruong.com.uireactivewithrx.di.scope.UserScope;
import samples.linhtruong.com.uireactivewithrx.network.request.LogoutRequest;
import samples.linhtruong.com.uireactivewithrx.network.request.UserInfoRequest;
import samples.linhtruong.com.uireactivewithrx.network.request.UserTransactionListRequest;
import samples.linhtruong.com.utils.LogUtils;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 3/5/17 - 00:09.
 * @organization VED
 */

@UserScope
@Component(
        modules = UserModule.class,
        dependencies = {AppComponent.class}
)
public interface UserComponent {

    final class Initializer {
        public static UserComponent init(AppComponent appComponent, Context context) {
            LogUtils.d("[test scope] init UserComponent");
            return DaggerUserComponent.builder().appComponent(appComponent).userModule(new UserModule(context)).build();
        }
    }

    void inject(HomeTabActivity activity);
    void inject(HomeMePresenter presenter);
    void inject(HomeReportPresenter presenter);
    void inject(UserInfoRequest request);
    void inject(UserTransactionListRequest request);
    void inject(LogoutRequest request);
}
