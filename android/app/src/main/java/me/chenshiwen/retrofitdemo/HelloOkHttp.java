package me.chenshiwen.retrofitdemo;

import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class HelloOkHttp {
    private static final String TAG = "HelloOkHttp";
    private OkHttpClient mClient;
    private Gson mGson = new Gson();
    private Handler mainHandler = new Handler();
    public HelloOkHttp(OkHttpClient client, TextView req, TextView rep) {
        mClient = client;
        mTvRep = rep;
        mTvReq = req;
    }
    private TextView mTvReq;
    private TextView mTvRep;

    public void get() {
        HttpUrl server = APiConstant.SERVER()
                .addQueryParameter("id", "1")
                .addPathSegments(APiConstant.GET_USER_NAME)
                .build();
        Log.d(TAG, "get " + server.toString());
        mTvReq.setText("get - " + server.toString());
        Request request = new Request.Builder()
                .url(server)
                .build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.d(TAG, "get fail: " + e.getMessage());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mTvRep.setText("fail - " + e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Log.d(TAG, "get success: " + response.isSuccessful());
                Log.d(TAG, "get success: " + Thread.currentThread().getName());
//                mainHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            mTvRep.setText("success - " + response.body().string());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
                mainHandler.post(() -> {
                    try {
                        mTvRep.setText("success - " + response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    private String getUserJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", 3L);
            jsonObject.put("name", "am");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public void postJson() {
        HttpUrl server = APiConstant.SERVER()
                .addPathSegments(APiConstant.POST_USER)
                .build();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Log.d(TAG, "post " + server.toString());
        Log.d(TAG, "post args -> " + getUserJson());
        mTvReq.setText("post - " + server.toString());
        RequestBody body = RequestBody.create(JSON, getUserJson());
        Request request = new Request.Builder()
                .url(server)
                .post(body)
                .build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "post json fail: " + e.getMessage());
//                mTvRep.setText("fail - " + e.getMessage());
                mainHandler.post(()-> mTvRep.setText("fail - " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "post json success: " + response.isSuccessful());
//                Log.d(TAG, "post json success: " + response.body().string());
//                mTvRep.setText("success - " + response.body().string());
                mainHandler.post(() -> {
                    try {
                        mTvRep.setText("success - " + response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    public void postForm() {
        HttpUrl server = APiConstant.SERVER()
                .addPathSegments(APiConstant.POST_USER_FORM)
                .build();
        Log.d(TAG, "post " + server.toString());
        RequestBody body = new FormBody.Builder()
                .add("id", "4")
                .add("name", "nmnm")
                .build();
        Request request = new Request.Builder()
                .url(server)
                .post(body)
                .build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "post form fail: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "post form success: " + response.isSuccessful());
                Log.d(TAG, "post form success: " + response.body().string());
            }
        });
    }

    public void postFormJson() {
        HttpUrl server = APiConstant.SERVER()
                .addPathSegments(APiConstant.POST_USER_FORM_JSON)
                .build();
        Log.d(TAG, "post " + server.toString());
        RequestBody body = new FormBody.Builder()
                .add("json", getUserJson())
                .build();
        Request request = new Request.Builder()
                .url(server)
                .post(body)
                .build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "post form json fail: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "post form json success: " + response.isSuccessful());
                Log.d(TAG, "post form json success: " + response.body().string());
            }
        });
    }

    public void postFile(File file) {
        HttpUrl server = APiConstant.SERVER()
                .addPathSegments(APiConstant.POST_FILE)
                .build();
        Log.d(TAG, "postFile " + server.toString());
        RequestBody fileBody = RequestBody.create(MediaType.get("file/*"), file);
        File f = null;
        RequestBody body = new MultipartBody.Builder()
                .addFormDataPart("file", file.getName(), fileBody)
                .addFormDataPart("name", "kkjj")
                .build();
        Request request = new Request.Builder()
                .url(server)
                .post(body)
                .build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "post file fail: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "post file success: " + response.isSuccessful());
                Log.d(TAG, "post file success: " + response.body().string());
            }
        });
    }

    public void getJson() {
        HttpUrl server = APiConstant.SERVER()
                .addPathSegments(APiConstant.GET_JSON)
                .build();
        Request request = new Request.Builder()
                .url(server)
                .get()
                .build();
        Log.d(TAG, "getJson " + server.toString());
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "get Json fail: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "get Json success: " + response.isSuccessful());
                String ret = response.body().string();
                Log.d(TAG, "get Json success: " + ret);
                User user = mGson.fromJson(ret, User.class);
                Log.d(TAG, "get Json user: " + user);
            }
        });
    }

    public void postString() {
        HttpUrl server = APiConstant.SERVER()
                .addPathSegments(APiConstant.POST_STRING)
                .build();
        RequestBody body = RequestBody.create(MediaType.parse("*/*"), "Hello World");
        Request request = new Request.Builder()
                .url(server)
                .post(body)
                .build();
        Log.d(TAG, "postString " + server.toString());
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "postString fail: " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "postString success: " + response.body().string());
            }
        });
    }

    public void postStream() {
        HttpUrl server = APiConstant.SERVER()
                .addPathSegments(APiConstant.POST_STREAM)
                .build();
        RequestBody body = new RequestBody() {
            @Override
            public MediaType contentType() {
                return MediaType.parse("text/plain"); // 任意类型
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeString("name:csw", Charset.defaultCharset());
                sink.writeString("age:10", Charset.defaultCharset());
                sink.writeInt(100);
                sink.flush();
            }
        };
        Request request = new Request.Builder()
                .url(server)
                .post(body)
                .build();
        Log.d(TAG, "postStream " + server.toString());
        mClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "postStream fail: " + e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d(TAG, "postStream success: " + response.body().string());
                    }
                });
    }
}
