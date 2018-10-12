package com.welson.zhihudaily.contract;

import com.welson.zhihudaily.data.ThemeContent;
import com.welson.zhihudaily.presenter.BasePresenter;
import com.welson.zhihudaily.view.BaseView;

public class CommonContract {

    public interface Presenter extends BasePresenter{
        void detachView();
        void requestContentData(int id);
    }

    public interface View extends BaseView<Presenter>{
        void showContentSuccess(ThemeContent themeContent);
    }
}
