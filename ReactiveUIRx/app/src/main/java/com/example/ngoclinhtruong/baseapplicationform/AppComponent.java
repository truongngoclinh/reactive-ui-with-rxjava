package com.example.ngoclinhtruong.baseapplicationform;

import com.example.base.BaseComponent;
import com.example.base.task.TaskManager;
import com.example.base.task.TaskResource;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ngoclinh.truong on 6/14/16.
 */

@Singleton
@Component(
        modules = {AppModule.class}
)
public interface AppComponent extends BaseComponent {

    final class Initializer {
        public static AppComponent init(AppModule appModule) {
            return DaggerAppComponent.builder().appModule(appModule).build();
        }
    }

    void inject(TaskResource taskResource);
    void inject(TaskManager taskManager);

    TaskResource taskResource();
    TaskManager taskManager();
}
