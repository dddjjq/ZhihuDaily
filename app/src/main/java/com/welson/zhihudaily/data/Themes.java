package com.welson.zhihudaily.data;

import java.util.ArrayList;

public class Themes {

    private int limit;
    private String[] subscribed;
    private ArrayList<ThemeData> others;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String[] getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(String[] subscribed) {
        this.subscribed = subscribed;
    }

    public ArrayList<ThemeData> getOthers() {
        return others;
    }

    public void setOthers(ArrayList<ThemeData> others) {
        this.others = others;
    }
}
