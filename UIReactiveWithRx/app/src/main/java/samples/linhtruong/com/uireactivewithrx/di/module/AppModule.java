package samples.linhtruong.com.uireactivewithrx.di.module;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import samples.linhtruong.com.db.DbManager;
import samples.linhtruong.com.manager.FileManager;
import samples.linhtruong.com.network.http.GsonFactory;
import samples.linhtruong.com.network.NetworkManager;
import samples.linhtruong.com.network.http.OkHttpFactory;
import samples.linhtruong.com.task.TaskManager;
import samples.linhtruong.com.task.TaskResources;
import samples.linhtruong.com.uireactivewithrx.app.App;
import samples.linhtruong.com.uireactivewithrx.app.AppLifeCycleMonitor;
import samples.linhtruong.com.uireactivewithrx.di.scope.ApplicationScope;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 3/4/17 - 00:33.
 * @organization VED
 */

@Module
public class AppModule {

    private final App mApp;
    private final FileManager mFileManager;
    private final OkHttpClient mOkHttpClient;
    private final Gson mGson;

    public AppModule(App app) {
        mApp = app;
        mFileManager = new FileManager(mApp);
        mGson = GsonFactory.build();
        mOkHttpClient = OkHttpFactory.build(mFileManager.getCacheDir());
    }

    @ApplicationScope
    @Provides
    AppLifeCycleMonitor provideLifecycleMonitor() {
        return new AppLifeCycleMonitor();
    }

    @ApplicationScope
    @Provides
    App provideApplicationContext() {
        return mApp;
    }

    @ApplicationScope
    @Provides
    NetworkManager provideNetworkManager() {
        return new NetworkManager(mApp, mOkHttpClient, mGson);
    }

    @ApplicationScope
    @Provides
    DbManager providerDbManager() {
        return new DbManager(mApp);
    }

    @ApplicationScope
    @Provides
    TaskResources provideTaskResource() {
        return new TaskResources();
    }

    @ApplicationScope
    @Provides
    TaskManager provideTaskManager() {
        return new TaskManager();
    }
}
