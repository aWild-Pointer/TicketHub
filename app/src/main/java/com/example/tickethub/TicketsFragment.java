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

        // 为 qrImageView 设置点击事件监听器
        qrImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动 TicketPage 活动
                Intent intent = new Intent(getActivity(), TicketPage.class);
                startActivity(intent);
            }
        });

        // 示例 JSON 数据
        String jsonData = "{\"data\": \"test-Linux\"}";

        // 启动异步任务
        new GenerateQrCodeTask().execute(new ApiClient(), jsonData);

        return view;
    }

    private class GenerateQrCodeTask extends AsyncTask<Object, Void, String> {
        @Override
        protected String doInBackground(Object... objects) {
            // 从参数中获取 ApiClient 实例和要发送的 JSON 数据
            ApiClient apiClient = (ApiClient) objects[0];
            String jsonData = (String) objects[1];
            // 发送 POST 请求，并获取响应数据
            return apiClient.postData("http://106.14.13.36:5000/api/generate_qr", jsonData);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                // 将 base64 编码的字符串解码为字节数组
                byte[] decodedBytes = android.util.Base64.decode(result, android.util.Base64.DEFAULT);
                // 将字节数组转换为位图
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                // 将位图设置给 ImageView
                qrImageView.setImageBitmap(bitmap);
            }
        }
    }
}
