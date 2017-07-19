package samples.linhtruong.com.network.http;

import android.text.TextUtils;
import retrofit2.Retrofit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 7/18/17 - 17:57.
 * @organization VED
 */

public class HttpManager {

    private final RetrofitFactory mFactory;
    private final Map<String, Retrofit> mRetroCache;
    private final Map<HttpServiceDescriptor, Object> mServiceCache;

    public HttpManager(RetrofitFactory factory) {
        mFactory = factory;
        mRetroCache = new ConcurrentHashMap<>();
        mServiceCache = new ConcurrentHashMap<>();
    }

    @SuppressWarnings("unchecked")
    public <T> T getService(HttpServiceDescriptor<T> descriptor) {
        if (descriptor == null) {
            throw new IllegalArgumentException("descriptor cannot be null");
        }

        Object service = mServiceCache.get(descriptor);
        if (service == null) {
            synchronized (this) {
                service = mServiceCache.get(descriptor);
                if (service == null) {
                    service = createService(descriptor);
                    mServiceCache.put(descriptor, service);
                }
            }
        }
        return (T) service;
    }

    private <T> T createService(HttpServiceDescriptor<T> descriptor) {
        String url = descriptor.getBaseUrl();
        if (TextUtils.isEmpty(url)) {
            throw new IllegalArgumentException("undefined base url in descriptor");
        }

        Retrofit retrofit = mRetroCache.get(url);
        if (retrofit == null) {
            retrofit = mFactory.createRetrofit(url);
            mRetroCache.put(url, retrofit);
        }

        return retrofit.create(descriptor.getServiceClass());
    }
}
