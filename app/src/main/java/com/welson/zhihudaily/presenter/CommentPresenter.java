package com.welson.zhihudaily.presenter;

import android.util.Log;

import com.welson.zhihudaily.contract.CommentContract;
import com.welson.zhihudaily.data.CommentData;
import com.welson.zhihudaily.data.CommentDataBean;
import com.welson.zhihudaily.data.CommentZipData;
import com.welson.zhihudaily.data.NewsExtras;
import com.welson.zhihudaily.retrofit.RetrofitHelper;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public class CommentPresenter extends AbstractPresenter implements CommentContract.Presenter{

    private static final String TAG = CommentPresenter.class.getSimpleName();
    private CommentData longComments;
    private CommentData shortComments;
    private CommentContract.View view;
    private CommentZipData zipData;

    public CommentPresenter(CommentContract.View view){
        this.view = view;
    }

    @Override
    public void requestLongCommentData(long id) {
        /*Observable.zip(RetrofitHelper.getInstance().getLongCommentData(id), RetrofitHelper.getInstance().getExtrasData(id),
                new BiFunction<CommentData, NewsExtras, CommentZipData>() {
                    @Override
                    public CommentZipData apply(CommentData commentData,NewsExtras newsExtras){
                        CommentZipData zipData = new CommentZipData();
                        zipData.setCommentData(commentData);
                        zipData.setNewsExtras(newsExtras);
                        return zipData;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentZipData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(CommentZipData commentZipData) {
                        zipData = commentZipData;
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        view.showZipCommentDataSuccess(zipData);
                    }
                });*/


        RetrofitHelper.getInstance().getLongCommentData(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(CommentData commentData) {
                        longComments = commentData;
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG,"onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG,"longComments size : " + longComments.getComments().size());
                        view.showLongCommentDataSuccess(longComments);
                    }
                });
    }

    @Override
    public void requestShortCommentData(long id) {
        RetrofitHelper.getInstance().getShortCommentData(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(CommentData commentData) {
                        shortComments = commentData;
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        view.showShortCommentDataSuccess(shortComments);
                    }
                });
    }

    @Override
    public void requestData() {

    }
}
