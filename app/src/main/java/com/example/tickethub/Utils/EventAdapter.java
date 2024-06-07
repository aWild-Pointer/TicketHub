package com.example.tickethub.Utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tickethub.EventInfo;
import com.example.tickethub.R;


import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Event> eventList;
    private Context context;

    public EventAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.eventTitleTextView.setText(event.getTitle());
        holder.eventDetailsTextView.setText(event.getDetails());
        holder.eventImageView.setImageResource(event.getImageResourceId());
        holder.eventButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, EventInfo.class);
            intent.putExtra("eventTitle", event.getTitle());
            intent.putExtra("eventDetails", event.getDetails());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView eventTitleTextView;
        TextView eventDetailsTextView;
        ImageView eventImageView;
        Button eventButton;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTitleTextView = itemView.findViewById(R.id.eventTitleTextView);
            eventDetailsTextView = itemView.findViewById(R.id.eventDetailsTextView);
            eventImageView = itemView.findViewById(R.id.eventImageView);
            eventButton = itemView.findViewById(R.id.eventButton);
        }
    }
}
