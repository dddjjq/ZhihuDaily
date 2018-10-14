package com.welson.zhihudaily.data;

import java.util.ArrayList;

public class Article {
    private String body;
    private String image_source;
    private String title;
    private String image;
    private ArrayList<Recommender> recommenders;
    private String share_url;
    private Object[] js;
    private ArrayList<Theme> theme;
    private String ga_prefix;
    private ArrayList<String> images;
    private int type;
    private long id;
    private ArrayList<String> css;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<Recommender> getRecommenders() {
        return recommenders;
    }

    public void setRecommenders(ArrayList<Recommender> recommenders) {
        this.recommenders = recommenders;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public Object[] getJs() {
        return js;
    }

    public void setJs(Object[] js) {
        this.js = js;
    }

    public ArrayList<Theme> getTheme() {
        return theme;
    }

    public void setTheme(ArrayList<Theme> theme) {
        this.theme = theme;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<String> getCss() {
        return css;
    }

    public void setCss(ArrayList<String> css) {
        this.css = css;
    }

    class Recommender{
        private String avatar;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

    class Theme{
        private String thumbnail;
        private int id;
        private String name;

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
