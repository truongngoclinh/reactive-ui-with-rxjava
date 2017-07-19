package samples.linhtruong.com;

import android.support.v7.app.AppCompatActivity;
import rx.Subscription;
import samples.linhtruong.com.task.TaskExecutable;
import samples.linhtruong.com.task.BaseObserver;
import samples.linhtruong.com.task.BaseTask;
import samples.linhtruong.com.task.TaskManager;

import javax.inject.Inject;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 6/29/17 - 09:56.
 * @organization VED
 */

public class BaseActivity extends AppCompatActivity implements TaskExecutable {

    @Inject
    TaskManager mTaskManager;

    @Override
    public <T> Subscription executeTask(BaseTask<T> task, BaseObserver<? super T> callback, boolean trackSubscrpition) {
        return null;
    }
}
