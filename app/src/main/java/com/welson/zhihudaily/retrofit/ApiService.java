package com.welson.zhihudaily.retrofit;

import com.welson.zhihudaily.data.NewsLatest;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET("news/latest")
    Observable<NewsLatest> getNewsData();

}
