package me.chenshiwen.retrofitdemo;

import android.Manifest;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "HelloOkHttp";
    private static final int RC_WRITE_SD = 1;

    private HelloOkHttp mHelloOkHttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHelloOkHttp = new HelloOkHttp(MyApplication.getInstance().getHttpClient());

        mHelloOkHttp.get();
        mHelloOkHttp.postJson();
        mHelloOkHttp.postForm();
        mHelloOkHttp.postFormJson();

        postFile();

        mHelloOkHttp.getJson();
    }

    @AfterPermissionGranted(RC_WRITE_SD)
    private void postFile() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            // ...
            File dir = Environment.getExternalStorageDirectory();
            Log.d(TAG, "dir -> " + dir.getAbsolutePath());
            mHelloOkHttp.postFile(new File(dir, "wifi_config.log"));
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "请求读写sd",
                    RC_WRITE_SD, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
