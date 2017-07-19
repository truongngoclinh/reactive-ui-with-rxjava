package samples.linhtruong.com.uireactivewithrx.di.component;

import dagger.Component;
import samples.linhtruong.com.BaseComponent;
import samples.linhtruong.com.network.NetworkManager;
import samples.linhtruong.com.task.TaskManager;
import samples.linhtruong.com.task.TaskResources;
import samples.linhtruong.com.uireactivewithrx.app.App;
import samples.linhtruong.com.uireactivewithrx.app.AppLifeCycleManager;
import samples.linhtruong.com.uireactivewithrx.di.module.AppModule;
import samples.linhtruong.com.uireactivewithrx.di.scope.ApplicationScope;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 3/4/17 - 01:36.
 * @organization VED
 */

@ApplicationScope
@Component(modules = {AppModule.class})
public interface AppComponent extends BaseComponent {

    final class Initialiazer {
        public static AppComponent init(App app) {
            return DaggerAppComponent.builder().appModule(new AppModule(app)).build();
        }
    }

    void inject(TaskResources taskResources);
    void inject(TaskManager taskManager);
    void inject(AppLifeCycleManager lifeCycleManager);
    void inject(App app);

    TaskResources taskResources();
    TaskManager taskManager();
    NetworkManager netwrokManager();
}
