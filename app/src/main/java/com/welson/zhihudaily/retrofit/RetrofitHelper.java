package com.welson.zhihudaily.retrofit;


import com.google.gson.GsonBuilder;
import com.welson.zhihudaily.data.Article;
import com.welson.zhihudaily.data.NewsBefore;
import com.welson.zhihudaily.data.NewsExtras;
import com.welson.zhihudaily.data.NewsLatest;
import com.welson.zhihudaily.data.ThemeContent;
import com.welson.zhihudaily.data.Themes;
import com.welson.zhihudaily.utils.Constants;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private OkHttpClient client = new OkHttpClient();
    GsonConverterFactory factory = GsonConverterFactory.create(new GsonBuilder().create());
    private Retrofit retrofit = null;
    private ApiService apiService;
    private static RetrofitHelper instance;

    public static RetrofitHelper getInstance() {
        if (instance == null){
            instance = new RetrofitHelper();
        }
        return instance;
    }

    private RetrofitHelper(){
        init();
    }

    private void init(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }


    public Observable<NewsLatest> getNewsData(){
        return apiService.getNewsData();
    }

    public Observable<NewsBefore> getNewsBeforeData(String date){
        return apiService.getNewsBeforeData(date);
    }

    public Observable<Themes> getThemeData(){
        return apiService.getThemeData();
    }

    public Observable<ThemeContent> getThemeContent(int id){
        return apiService.getThemeContent(id);
    }

    public Observable<Article> getArticleData(long id){
        return apiService.getArticleData(id);
    }

    public Observable<NewsExtras> getExtrasData(long id){
        return apiService.getExtrasData(id);
    }
}
