package me.chenshiwen.retrofitdemo;

import android.util.Log;

import okhttp3.HttpUrl;

public class APiConstant {
    private static final String TAG = "APiConstant";
    public static HttpUrl.Builder SERVER() {
        return new HttpUrl.Builder()
                .scheme("http")
                .host("172.16.85.180")
                .port(8088);
    }
    public static String GET_USER_NAME = "users/userName";
    public static String POST_USER = "users/addUser";
    public static String POST_USER_FORM = "users/addUserForm";
    public static String POST_USER_FORM_JSON = "users/addUserFormJson";
    public static String POST_FILE = "users/postFile";
    public static String GET_JSON = "users/getJson";
    public static String POST_STRING = "users/postString";
    public static String POST_STREAM = "users/postStream";
}
