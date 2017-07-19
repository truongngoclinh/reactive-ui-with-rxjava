package samples.linhtruong.com.task;

import android.content.Context;
import android.content.ContextWrapper;
import rx.Subscription;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 6/29/17 - 16:15.
 * @organization VED
 */

public interface TaskExecutable {

    class Extractor {

        public static TaskExecutable extract(Context context) {
            if (context instanceof TaskExecutable) {
                return (TaskExecutable) context;
            }

            if (context instanceof ContextWrapper) {
                Context wrapped = ((ContextWrapper) context).getBaseContext();
                if (wrapped instanceof TaskExecutable) {
                    return (TaskExecutable) wrapped;
                }
            }

            return null;
        }
    }

    /**
     *
     * @param task task to be exectured
     * @param callback callback for the observable
     * @param trackSubscrpition true if the subscription will be disposed upon destroy of current activity/fragment
     * @param <T> return type
     * @return an subscription
     */
    <T> Subscription executeTask(BaseTask<T> task, BaseObserver<? super T> callback, boolean trackSubscrpition);
}
