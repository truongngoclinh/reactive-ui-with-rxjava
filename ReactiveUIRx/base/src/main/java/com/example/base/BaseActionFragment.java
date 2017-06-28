package com.example.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ngoclinh.truong on 6/16/16.
 */
public abstract class BaseActionFragment extends BaseFragment {

    private BaseActionView mContentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = onCreateContentView(savedInstanceState);

        Toolbar toolbar = mContentView.getToolbar();
        Activity activity = getActivity();
        if (activity instanceof AppCompatActivity) {
            ((AppCompatActivity) activity).setSupportActionBar(toolbar);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        onConfigToolbar(toolbar);
        return mContentView;
    }

    protected abstract BaseActionView onCreateContentView(Bundle savedInstanceState);

    protected abstract void onConfigToolbar(Toolbar toolbar);

    public Toolbar getToolbar() {
        return mContentView != null ? mContentView.getToolbar() : null;
    }

    protected void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Activity activity = getActivity();
            if (activity != null) {
                activity.getWindow().setStatusBarColor(color);
            }
        }
    }

    protected void setStatusBarColorRes(@ColorRes int colorRes) {
        setStatusBarColor(getResources().getColor(colorRes));
    }
}
