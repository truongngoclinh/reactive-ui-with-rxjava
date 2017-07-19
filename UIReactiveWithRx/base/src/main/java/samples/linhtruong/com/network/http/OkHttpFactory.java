package samples.linhtruong.com.network.http;

import samples.linhtruong.com.NetworkConst;
import samples.linhtruong.com.manager.FileManager;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import samples.linhtruong.com.base.BuildConfig;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 6/29/17 - 10:18.
 * @organization VED
 */

public class OkHttpFactory {

    private OkHttpFactory() {
    }

    public static OkHttpClient build(File cacheDir) {
        return new OkHttpClient.Builder()
                .cache(new Cache(cacheDir, FileManager.CACHE_DIR_SIZE))
                /*.addInterceptor(new HttpUserAgentInterceptor())
                .addInterceptor(new HttpDisableCacheInterceptor())
                .addNetworkInterceptor(new HttpImageCacheInterceptor())
                .addNetworkInterceptor(new HttpTimeStatisticsInterceptor())
                .addInterceptor(new HttpDebugInterceptor())*/
                .addInterceptor(new HttpLoggingInterceptor().setLevel(
                        BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.HEADERS :
                                HttpLoggingInterceptor.Level.NONE))
                .connectTimeout(NetworkConst.HTTP_CONN_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(NetworkConst.HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(NetworkConst.HTTP_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();

    }
}
