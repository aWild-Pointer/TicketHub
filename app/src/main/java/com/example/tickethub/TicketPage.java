package com.example.tickethub;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TicketPage extends AppCompatActivity {

    private ImageView qrImageView;
    private Button btn_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_page);

        qrImageView = findViewById(R.id.qr_image_view);
        btn_return = findViewById(R.id.btn_return);

        String data = "test-Linux";

        sendRequest(data);

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String name = intent.getStringExtra("name");
                String phone = intent.getStringExtra("phone");
                Intent intent2 = new Intent(TicketPage.this, TicketsFragment.class);
                intent2.putExtra("data", data);
                startActivity(intent2);
            }
        });
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
                    runOnUiThread(new Runnable() {
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
