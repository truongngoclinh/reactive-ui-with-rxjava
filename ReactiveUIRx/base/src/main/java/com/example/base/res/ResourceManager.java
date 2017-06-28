package com.example.base.res;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.ArrayRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.StringRes;

/**
 * Created by ngoclinh.truong on 6/14/16.
 */
public class ResourceManager {

    private final Context mContext;

    public ResourceManager(Context context) {
        mContext = context.getApplicationContext();
    }

    public String string(@StringRes int res) {
        return mContext.getString(res);
    }

    public String string(@StringRes int res, Object... args) {
        return mContext.getString(res, args);
    }

    public String[] stringArr(@ArrayRes int res) {
        return mContext.getResources().getStringArray(res);
    }

    public int integer(@IntegerRes int res) {
        return mContext.getResources().getInteger(res);
    }

    public int dimen(@DimenRes int res) {
        return mContext.getResources().getDimensionPixelSize(res);
    }

    public Bitmap bitmap(@DrawableRes int res) {
        return BitmapFactory.decodeResource(mContext.getResources(), res);
    }

}
