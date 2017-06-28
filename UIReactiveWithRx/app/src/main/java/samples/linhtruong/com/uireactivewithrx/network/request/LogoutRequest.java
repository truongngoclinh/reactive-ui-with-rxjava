package samples.linhtruong.com.uireactivewithrx.network.request;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import samples.linhtruong.com.base.BaseHttpRequest;
import samples.linhtruong.com.base.BaseResponse;
import samples.linhtruong.com.uireactivewithrx.network.APIConfig;
import samples.linhtruong.com.uireactivewithrx.network.APIService;
import samples.linhtruong.com.uireactivewithrx.storage.LoginSession;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 3/6/17 - 10:50.
 * @organization VED
 */

public class LogoutRequest extends BaseHttpRequest<BaseResponse> {

    public APIService mService;

    public LoginSession mLoginSession;

    private Map<String, String> mData;

    public LogoutRequest(APIService service, LoginSession session) {
        mService = service;
        mLoginSession = session;
    }

    public void initData() {
        mData = new HashMap<>();
        mData.put("uid", mLoginSession.getUid());
        mData.put("access_token", mLoginSession.getToken());
    }

    @Deprecated
    @Override
    public BaseResponse getMockResponse() {
        BaseResponse mockResponse = new BaseResponse();
        mockResponse.error = APIConfig.MOCK.ERROR_NONE;
        mockResponse.result = APIConfig.MOCK.RESULT_OK;

        return mockResponse;
    }

    @Override
    protected String getURLRequest() {
        return APIConfig.BASE_URL + "/me/logout";
    }

    @Override
    protected Call<BaseResponse> call() {
        return mService.logout(mData);
    }
}
