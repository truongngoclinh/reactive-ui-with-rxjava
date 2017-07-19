package samples.linhtruong.com.helper;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import samples.linhtruong.com.utils.LogUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 7/18/17 - 18:38.
 * @organization VED
 */

public class TimeHelper {

    private final String mSimpleContextualDateStringFormat;
    private final DateFormat mCommonDateFormat;
    private final Context mContext;

    public TimeHelper(Context context) {
        mContext = context.getApplicationContext();
        DateFormat tempDateFormat;
        mSimpleContextualDateStringFormat = Settings.System.getString(context.getContentResolver(),
                Settings.System.DATE_FORMAT);
        if (TextUtils.isEmpty(mSimpleContextualDateStringFormat)) {
            tempDateFormat = android.text.format.DateFormat.getMediumDateFormat(context);
        } else {
            // We must be prepared for IllegalArgumentExceptions
            try {
                tempDateFormat = new SimpleDateFormat(mSimpleContextualDateStringFormat);
            } catch (IllegalArgumentException a) {
                tempDateFormat = android.text.format.DateFormat.getMediumDateFormat(context);
            }
        }
        mCommonDateFormat = tempDateFormat;
    }

    public static void syncTo(int currentTimeSeconds) {
        if (currentTimeSeconds <= 0) {
            return;
        }
        int now = nowAbsolute();
        SYNC_DIFF = (currentTimeSeconds - now) * 1000;
        LogUtils.d("sync current time to: %d, diff: %d", currentTimeSeconds, SYNC_DIFF);
    }

    public static long millNowAbsolute() {
        return System.currentTimeMillis();
    }

    public static int nowAbsolute() {
        return (int) (millNowAbsolute() / 1000);
    }

    /**
     * the difference in milliseconds to sync between local time and server time
     */
    private static volatile long SYNC_DIFF = 0;

    /**
     * Warning: returned current time might be adjusted by diff defined by {@link #syncTo(int)}. For unbiased
     * current time, use {@link #nowAbsolute()}
     *
     * @return time in seconds
     */
    public static int now() {
        return (int) (millNow() / 1000);
    }

    /**
     * Warning: returned current time might be adjusted by diff defined by {@link #syncTo(int)}. For unbiased
     * current time, use {@link #millNowAbsolute()}
     *
     * @return time in seconds
     */
    public static long millNow() {
        return System.currentTimeMillis() + SYNC_DIFF;
    }
}
