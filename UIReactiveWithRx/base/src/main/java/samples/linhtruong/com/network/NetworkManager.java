package samples.linhtruong.com.network;

import android.content.Context;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import rx.Observable;
import samples.linhtruong.com.network.http.HttpFactory;
import samples.linhtruong.com.network.http.HttpManager;
import samples.linhtruong.com.network.http.HttpServiceDescriptor;
import samples.linhtruong.com.utils.LogUtils;
import samples.linhtruong.com.utils.NetworkUtil;

import java.util.concurrent.Callable;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 6/29/17 - 10:15.
 * @organization VED
 */

public class NetworkManager {

    private final Context mContext;
    private final HttpManager mHttpManager;

    public NetworkManager(Context context, OkHttpClient client, Gson gson) {
        mContext = context;
        mHttpManager = new HttpManager(new HttpFactory(client, gson));
    }

    public <T> T httpService(HttpServiceDescriptor<T> descriptor) {
        return mHttpManager.getService(descriptor);
    }

    public Observable<Boolean> init() {
        LogUtils.d("init network");

        final boolean networkAvailable = NetworkUtil.check().isConnected();
        if (!networkAvailable) {
            LogUtils.d("no internet connection");
            return Observable.just(false);
        }

        // init tcp, udp connection
        return Observable.just(true);
    }

    public Observable<Boolean> disconnect() {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                // disconnect tcp, udp
                return true;
            }
        });
    }
}
