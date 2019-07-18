package me.chenshiwen.retrofitdemo;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

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

public class HelloOkHttp {
    private static final String TAG = "HelloOkHttp";
    private OkHttpClient mClient;
    private Gson mGson = new Gson();
    public HelloOkHttp(OkHttpClient client) {
        mClient = client;
    }

    public void get() {
        HttpUrl server = APiConstant.SERVER()
                .addQueryParameter("id", "1")
                .addPathSegments(APiConstant.GET_USER_NAME)
                .build();
        Log.d(TAG, "get " + server.toString());
        Request request = new Request.Builder()
                .url(server)
                .build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "get fail: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "get success: " + response.isSuccessful());
                Log.d(TAG, "get success: " + response.body().string());
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
        RequestBody body = RequestBody.create(JSON, getUserJson());
        Request request = new Request.Builder()
                .url(server)
                .post(body)
                .build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "post json fail: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "post json success: " + response.isSuccessful());
                Log.d(TAG, "post json success: " + response.body().string());
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
        Log.d(TAG, "post " + server.toString());
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
}
