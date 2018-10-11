package com.welson.zhihudaily.presenter;

import android.util.Log;

import com.welson.zhihudaily.contract.HomeContract;
import com.welson.zhihudaily.data.NewsBefore;
import com.welson.zhihudaily.data.NewsLatest;
import com.welson.zhihudaily.utils.RetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter extends AbstractPresenter implements HomeContract.Presenter {

    private static final String TAG = HomePresenter.class.getSimpleName();
    private HomeContract.View view;
    private NewsLatest newsLatest;
    private NewsBefore newsBefore;

    public HomePresenter(HomeContract.View view){
        this.view = view;
    }

    @Override
    public void attachView(HomeContract.View v) {

    }

    @Override
    public void detachView() {
        if (view != null){
            view = null;
        }
    }

    @Override
    public void requestData() {
        RetrofitHelper.getInstance().getNewsData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsLatest>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(NewsLatest mNewsLatest) {
                        newsLatest = mNewsLatest;
                        Log.d(TAG,"onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG,"onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG,"onComplete");
                        view.showBannerSuccess(newsLatest);
                        view.showMainDataSuccess(newsLatest);
                    }
                });
    }

    public void requestDataWithDate(String dateStr){
        RetrofitHelper.getInstance().getNewsBeforeData(dateStr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsBefore>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(NewsBefore mNewsBefore) {
                        newsBefore = mNewsBefore;
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        view.showMoreDataSuccess(newsBefore);
                    }
                });
    }
}
