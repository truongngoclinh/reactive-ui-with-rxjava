package samples.linhtruong.com.uireactivewithrx.network.mock;

import samples.linhtruong.com.base.BaseResponse;
import samples.linhtruong.com.uireactivewithrx.network.APIConfig;
import samples.linhtruong.com.uireactivewithrx.network.APIService;
import samples.linhtruong.com.uireactivewithrx.network.request.LogoutRequest;
import samples.linhtruong.com.uireactivewithrx.storage.LoginSession;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 4/6/17 - 13:48.
 * @organization VED
 */

public class MockLogoutRequest extends LogoutRequest {

    public MockLogoutRequest(APIService service, LoginSession session) {
        super(service, session);
    }

    @Override
    public BaseResponse getResponse() {
        BaseResponse mockResponse = new BaseResponse();
        mockResponse.error = APIConfig.MOCK.ERROR_NONE;
        mockResponse.result = APIConfig.MOCK.RESULT_OK;

        return mockResponse;
    }
}
