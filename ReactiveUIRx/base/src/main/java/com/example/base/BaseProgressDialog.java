package com.example.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ngoclinh.truong on 6/16/16.
 */
public class BaseProgressDialog extends Dialog {

    private final Handler mUI;
    private final Animation mLoadingAnimation;
    private final TextView mTvMessage;
    private final ImageView mIvLoading;
    private final String mDefaultMessage;
    private OnBackPressListener mOnBackPressedListener;

    public BaseProgressDialog(Context context) {
        super(context, R.style.DialogTransparent);
        mUI = new Handler(Looper.getMainLooper());
        mLoadingAnimation = AnimationUtils.loadAnimation(context, R.anim.base_progress_anim);
        mDefaultMessage = context.getString(R.string.progress_dialog_loading);

        setContentView(R.layout.base_widget_progress_dialog);
        mTvMessage = (TextView) this.findViewById(R.id.title);
        mIvLoading = (ImageView) findViewById(R.id.loading_image);

        ViewCompat.setLayerType(mIvLoading, ViewCompat.LAYER_TYPE_SOFTWARE, null);
    }

    public void close() {
        try {
            this.dismiss();
        } catch (Exception e) {
            // fix based on http://stackoverflow.com/a/5102572/827110
        }
    }

    public void setOnBackPressedListener(OnBackPressListener listener) {
        mOnBackPressedListener = listener;
    }

    @Override
    protected void onStart() {
        mIvLoading.startAnimation(mLoadingAnimation);
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mIvLoading.clearAnimation();
    }

    public void setOperationCancelable(boolean cancel) {
        setCanceledOnTouchOutside(cancel);
        setCancelable(cancel);
    }

    @Override
    public void onBackPressed() {
        if (mOnBackPressedListener != null && mOnBackPressedListener.onPressBack()) {
            return;
        }
        super.onBackPressed();
    }

    public void setMessage(String msg) {
        mTvMessage.setText(TextUtils.isEmpty(msg) ? mDefaultMessage : msg);
    }

    @Override
    public void show() {
        mUI.removeCallbacks(DELAY_SHOW);
        mUI.postDelayed(DELAY_SHOW, 300);
    }

    @Override
    public void dismiss() {
        mUI.removeCallbacks(DELAY_SHOW);
        if (isShowing()) {
            super.dismiss();
        }
    }

    private final Runnable DELAY_SHOW = new Runnable() {
        @Override
        public void run() {
            BaseProgressDialog.super.show();
        }
    };

    public interface OnBackPressListener {
        boolean onPressBack();
    }
}
