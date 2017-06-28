package com.example.base.db;

import com.example.base.db.notify.UpdateNotify;
import com.example.utils.LogUtils;

import java.util.LinkedHashMap;
import java.util.Map;

import io.realm.Realm;
import rx.subjects.Subject;

/**
 * Created by ngoclinh.truong on 6/16/16.
 */
public abstract class RxUpdate<T> extends Update<T> {

    private Subject<UpdateNotify, UpdateNotify> mNotifySubject;
    private LinkedHashMap<Class<? extends UpdateNotify>, UpdateNotify> mNotifies;

    public void setNotifySubject(Subject<UpdateNotify, UpdateNotify> subject) {
        mNotifySubject = subject;
    }


    @Override
    protected T execute(Realm db) {
        return null;
    }

    /**
     * Add notify which will be published to downstream upon transaction is successfully committed.
     * Notifies will be automatically merged if mergeable, and discarded otherwise if duplicates are found.
     *
     * @param notify
     */
    protected final void addNotify(UpdateNotify notify) {
        if (notify == null) {
            return;
        }

        if (mNotifies == null) {
            mNotifies = new LinkedHashMap<>();
        }

        UpdateNotify existing = mNotifies.get(notify.getClass());
        if (existing == null) {
            LogUtils.d("add notify: %s ", notify);
            mNotifies.put(notify.getClass(), notify);
        } else {
            LogUtils.d("discard duplicate notify: %s", notify);
        }

    }

    protected final void removeNotify(Class<? extends  UpdateNotify> notifyClass) {
        if (mNotifies == null) {
            return;
        }

        mNotifies.remove(notifyClass);
        LogUtils.d("remove notify: %s ", notifyClass.getSimpleName());
    }

    @Override
    protected void onPostCommit() {
        if (mNotifySubject != null && mNotifies != null) {
            for (Map.Entry<Class<? extends UpdateNotify>, UpdateNotify> entry : mNotifies.entrySet()) {
                UpdateNotify notify = entry.getValue();
                LogUtils.d("%s sending notify %s", TAG, notify);
                mNotifySubject.onNext(notify);
            }
        }
    }
}
