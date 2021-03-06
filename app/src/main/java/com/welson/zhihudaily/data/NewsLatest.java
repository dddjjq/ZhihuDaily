package com.welson.zhihudaily.data;

import java.util.ArrayList;

public class NewsLatest {

    private String date;
    private ArrayList<NewsStory> stories;
    private ArrayList<NewsTopStory> top_stories;

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

    public ArrayList<NewsTopStory> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(ArrayList<NewsTopStory> top_stories) {
        this.top_stories = top_stories;
    }
}
