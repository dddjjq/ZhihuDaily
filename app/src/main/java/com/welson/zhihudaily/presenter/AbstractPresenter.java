package com.welson.zhihudaily.presenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class AbstractPresenter {

    private CompositeDisposable compositeDisposable;

    public void addDisposable(Disposable disposable){
        if (compositeDisposable == null){
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public void removeAllDisposable(){
        if (!compositeDisposable.isDisposed() && compositeDisposable != null){
            compositeDisposable.dispose();
        }
    }
}
