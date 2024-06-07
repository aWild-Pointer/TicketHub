package com.example.tickethub.Utils;

import android.os.Handler;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;

public class ApiClient {
    private OkHttpClient client = new OkHttpClient();


    public void postData(Map<String, String> data, String url, final ApiCallback callback) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            formBuilder.add(entry.getKey(), entry.getValue());
        }

        RequestBody requestBody = formBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 请求失败时调用 onFailure 回调
                e.printStackTrace();
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    //请求成功时调用 onSuccess 回调
                    String responseData = response.body().string();
                    callback.onSuccess(responseData);
                } else {
                    callback.onFailure(new IOException("Unexpected code " + response));
                }
            }
        });
    }

    public void getData(String url, ApiCallback callback) {
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().string());
                } else {
                    callback.onFailure(new IOException("Unexpected code " + response));
                }
            }
        });
    }



    public interface ApiCallback {
        // 定义回调接口，用于处理请求结果
        void onSuccess(String responseData);
        void onFailure(Exception e);
    }
}
