package com.example.tickethub;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class TicketPage extends AppCompatActivity {

    private TextView responseTextView;
    private ImageView qrImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_page);

        qrImageView = findViewById(R.id.qr_image_view);

        // 创建要发送的 JSON 数据
        String jsonData = "{\"data\": \"test-Linux\"}"; // 要发送的 JSON 数据

        // 创建 ApiClient 实例
        ApiClient apiClient = new ApiClient();

        // 发送 POST 请求，并获取响应数据
        new SendDataAsyncTask().execute(apiClient, jsonData);
    }

    private class SendDataAsyncTask extends AsyncTask<Object, Void, String> {

        @Override
        protected String doInBackground(Object... objects) {
            // 从参数中获取 ApiClient 实例和要发送的 JSON 数据
            ApiClient apiClient = (ApiClient) objects[0];
            String jsonData = (String) objects[1];
            // 发送 POST 请求，并获取响应数据
            return apiClient.postData("http://106.14.13.36:5000/api/generate_qr", jsonData);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            // 在 UI 线程中更新 UI 控件
//            if (s != null) {
//                responseTextView.setText(s); // 将获取的数据显示在 TextView 中
//            } else {
//                responseTextView.setText("Failed to send data"); // 显示错误信息
//            }


            if (s != null) {
                // 将 base64 编码的字符串解码为字节数组
                byte[] decodedBytes = android.util.Base64.decode(s, android.util.Base64.DEFAULT);
                // 将字节数组转换为位图
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                // 将位图设置给 ImageView
                qrImageView.setImageBitmap(bitmap);


            }
        }
    }
}