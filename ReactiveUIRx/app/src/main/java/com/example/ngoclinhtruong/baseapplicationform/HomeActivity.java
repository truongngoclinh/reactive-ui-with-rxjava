package com.example.ngoclinhtruong.baseapplicationform;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.base.BaseActivity;
import com.example.base.task.DataObserver;
import com.example.databases.schema.User;
import com.example.ngoclinhtruong.baseapplicationform.task.AddRecordTask;
import com.example.ngoclinhtruong.baseapplicationform.task.DeleteRecordTask;
import com.example.ngoclinhtruong.baseapplicationform.task.UpdateRecordTask;
import com.example.ngoclinhtruong.baseapplicationform.test.TabActivity_;
import com.example.utils.LogUtils;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class HomeActivity extends BaseActivity {

    @ViewById(R.id.add_record)
    Button mAdd;

    @ViewById(R.id.remove_record)
    Button mRecord;

    @ViewById(R.id.update_record)
    Button mUpdate;

    @ViewById(R.id.list_all_record)
    RecyclerView mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        TabActivity_.intent(this).start();
    }

    @Click(R.id.add_record)
    void add() {
        executeTask(new AddRecordTask(), new DataObserver<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }, false);
        LogUtils.d("add a record!");
    }

    @Click(R.id.remove_record)
    void remove() {
        long deleteId = 0;
        executeTask(new DeleteRecordTask(deleteId), new DataObserver<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }, false);
        LogUtils.d("remove a record!");
    }

    @Click(R.id.update_record)
    void update() {
        long updateId = 0;
        String newName = "test";
        executeTask(new UpdateRecordTask(newName, updateId), new DataObserver<User>() {
            @Override
            public void onNext(User user) {
                super.onNext(user);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }, false);
        LogUtils.d("update a record!");
    }


}
