package me.chenshiwen.retrofitdemo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.util.Log;

import java.util.List;

import okhttp3.OkHttpClient;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    private OkHttpClient mOkHttpClient;
    private static MyApplication sMyApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        sMyApplication = this;
        String processName = getCurrentProcessName();
        if (getPackageName().equals(processName)) {
            initOkHttpClient();
        }
    }

    private void initOkHttpClient() {
        mOkHttpClient = new OkHttpClient.Builder()
                .build();
    }

    public OkHttpClient getHttpClient() {
        return mOkHttpClient;
    }

    private String getCurrentProcessName() {
        int pid = Process.myPid();
        Log.d(TAG, "当前pid: " + pid);
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos =
                activityManager.getRunningAppProcesses();
        if (processInfos != null) {
            for(ActivityManager.RunningAppProcessInfo info: processInfos) {
                if (info.pid == pid) {
                    return info.processName;
                }
            }
        } else {
            Log.d(TAG, "processInfos is null");
        }
        return null;
    }

    public static MyApplication getInstance() {
        return sMyApplication;
    }
}
