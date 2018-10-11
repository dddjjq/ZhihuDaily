package com.welson.zhihudaily.contract;

import com.welson.zhihudaily.data.NewsBefore;
import com.welson.zhihudaily.data.NewsLatest;
import com.welson.zhihudaily.presenter.BasePresenter;
import com.welson.zhihudaily.view.BaseView;

public class HomeContract {

    public interface Presenter extends BasePresenter{
        void attachView(View v);
        void detachView();
    }

    public interface View extends BaseView<Presenter>{
        void showBannerSuccess(NewsLatest newsLatest);
        void showMainDataSuccess(NewsLatest newsLatest);
        void showMoreDataSuccess(NewsBefore newsBefore);
    }
}
