package com.welson.zhihudaily.data;

import java.util.ArrayList;

public class ThemeContent {

    private ArrayList<NewsStory> stories;
    private String description;
    private String background;
    private long color;
    private String name;
    private String image;
    private ArrayList<Editor> editors;
    private String image_source;

    public ArrayList<NewsStory> getStories() {
        return stories;
    }

    public void setStories(ArrayList<NewsStory> stories) {
        this.stories = stories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public long getColor() {
        return color;
    }

    public void setColor(long color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<Editor> getEditors() {
        return editors;
    }

    public void setEditors(ArrayList<Editor> editors) {
        this.editors = editors;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }
}


