package com.welson.zhihudaily.retrofit;

import com.welson.zhihudaily.data.Article;
import com.welson.zhihudaily.data.CommentData;
import com.welson.zhihudaily.data.CommentDataBean;
import com.welson.zhihudaily.data.NewsBefore;
import com.welson.zhihudaily.data.NewsExtras;
import com.welson.zhihudaily.data.NewsLatest;
import com.welson.zhihudaily.data.ThemeContent;
import com.welson.zhihudaily.data.Themes;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("news/latest")
    Observable<NewsLatest> getNewsData();

    @GET("news/before/{date}")
    Observable<NewsBefore> getNewsBeforeData(@Path("date")String date);

    @GET("themes")
    Observable<Themes> getThemeData();

    @GET("theme/{id}")
    Observable<ThemeContent> getThemeContent(@Path("id")int id);

    @GET("news/{id}")
    Observable<Article> getArticleData(@Path("id")long id);

    @GET("story-extra/{id}")
    Observable<NewsExtras> getExtrasData(@Path("id")long id);

    @GET("story/{id}/long-comments")
    Observable<CommentData> getLongCommentData(@Path("id")long id);

    @GET("story/{id}/short-comments")
    Observable<CommentData> getShortCommentData(@Path("id")long id);
}
