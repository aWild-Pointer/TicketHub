package com.example.tickethub;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

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

        Map<String,String> Data = new LinkedHashMap<>();
        Data.put("data",data);

        ApiClient apiClient =new ApiClient();
        apiClient.postData(Data, "http://106.14.13.36:5000/api/generate_qr", new ApiClient.ApiCallback() {
            @Override
            public void onSuccess(String responseData) {
                //在 UI 线程中处理成功的响应
                runOnUiThread(() -> {
                    if (responseData != null) {
                        try {
                            //解码 Base64 数据并转换为 Bitmap
                            byte[] decodedBytes = Base64.decode(responseData, Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                            //显示 Bitmap
                            qrImageView.setImageBitmap(bitmap);
                        } catch (Exception e) {
                        }
                    }
                });
            }
            @Override
            public void onFailure(Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(TicketPage.this, "Failed to load QR code", Toast.LENGTH_SHORT).show();
                });
            }
        });

//        byte[] decodedBytes = android.util.Base64.decode(responseData, android.util.Base64.DEFAULT);
//        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
//        qrImageView.setImageBitmap(bitmap);
//
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
