package samples.linhtruong.com.manager;

import android.content.Context;
import android.os.Environment;
import samples.linhtruong.com.utils.LogUtils;

import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 6/29/17 - 10:21.
 * @organization VED
 */

public class FileManager {

    public static final long CACHE_DIR_SIZE = 100 * 1024 * 1024;
    public static final String DIR_CACHE = "cache";

    private Context mContext;
    private AtomicReference<File> mWriteableRoot;

    public FileManager(Context context) {
        mContext = context;
        mWriteableRoot = new AtomicReference<>();
    }

    /**
     * Return App's internal dir, no need permission to access
     */
    public File getCacheDir() {
        return getAppInternalDir(DIR_CACHE);
    }

    public File getAppInternalDir(String subDir) {
        File root = mWriteableRoot.get();
        if (root == null) {
            initRoot();
            root = mWriteableRoot.get();
        }

        if (root == null) {
            return null;
        }

        File dir = new File(root, subDir);
        if (!dir.exists()) {
            dir.mkdir();
        }

        return dir;
    }


    private void initRoot() {
        File root = mContext.getExternalFilesDir(null);
        if (root == null || !root.exists()) {
            String externalRootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            String packageName = mContext.getPackageName();
            String path = String.format("%s/Android/data/%s/files", externalRootPath, packageName);

            root = new File(path);
            if (!root.exists()) {
                if (!root.mkdir()) {
                    // fallback to internal storage
                    root = mContext.getCacheDir();
                }
            }
        }

        if (mWriteableRoot.compareAndSet(null, root)) {
            LogUtils.d("App writable dir root: " + root);
        }
    }


}
