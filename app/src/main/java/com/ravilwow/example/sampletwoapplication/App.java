package com.ravilwow.example.sampletwoapplication;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by ravikwow
 * Date: 02.11.17.
 */
public class App extends Application {
    public static final String TAG = "LOG_TAG";

    private static String getProcessName(Application app) {
        try {
            Field loadedApkField = app.getClass().getField("mLoadedApk");
            loadedApkField.setAccessible(true);
            Object loadedApk = loadedApkField.get(app);

            Field activityThreadField = loadedApk.getClass().getDeclaredField("mActivityThread");
            activityThreadField.setAccessible(true);
            Object activityThread = activityThreadField.get(loadedApk);

            Method getProcessName = activityThread.getClass().getDeclaredMethod("getProcessName", null);
            return (String) getProcessName.invoke(activityThread, null);
        } catch (Exception e) {
            int pid = android.os.Process.myPid();
            ActivityManager manager = (ActivityManager) app.getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
                if (processInfo.pid == pid) {
                    return processInfo.processName;
                }
            }
        }
        return "";
    }

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
}
