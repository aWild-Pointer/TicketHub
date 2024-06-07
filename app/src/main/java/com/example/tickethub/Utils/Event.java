package com.example.tickethub.Utils;

public class Event {
    private String title;
    private String details;
    private int imageResourceId;

    public Event(String title, String details, int imageResourceId) {
        this.title = title;
        this.details = details;
        this.imageResourceId = imageResourceId;
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

}
