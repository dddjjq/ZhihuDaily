package com.welson.zhihudaily.contract;

import com.welson.zhihudaily.data.Article;
import com.welson.zhihudaily.data.NewsExtras;
import com.welson.zhihudaily.presenter.BasePresenter;
import com.welson.zhihudaily.view.BaseView;

public class ArticleContract {

    public interface Presenter extends BasePresenter{
        void requestArticleData(long id);
        void detachView();
        void requestExtraData(long id);
    }

    public interface View extends BaseView<Presenter>{
        void showArticleSuccess(Article article);
        void showExtrasSuccess(NewsExtras extras);
    }
}
