package com.example.tickethub;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class TicketPage extends AppCompatActivity {

    private TextView responseTextView;
    private ImageView qrImageView;
    private Button btn_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_page);

        qrImageView = findViewById(R.id.qr_image_view);
        btn_return = findViewById(R.id.btn_return);

        // 创建要发送的 JSON 数据
        String jsonData = "{\"data\": \"test-Linux\"}"; // 要发送的 JSON 数据

        // 创建 ApiClient 实例
        ApiClient apiClient = new ApiClient();

        // 发送 POST 请求，并获取响应数据
        new SendDataAsyncTask().execute(apiClient, jsonData);

        btn_return.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String name = intent.getStringExtra("name");
                String phone = intent.getStringExtra("phone");
                Intent intent2 = new Intent(TicketPage.this, TicketsFragment.class);
                intent2.putExtra("data", jsonData);
                startActivity(intent2);
            }
        });
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