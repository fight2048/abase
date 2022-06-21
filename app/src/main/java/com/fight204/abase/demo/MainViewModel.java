package com.fight204.abase.demo;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.fight2048.abase.mvvm.viewmodel.base.BaseViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends BaseViewModel<MainRepository> {
    public MainViewModel(@NonNull Application application) {
        super(application);
        Log.e(TAG, "MainViewModel: ");
    }

    @Override
    public void onInitialize(Object... request) {
        Observable.create(emitter -> {
                    Thread.sleep(4000);
                    emitter.onComplete();
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<Object>() {
                    @Override
                    public void onSuccess(Object response) {
                        Log.e(TAG, "开始: " + response);
                    }
                });
    }
}
