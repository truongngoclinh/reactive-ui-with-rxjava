package com.example.ngoclinhtruong.baseapplicationform;

import com.example.base.BaseApplication;

/**
 * Created by ngoclinh.truong on 6/14/16.
 */
public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initDependencies();
    }

    private void initDependencies() {
        AppComponent component = AppComponent.Initializer.init(new AppModule(this));
        setmComponent(component);

        component.inject(component.taskManager());
        component.inject(component.taskResource());
    }
}
