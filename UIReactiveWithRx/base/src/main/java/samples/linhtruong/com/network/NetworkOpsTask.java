package samples.linhtruong.com.network;

import rx.Observable;
import samples.linhtruong.com.task.BaseTask;
import samples.linhtruong.com.task.TaskResources;
import samples.linhtruong.com.utils.LogUtils;

/**
 * Created by bo on 18/2/16.
 */
public class NetworkOpsTask extends BaseTask<Boolean> {

    public static final int OPS_INIT_NETWORK = 1;
    public static final int OPS_DISCONNECT_NETWORK = 2;
    public static final int OPS_ENTER_BACKGROUND = 3;

    private final int ops;

    public NetworkOpsTask(int ops) {
        this.ops = ops;
    }

    @Override
    public Observable<Boolean> execute(final TaskResources res) {
        switch (ops) {
            case OPS_INIT_NETWORK:
                LogUtils.d("init network");
                return res.networkManager.init();
            case OPS_DISCONNECT_NETWORK:
                LogUtils.d("disconnect network");
                return res.networkManager.disconnect();
            case OPS_ENTER_BACKGROUND:
                // retry connect
                return Observable.empty();
            default:
                return Observable.empty();
        }
    }
}
