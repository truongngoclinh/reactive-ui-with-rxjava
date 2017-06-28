package samples.linhtruong.com.uireactivewithrx.test;

import android.os.Bundle;
import samples.linhtruong.com.uireactivewithrx.di.MockMode;
import samples.linhtruong.com.uireactivewithrx.network.request.LoginRequest;
import samples.linhtruong.com.uireactivewithrx.storage.DbManager;
import samples.linhtruong.com.uireactivewithrx.storage.UserStore;
import samples.linhtruong.com.uireactivewithrx.utils.base.BaseActionActivity;

import javax.inject.Inject;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 5/26/17 - 15:15.
 * @organization VED
 */

public class ChildActivity extends BaseActionActivity {

    @Inject
    @MockMode("mock")
    UserStore mUserStore;

    @Inject
    @MockMode("mock")
    DbManager mDbManager;

    @Inject
    @MockMode("mock")
    LoginRequest mLoginRequest;

    @Override
    protected void initDependency() {

    }

    @Override
    protected void onCreateContent(Bundle bundle) {

    }
}
