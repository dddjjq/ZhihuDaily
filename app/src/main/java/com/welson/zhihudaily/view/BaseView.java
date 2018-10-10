package com.welson.zhihudaily.view;

import com.welson.zhihudaily.presenter.BasePresenter;

public interface BaseView<P extends BasePresenter> {
    void setPresenter(P presenter);
}
