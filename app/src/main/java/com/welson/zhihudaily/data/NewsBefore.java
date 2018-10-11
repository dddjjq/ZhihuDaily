package com.welson.zhihudaily.data;

import java.util.ArrayList;

public class NewsBefore {

    private String date;
    private ArrayList<NewsStory> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<NewsStory> getStories() {
        return stories;
    }

    public void setStories(ArrayList<NewsStory> stories) {
        this.stories = stories;
    }
}
