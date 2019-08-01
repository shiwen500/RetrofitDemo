package me.chenshiwen.retrofitdemo;

import android.Manifest;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "HelloOkHttp";
    private static final int RC_WRITE_SD = 1;

    private HelloOkHttp mHelloOkHttp;
    private TextView mTvReq;
    private TextView mTvRep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mHelloOkHttp.get();
//        mHelloOkHttp.postJson();
//        mHelloOkHttp.postForm();
//        mHelloOkHttp.postFormJson();

//        postFile();

//        mHelloOkHttp.getJson();
//        mHelloOkHttp.postString();
//        mHelloOkHttp.postStream();
        initButton();
        mHelloOkHttp = new HelloOkHttp(MyApplication.getInstance().getHttpClient(), mTvReq, mTvRep);
    }

    private void initButton() {
        findViewById(R.id.get).setOnClickListener(this);
        findViewById(R.id.postJson).setOnClickListener(this);
        findViewById(R.id.postForm).setOnClickListener(this);
        findViewById(R.id.postFormJson).setOnClickListener(this);
        findViewById(R.id.postFile).setOnClickListener(this);
        findViewById(R.id.getJson).setOnClickListener(this);
        findViewById(R.id.postString).setOnClickListener(this);
        findViewById(R.id.postStream).setOnClickListener(this);
        mTvRep = findViewById(R.id.rep);
        mTvReq = findViewById(R.id.req);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get:
                mHelloOkHttp.get();
                break;
            case R.id.postJson:
                mHelloOkHttp.postJson();
                break;
            case R.id.postForm:
                mHelloOkHttp.postForm();
                break;
            case R.id.postFormJson:
                mHelloOkHttp.postFormJson();
                break;
            case R.id.postFile:
                postFile();
                break;
            case R.id.getJson:
                mHelloOkHttp.getJson();
                break;
            case R.id.postString:
                mHelloOkHttp.postString();
                break;
            case R.id.postStream:
                mHelloOkHttp.postStream();
                break;
        }
    }

    @AfterPermissionGranted(RC_WRITE_SD)
    private void postFile() {
        Log.d(TAG, "postFile -> ");
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
