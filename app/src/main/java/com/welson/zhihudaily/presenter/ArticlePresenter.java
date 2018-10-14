package com.welson.zhihudaily.presenter;

import com.welson.zhihudaily.contract.ArticleContract;
import com.welson.zhihudaily.data.Article;
import com.welson.zhihudaily.data.NewsExtras;
import com.welson.zhihudaily.retrofit.RetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ArticlePresenter extends AbstractPresenter implements ArticleContract.Presenter{

    private ArticleContract.View view;
    private Article article;
    private NewsExtras newsExtras;

    public ArticlePresenter(ArticleContract.View view){
        this.view = view;
    }

    @Override
    public void requestData() {

    }


    @Override
    public void requestArticleData(long id) {
        RetrofitHelper.getInstance().getArticleData(id)
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
                    }

                    @Override
                    public void onComplete() {
                        view.showArticleSuccess(article);
                    }
                });
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
                    }

                    @Override
                    public void onComplete() {
                        view.showExtrasSuccess(newsExtras);
                    }
                });
    }
}
