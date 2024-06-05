package com.example.tickethub;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tickethub.Utils.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private EditText phoneEditText, codeEditText;
    private Button sendCodeButton, verifyButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phoneEditText = findViewById(R.id.phoneEditText);
        codeEditText = findViewById(R.id.CodeEditText);
        sendCodeButton = findViewById(R.id.sendCodeButton);
        verifyButton = findViewById(R.id.verifyButton);
        textView = findViewById(R.id.codeshow);

//        Intent intent = getIntent();
//        String name = intent.getStringExtra("name");

        sendCodeButton.setOnClickListener(v -> getCode());
        verifyButton.setOnClickListener(v -> login());
    }

    public void getCode() {
        String phone = phoneEditText.getText().toString().trim();
        if (!isValidPhoneNumber(phone)) {
            // 提示用户输入有效的手机号
            textView.setText("请输入有效的手机号");
            return;
        }

        ApiClient apiClient = new ApiClient();
        Map<String, String> data = new LinkedHashMap<>();
        data.put("phone", phone);
        apiClient.postData(data, "http://106.14.13.36:5000/api/logcode", new ApiClient.ApiCallback() {
            @Override
            public void onSuccess(String responseData) {
                runOnUiThread(() -> {
                    textView.setText(responseData);
                    // 启动倒计时
                    startCountDownTimer();
                });
            }

            @Override
            public void onFailure(Exception e) {
                runOnUiThread(() -> {
                    textView.setText("获取验证码失败，请重试！");
                });
            }
        });
    }

    private boolean isValidPhoneNumber(String phone) {
        // 检查手机号是否为11位数字并以1开头
        return phone != null && phone.length() == 11 && phone.matches("1\\d{10}");
    }

    private void startCountDownTimer() {
        sendCodeButton.setEnabled(false); // 禁用按钮
        new CountDownTimer(10000, 1000) { // 10秒倒计时，每秒更新一次

            public void onTick(long millisUntilFinished) {
                sendCodeButton.setText("重新发送 (" + millisUntilFinished / 1000 + "s)");
            }

            public void onFinish() {
                sendCodeButton.setEnabled(true); // 启用按钮
                sendCodeButton.setText("获取验证码");
            }
        }.start();
    }

    public void login() {
        String phone = phoneEditText.getText().toString().trim();
        String code = codeEditText.getText().toString().trim();
        if (phone.isEmpty() || code.isEmpty()) {
            // 提示用户输入手机号和验证码
            textView.setText("请输入手机号和验证码");
            return;
        }

        Map<String, String> data = new LinkedHashMap<>();
        data.put("phone", phone);
        data.put("code", code);
        ApiClient apiClient = new ApiClient();
        apiClient.postData(data, "http://106.14.13.36:5000/api/login", new ApiClient.ApiCallback() {
            @Override
            public void onSuccess(String responseData) {
                runOnUiThread(() -> {
//                    if (!responseData.isEmpty()) {
                        String username ,userinfo;
                        Intent intent = new Intent(MainActivity.this, NavigationBar.class);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(responseData);
                            username = jsonObject.optString("username");
                            userinfo = jsonObject.optString("userinfo");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        intent.putExtra("username",username);
                        intent.putExtra("userinfo",userinfo);
                        startActivity(intent);
//                    } else {
//                        textView.setText("登录失败，请重试！");
//                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                runOnUiThread(() -> {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("错误")
                            .setMessage("登录失败，请重试！")
                            .setIcon(R.drawable.logo)
                            .setPositiveButton("朕知道了", (dialog, which) -> dialog.dismiss())
                            .create();
                    alertDialog.show();
                });
            }
        });
    }
}
