package com.example.base;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by ngoclinh.truong on 6/16/16.
 */
public abstract class BaseActionView extends BaseView {

    protected enum ActionBarMode {
        Normal, Floating, Spinner
    }

    private final Toolbar mToolbar;
    private final FrameLayout mContainer;

    public BaseActionView(Context context) {
        super(context);
        int layoutRes = getLayoutRes();
        inflate(getContext(), layoutRes, this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mContainer = (FrameLayout) findViewById(R.id.content_layout);
        mContainer.addView(createContentView(context));
    }

    protected abstract View createContentView(Context context);

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public FrameLayout getContentContainer() {
        return mContainer;
    }

    private int getLayoutRes() {
        switch (getActionBarMode()) {
            case Floating:
                return R.layout.layout_float_toolbar;
            case Spinner:
                return R.layout.layout_spinner_toolbar;
            default:
                return R.layout.layout_base_toolbar;
        }
    }

    protected ActionBarMode getActionBarMode() {
        return ActionBarMode.Normal;
    }
}
