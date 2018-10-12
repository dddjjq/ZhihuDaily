package com.welson.zhihudaily.presenter;

import android.util.Log;

import com.welson.zhihudaily.contract.CommonContract;
import com.welson.zhihudaily.data.ThemeContent;
import com.welson.zhihudaily.retrofit.RetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CommonPresenter extends AbstractPresenter implements CommonContract.Presenter{

    private CommonContract.View view;
    private ThemeContent themeContent;

    public CommonPresenter(CommonContract.View view){
        this.view = view;
    }

    @Override
    public void detachView() {
        if (view != null){
            view = null;
        }
    }

    @Override
    public void requestContentData(int id) {
        Log.d("dingyl","id : " + id );
        RetrofitHelper.getInstance().getThemeContent(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ThemeContent>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(ThemeContent mThemeContent) {
                        themeContent = mThemeContent;
                        Log.d("dingyl","themeContent size " +  themeContent.getStories().size());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        view.showContentSuccess(themeContent);
                    }
                });
    }

    @Override
    public void requestData() {

    }
}
