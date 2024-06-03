package com.example.tickethub;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private EditText phoneEditText, codeEditText;
    private Button sendCodeButton, verifyButton;
    private int code;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phoneEditText = findViewById(R.id.phoneEditText);
        codeEditText = findViewById(R.id.CodeEditText);
        sendCodeButton = findViewById(R.id.sendCodeButton);
        verifyButton = findViewById(R.id.verifyButton);

        sendCodeButton.setOnClickListener(v -> getCode());
        verifyButton.setOnClickListener(v -> login());
    }

    public void getCode() {
        String phone = phoneEditText.getText().toString().trim();
        ApiClient apiClient = new ApiClient();
        Map<String, String> Data = new LinkedHashMap<>();
        Data.put("phone", phone);
        apiClient.postData(Data, "http://106.14.13.36:5000/api/logcode", new ApiClient.ApiCallback() {
            @Override
            public void onSuccess(String responseData) {
                runOnUiThread(() -> {
                    if (responseData != null) {
                        AlertDialog alertDialog1 = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("验证码确认")
                                .setMessage(responseData)
                                .setIcon(R.mipmap.ic_launcher)
                                .setPositiveButton("朕知道了", (dialog, which) -> dialog.dismiss())
                                .create();
                        alertDialog1.show();
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("错误")
                            .setMessage("获取验证码失败，请重试！")
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton("朕知道了", (dialog, which) -> dialog.dismiss())
                            .create();
                    alertDialog.show();
                });
            }
        });
    }

    public void login() {
        String phone = phoneEditText.getText().toString().trim();
        String code = codeEditText.getText().toString().trim();
        Map<String, String> Data = new LinkedHashMap<>();
        Data.put("phone", phone);
        Data.put("code", code);
        ApiClient apiClient = new ApiClient();
        apiClient.postData(Data, "http://106.14.13.36:5000/api/login", new ApiClient.ApiCallback() {
            @Override
            public void onSuccess(String responseData) {
                runOnUiThread(() -> {
                    if (Objects.equals(responseData, "SUCCESS")) {
                        Intent intent = new Intent(MainActivity.this, NavigationBar.class);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("错误")
                            .setMessage("登录失败，请重试！")
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton("朕知道了", (dialog, which) -> dialog.dismiss())
                            .create();
                    alertDialog.show();
                });
            }
        });
    }
}

