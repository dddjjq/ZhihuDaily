package com.welson.zhihudaily.retrofit;

import com.welson.zhihudaily.data.NewsBefore;
import com.welson.zhihudaily.data.NewsLatest;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("news/latest")
    Observable<NewsLatest> getNewsData();

    @GET("news/before/{date}")
    Observable<NewsBefore> getNewsBeforeData(@Path("date")String date);
}
