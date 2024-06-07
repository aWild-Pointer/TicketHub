package com.example.tickethub;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tickethub.Utils.Event;
import com.example.tickethub.Utils.EventAdapter;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private ArrayList<Event> eventList;
    private Button btn_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Initialize RecyclerView and set up the adapter
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btn_return = findViewById(R.id.btn_return);

        eventList = new ArrayList<>();
        eventAdapter = new EventAdapter(this, eventList);
        recyclerView.setAdapter(eventAdapter);

        // You can add mock data to the eventList here or fetch from a database
        loadEvents();

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadEvents() {
        // Add mock data to eventList
//        eventList.add(new Event("", "Description 1"));
        eventList.add(new Event("南京站", "2024张惠妹 ASMR MAX巡回演唱会 - 南京站\n时间: 2024.05.11 周六 19:00\n场馆: 南京市 | 南京奥体中心体育场", R.drawable.event_nanjing));

        eventAdapter.notifyDataSetChanged();
    }


}
