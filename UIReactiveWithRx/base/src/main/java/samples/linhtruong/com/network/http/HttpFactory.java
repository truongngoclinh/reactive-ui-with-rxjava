package samples.linhtruong.com.network.http;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import samples.linhtruong.com.task.TaskSchedulers;

public class HttpFactory implements RetrofitFactory {

    private volatile GsonConverterFactory mGsonFactory;
    private volatile RxJavaCallAdapterFactory mRxFactory;

    private final OkHttpClient mClient;
    private final Gson mGson;

    public HttpFactory(OkHttpClient client, Gson gson) {
        mClient = client;
        mGson = gson;
    }

    @Override
    public Retrofit createRetrofit(String baseUrl) {
        if (mGsonFactory == null) {
            synchronized (this) {
                if (mGsonFactory == null) {
                    mGsonFactory = createGsonFactory();
                }
            }
        }

        if (mRxFactory == null) {
            synchronized (this) {
                if (mRxFactory == null) {
                    mRxFactory = createRxFactory();
                }
            }
        }

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mClient)
                .addConverterFactory(mGsonFactory)
                .addCallAdapterFactory(mRxFactory)
                .build();
    }

    private GsonConverterFactory createGsonFactory() {
        return GsonConverterFactory.create(mGson);
    }

    private RxJavaCallAdapterFactory createRxFactory() {
        return RxJavaCallAdapterFactory.createWithScheduler(TaskSchedulers.IO);
    }
}
