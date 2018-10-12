package com.welson.zhihudaily.presenter;

import com.welson.zhihudaily.contract.MainContract;
import com.welson.zhihudaily.data.Themes;
import com.welson.zhihudaily.retrofit.RetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter extends AbstractPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private Themes themes;

    public MainPresenter(MainContract.View view){
        this.view = view;
    }

    @Override
    public void requestData() {
        RetrofitHelper.getInstance().getThemeData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Themes>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(Themes mThemes) {
                        themes = mThemes;
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        view.showThemeSuccess(themes);
                    }
                });
    }
}
