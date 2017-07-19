package samples.linhtruong.com;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import samples.linhtruong.com.utils.LogUtils;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 6/29/17 - 09:55.
 * @organization VED
 */

public class BaseApplication extends Application {

    private volatile static BaseComponent sComponent;
    private String mProcessName;

    protected void setComponent(BaseComponent component) {
        sComponent = component;
    }

    protected static BaseComponent getComponent() {
        return sComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mProcessName = getCurrentProcess();
    }

    protected boolean isMainProcess() {
        return TextUtils.isEmpty(mProcessName);
    }

    protected String getCurrentProcess() {
        String currentProcessName = "";
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == pid) {
                LogUtils.d("loop %d %s", processInfo.pid, processInfo.processName);
                int index = processInfo.processName.indexOf(':');
                if(index>0) {
                    currentProcessName = processInfo.processName.substring(index);
                }
                break;
            }
        }
        LogUtils.d("current process %s" ,currentProcessName);
        return currentProcessName;
    }
}
