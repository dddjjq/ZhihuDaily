package com.welson.zhihudaily.contract;

import com.welson.zhihudaily.data.CommentData;
import com.welson.zhihudaily.data.CommentZipData;
import com.welson.zhihudaily.presenter.BasePresenter;
import com.welson.zhihudaily.view.BaseView;

public class CommentContract {

    public interface Presenter extends BasePresenter{
        void requestLongCommentData(long id);
        void requestShortCommentData(long id);
    }

    public interface View extends BaseView<Presenter>{
        void showLongCommentDataSuccess(CommentData commentData);
        void showShortCommentDataSuccess(CommentData commentData);
        void showZipCommentDataSuccess(CommentZipData zipData);
    }
}
