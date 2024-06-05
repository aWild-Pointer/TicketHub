package com.example.tickethub;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProfileFragment extends Fragment {

    private String username,userinfo;

    public ProfileFragment() {
        // Required empty public constructor
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
        TextView textViewusername = view.findViewById(R.id.username);
        textViewusername.setText(username);

        TextView textViewuserinfo = view.findViewById(R.id.userinfo);
        textViewuserinfo.setText(userinfo);

        return view;
    }
}
