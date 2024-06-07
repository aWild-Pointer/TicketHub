package com.example.tickethub.UI;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tickethub.R;
import com.example.tickethub.Utils.ApiClient;
import com.example.tickethub.Utils.Event;
import com.example.tickethub.Utils.EventAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private List<Event> eventList;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 初始化 RecyclerView 和其他视图
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        eventList = new ArrayList<>();
        eventAdapter = new EventAdapter(getContext(), eventList);
        recyclerView.setAdapter(eventAdapter);

        // 模拟网络请求获取数据
        for (int i = 1; i <= 4; i++) {
            Map<String, String> data = new LinkedHashMap<>();
            data.put("choice", String.valueOf(i));
            setEvent(data);
        }

        return view;
    }

    private void setEvent(Map<String, String> data) {
        ApiClient apiClient = new ApiClient();
        apiClient.postData(data, "http://106.14.13.36:5000/api/eventinfo", new ApiClient.ApiCallback() {
            @Override
            public void onSuccess(String responseData) {
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    String eventName = jsonObject.optString("eventname");
                    String eventinfo = jsonObject.optString("eventinfo");
                    String eventresource = jsonObject.optString("eventresource");

                    // 根据资源名称获取资源ID
                    int resId = getResources().getIdentifier(eventresource, "drawable", getActivity().getPackageName());
                    if (resId == 0) {
                        // 资源未找到，使用默认资源
                        resId = R.drawable.error; // 你需要定义一个默认图片
                    }

                    Event event = new Event(eventName, eventinfo, resId);

                    // 在主线程中更新UI
                    getActivity().runOnUiThread(() -> {
                        eventList.add(event);
                        eventAdapter.notifyDataSetChanged(); // 使用 notifyDataSetChanged 更新整个列表
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });
    }
}
