package samples.linhtruong.com.uireactivewithrx.network.mock;

import samples.linhtruong.com.uireactivewithrx.network.APIConfig;
import samples.linhtruong.com.uireactivewithrx.network.APIService;
import samples.linhtruong.com.uireactivewithrx.network.request.LoginRequest;
import samples.linhtruong.com.utils.LogUtils;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 4/6/17 - 13:47.
 * @organization VED
 */

public class MockLoginRequest extends LoginRequest {

    public MockLoginRequest(APIService service) {
        super(service);
    }

    @Override
    public LoginResponse getResponse() {
        LoginResponse mockResponse = new LoginResponse();
        mockResponse.error = APIConfig.MOCK.ERROR_NONE;
        mockResponse.result = APIConfig.MOCK.RESULT_OK;
        mockResponse.accessToken = APIConfig.MOCK_LOGIN.TOKEN;

        if (mData.get("account").contains("1")) {
            mockResponse.uid = APIConfig.MOCK_USER1.UID;
        } else {
            mockResponse.uid = APIConfig.MOCK_USER2.UID;
        }

        LogUtils.d("get mock result: " + mockResponse.uid);

        return mockResponse;
    }
}
