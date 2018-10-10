package com.welson.zhihudaily.contract;

import com.welson.zhihudaily.presenter.BasePresenter;
import com.welson.zhihudaily.view.BaseView;

public class HomeContract {

    public interface Presenter extends BasePresenter{
        void getBannerData();
        void getMainData();
    }

    public interface View extends BaseView<Presenter>{
        void showBannerSuccess();
        void showMainDataSuccess();
    }
}
