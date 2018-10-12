package com.welson.zhihudaily.contract;

import com.welson.zhihudaily.data.Themes;
import com.welson.zhihudaily.presenter.BasePresenter;
import com.welson.zhihudaily.view.BaseView;

public class MainContract {

    public interface Presenter extends BasePresenter{

    }
    public interface View extends BaseView<Presenter>{
        void showThemeSuccess(Themes themes);
    }
}
