package samples.linhtruong.com.network.http;

import retrofit2.Retrofit;

/**
 * Created by yangbo on 28/4/16.
 */
public interface RetrofitFactory {

    Retrofit createRetrofit(String baseUrl);
}
