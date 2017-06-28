package samples.linhtruong.com.uireactivewithrx.di.module;

import dagger.Module;
import dagger.Provides;
import samples.linhtruong.com.uireactivewithrx.di.MockMode;
import samples.linhtruong.com.uireactivewithrx.network.APIService;
import samples.linhtruong.com.uireactivewithrx.network.mock.MockLoginRequest;
import samples.linhtruong.com.uireactivewithrx.network.request.LoginRequest;
import samples.linhtruong.com.utils.LogUtils;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 3/4/17 - 01:52.
 * @organization VED
 */

@Module
public class LoginModule {

    @Provides
    @MockMode("prod")
    LoginRequest provideLoginRequest(APIService service) {
        LogUtils.d("[test scope] LoginModule: provideLoginRequest()");
        return new LoginRequest(service);
    }

    @Provides
    @MockMode("mock")
    LoginRequest provideMockLoginRequest(APIService service) {
        return new MockLoginRequest(service);
    }
}
