package com.example.tickethub;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.tickethub.Utils.Event;
import com.example.tickethub.Utils.EventAdapter;

import java.util.ArrayList;
import java.util.List;

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

        // Initialize RecyclerView and other views here
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        eventList = new ArrayList<>();
        // Populate your list with event data
        eventList.add(new Event("南京站", "2024张惠妹 ASMR MAX巡回演唱会 - 南京站\n时间: 2024.05.11 周六 19:00\n场馆: 南京市 | 南京奥体中心体育场", R.drawable.event_nanjing));
        eventList.add(new Event("北京站", "坂本龙一经典名曲LIVE音乐现场\n时间: 2024.03.27 周三 19:30-21:00\n场馆: 北京市 | 爱乐汇艺术空间", R.drawable.event_beijing));
        eventList.add(new Event("上海站", "2024张杰未来·LIVE “开往1982”巡回演唱会 - 上海站\n时间: 2024.03.28 03:30\n场馆: 上海市 | 虹口足球场", R.drawable.event_shanghai));
        eventList.add(new Event("北京站", "2024檀谷开山节\n时间: 2024.03.27\n场馆: 北京市", R.drawable.event_beijing2));

        eventAdapter = new EventAdapter(getContext(), eventList);
        recyclerView.setAdapter(eventAdapter);

        return view;
    }
}

