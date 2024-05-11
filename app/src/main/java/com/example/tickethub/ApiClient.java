package com.example.tickethub;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//import okhttp3.logging.HttpLoggingInterceptor;
public class ApiClient {

    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    // 发起网络请求并获取数据的方法
    public String fetchData(String url) {
        // 构建一个请求对象
        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            // 同步执行请求并获取响应对象
            Response response = client.newCall(request).execute();
            // 返回响应体中的字符串形式数据
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // 如果发生异常，则返回 null
        }
    }

    // 发送 POST 请求并获取数据的方法
    public String postData(String url, String json) {
        // 构建请求体
        RequestBody body = RequestBody.create(json, JSON);
        // 构建一个 POST 请求对象
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try {
            // 打印请求信息
            System.out.println("Sending POST request to: " + url);
            System.out.println("Request body: " + json);

            // 同步执行请求并获取响应对象
            Response response = client.newCall(request).execute();
            // 返回响应体中的字符串形式数据
            String responseData = response.body().string();

            // 打印响应信息
            System.out.println("Response from server: " + responseData);

            return responseData;
        } catch (Exception e) {
            e.printStackTrace();
            return null; // 如果发生异常，则返回 null
        }
    }
}

