package samples.linhtruong.com.task;

import rx.Observable;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 6/29/17 - 11:12.
 * @organization VED
 */

public abstract class BaseTask<T> {

    public abstract Observable<T> execute(TaskResources res);
}
