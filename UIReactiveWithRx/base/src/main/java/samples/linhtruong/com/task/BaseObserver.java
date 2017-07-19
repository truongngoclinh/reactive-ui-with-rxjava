package samples.linhtruong.com.task;

import rx.Observer;
import samples.linhtruong.com.utils.LogUtils;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 6/29/17 - 16:08.
 * @organization VED
 */

public class BaseObserver<T> implements Observer {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        LogUtils.e(e.getMessage());
    }

    @Override
    public void onNext(Object o) {

    }
}
