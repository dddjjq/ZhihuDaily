package com.welson.zhihudaily.utils;

import com.welson.zhihudaily.data.Article;

public class HtmlUtils {
    public static String structHtml(Article article){
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"img-wrap\">")
                .append("<h1 class=\"headline-title\">")
                .append(article.getTitle())
                .append("</h1>")
                .append("<span class=\"img-source\">")
                .append(article.getImage_source())
                .append("</span>") .append("<img src=\"")
                .append(article.getImage())
                .append("\" alt=\"\">")
                .append("<div class=\"img-mask\"></div>"); //news_content_style.css和news_header_style.css都是在assets里的
        String mNewsContent = "<link rel=\"stylesheet\" type=\"text/css\" href=\"article.css\"/>"
                + "<link rel=\"stylesheet\" type=\"text/css\" href=\"header.css\"/>"
                + article.getBody().replace("<div class=\"img-place-holder\">", sb.toString());
        return mNewsContent;
    }
}
