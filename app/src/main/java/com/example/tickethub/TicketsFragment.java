package com.example.tickethub;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.widget.ImageView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TicketsFragment extends Fragment {
    private ImageView qrImageView;

    public TicketsFragment() {
        // 必须的空公共构造函数
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_tickets, container, false);

        // 初始化 qrImageView
        qrImageView = view.findViewById(R.id.qrImageView);

        String data = "test-Linux";

        sendRequest(data);

        // 为 qrImageView 设置点击事件监听器
        qrImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动 TicketPage 活动
                Intent intent = new Intent(getActivity(), TicketPage.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void sendRequest(String data) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("data", data)
                .build();
        Request request = new Request.Builder()
                .url("http://106.14.13.36:5000/api/generate_qr")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                String responseData = response.body().string();
                try {
                    byte[] decodedBytes = android.util.Base64.decode(responseData, android.util.Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

                    // 更新 UI 必须在主线程中进行
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            qrImageView.setImageBitmap(bitmap);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
