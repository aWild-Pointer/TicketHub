package com.example.tickethub.UI;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tickethub.HistoryActivity;
import com.example.tickethub.MainActivity;
import com.example.tickethub.R;

public class ProfileFragment extends Fragment {

    private String username, userinfo;
    private Button order, logoff;

    public ProfileFragment() {
        // 必须的空公共构造函数
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // 获取传递过来的 username
        if (getArguments() != null) {
            username = getArguments().getString("username");
            userinfo = getArguments().getString("userinfo");
        }

        // 使用传递过来的 username，例如显示在 TextView 中
        TextView textViewUsername = view.findViewById(R.id.username);
        textViewUsername.setText(username);

        TextView textViewUserinfo = view.findViewById(R.id.userinfo);
        textViewUserinfo.setText(userinfo);

        order = view.findViewById(R.id.order);
        logoff = view.findViewById(R.id.logoff);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HistoryActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("userinfo", userinfo);
                startActivity(intent);
            }
        });

        logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
