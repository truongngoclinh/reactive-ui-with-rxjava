package samples.linhtruong.com.helper;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author amulya
 * @datetime 16 Apr 2014, 7:26 PM
 */
public final class AppLogger {
    /**
     * This value must be set in the same process
     */
    public static boolean RELEASE = true;
    private static final String APP_LOG_FLAG = "GPNS";
    private static Context mContext;

    private static final SimpleDateFormat format = new SimpleDateFormat("MMM-dd-yyyy HH:mm:ss", Locale.ENGLISH);

    public static void init(Context context) {
        mContext = context;
    }

    public static void e(Throwable e) {
        if(!RELEASE) {
            Log.e(APP_LOG_FLAG, e.getMessage(), e);
        }
    }

    public static void i(String s) {
        if(!RELEASE) {
            Log.i(APP_LOG_FLAG, s);
        }
    }

    public static void f(String s) {
        if (!RELEASE) {
            appendLog(formatLog(s));
        }
    }

    private static String formatLog(String event) {
            return format.format(new Date()) + ";" + event;
    }

    private static void appendLog(String text) {
        if (mContext == null) {
            return;
        }

        /**
         * To support Android M, we shift the log directory to the internal cache directory
         */
        File logFile = new File(mContext.getCacheDir() + "/log_gpns.txt");
        i("cache directory "+mContext.getCacheDir());
        boolean isExists = logFile.exists();
        if (!isExists) {
            try {
                isExists = logFile.createNewFile();
            }
            catch (IOException e) {
                e(e);
            }
        }
        if (!isExists) {
            return;
        }

        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            e(e);
        }
    }

    public static void d(String s) {
        if(!RELEASE) {
            Log.d(APP_LOG_FLAG, s);
        }
    }

    private AppLogger() {} // private constructor for utility class


    public static abstract class NetworkThreadExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            if (!RELEASE) {
                AppLogger.i("NETWORK THREAD CRASH");
                AppLogger.e(ex);
            }
        }
    }
}
