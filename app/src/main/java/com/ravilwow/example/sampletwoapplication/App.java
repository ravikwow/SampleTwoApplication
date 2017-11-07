package com.ravilwow.example.sampletwoapplication;

import android.app.Application;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by ravikwow
 * Date: 02.11.17.
 */
public class App extends Application {
    public static final String TAG = "LOG_TAG";

    @Override
    public void onCreate() {
        super.onCreate();
        String processName = getProcessName(this);
        processName = processName.replace(getApplicationInfo().processName, "");
        if (":service".equals(processName)) {
            Log.d(TAG, "App.onCreate: service " + this);
        } else {
            Log.d(TAG, "App.onCreate: general " + this);
        }
    }

    private String getProcessName(Application app) {
        String processName = null;
        try {
            Field loadedApkField = app.getClass().getField("mLoadedApk");
            loadedApkField.setAccessible(true);
            Object loadedApk = loadedApkField.get(app);

            Field activityThreadField = loadedApk.getClass().getDeclaredField("mActivityThread");
            activityThreadField.setAccessible(true);
            Object activityThread = activityThreadField.get(loadedApk);

            Method getProcessName = activityThread.getClass().getDeclaredMethod("getProcessName", null);
            processName = (String) getProcessName.invoke(activityThread, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return processName;
    }
}
