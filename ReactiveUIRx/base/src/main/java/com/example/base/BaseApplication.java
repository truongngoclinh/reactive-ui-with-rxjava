package com.example.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.example.utils.LogUtils;

/**
 * Created by ngoclinh.truong on 6/14/16.
 */
public class BaseApplication extends Application {

    private String mProcessName;
    private BaseComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mProcessName = getCurrentProcess();
    }

    protected String getCurrentProcess() {
        String currentProcessName = "";
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == pid) {
                LogUtils.d("loop %d %s", processInfo.pid, processInfo.processName);
                int index = processInfo.processName.indexOf(':');
                if (index > 0) {
                    currentProcessName = processInfo.processName.substring(index);
                }
                break;
            }
        }
        LogUtils.d("current process %s", currentProcessName);
        return currentProcessName;
    }

    public BaseComponent getComponent() {
        return mComponent;
    }

    public void setmComponent(BaseComponent mComponent) {
        this.mComponent = mComponent;
    }
}
