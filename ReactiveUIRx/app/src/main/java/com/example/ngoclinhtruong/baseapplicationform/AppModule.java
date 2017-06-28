package com.example.ngoclinhtruong.baseapplicationform;

import android.content.Context;

import com.example.base.db.DbManager;
import com.example.base.res.ResourceManager;
import com.example.base.task.TaskManager;
import com.example.base.task.TaskResource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ngoclinh.truong on 6/14/16.
 */

@Module
public class AppModule {

    private final Context mContext;

    public AppModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    ResourceManager provideResourceManager() {
        return new ResourceManager(mContext);
    }

    @Provides
    @Singleton
    TaskManager provideTaskManager() {
        return new TaskManager();
    }

    @Provides
    @Singleton
    TaskResource provideTaskResource() {
        return new TaskResource();
    }

    @Provides
    @Singleton
    DbManager provideDbManger() {
        return new DbManager(mContext);
    }

}
