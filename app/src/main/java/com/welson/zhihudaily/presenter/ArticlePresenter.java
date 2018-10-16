package com.welson.zhihudaily.presenter;

import android.util.Log;

import com.welson.zhihudaily.contract.ArticleContract;
import com.welson.zhihudaily.data.Article;
import com.welson.zhihudaily.data.ArticleZipData;
import com.welson.zhihudaily.data.NewsExtras;
import com.welson.zhihudaily.retrofit.RetrofitHelper;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public class ArticlePresenter extends AbstractPresenter implements ArticleContract.Presenter{

    private static final String TAG = ArticlePresenter.class.getSimpleName();
    private ArticleContract.View view;
    private Article article;
    private NewsExtras newsExtras;
    private ArticleZipData articleZipData;

    public ArticlePresenter(ArticleContract.View view){
        this.view = view;
    }

    @Override
    public void requestData() {

    }


    @Override
    public void requestArticleData(long id) {
        Observable.zip(RetrofitHelper.getInstance().getArticleData(id), RetrofitHelper.getInstance().getExtrasData(id),
                new BiFunction<Article, NewsExtras, ArticleZipData>() {
                    @Override
                    public ArticleZipData apply(Article article,NewsExtras extras){
                        ArticleZipData articleZipData = new ArticleZipData();
                        articleZipData.setArticle(article);
                        articleZipData.setExtras(extras);
                        return articleZipData;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleZipData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(ArticleZipData mArticleZipData) {
                        articleZipData = mArticleZipData;
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG,"onError");
                        Log.d(TAG,e.getMessage()+"");
                    }

                    @Override
                    public void onComplete() {
                        view.showZipDataSuccess(articleZipData);
                    }
                });
        /*RetrofitHelper.getInstance().getArticleData(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Article>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(Article mArticle) {
                        article = mArticle;
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG,"onError 1");
                    }

                    @Override
                    public void onComplete() {
                        view.showArticleSuccess(article);
                    }
                });*/
    }

    @Override
    public void detachView() {
        if (view != null){
            view = null;
        }
    }

    @Override
    public void requestExtraData(long id) {
        RetrofitHelper.getInstance().getExtrasData(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsExtras>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(NewsExtras mNewsExtras) {
                        newsExtras = mNewsExtras;
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG,"onError 2");
                    }

                    @Override
                    public void onComplete() {
                        view.showExtrasSuccess(newsExtras);
                    }
                });
    }
}
