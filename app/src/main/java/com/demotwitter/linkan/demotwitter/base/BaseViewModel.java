package com.demotwitter.linkan.demotwitter.base;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.Nullable;
import android.util.Log;

import com.demotwitter.linkan.demotwitter.RxScheduler.SchedulerProvider;
import com.demotwitter.linkan.demotwitter.data.DataManager;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseViewModel<N extends BaseNavigator> extends ViewModel {

    private final DataManager mDataManager;

    private final ObservableBoolean loading = new ObservableBoolean(false);

    private final SchedulerProvider mSchedulerProvider;

    private CompositeDisposable mCompositeDisposable;

    private WeakReference<N> mNavigator;

    protected AtomicLong referenceTime;


    public final ObservableBoolean userProfileVisible = new ObservableBoolean(false);

    public final ObservableField<String> userAvatarUrl;


    public BaseViewModel(DataManager dataManager,
                         SchedulerProvider scheduleProvider) {
        this.mDataManager = dataManager;
        this.mSchedulerProvider = scheduleProvider;
        this.mCompositeDisposable = new CompositeDisposable();

        userAvatarUrl = new ObservableField<>(getDataManager().getUserAvatarUrl());
    }


    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public N getNavigator() {
        return mNavigator.get();
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    protected <T> ObservableTransformer<T, T> showDebugWithSideEffect(final String operatorName) {

        return upstream -> upstream
                .doOnSubscribe(disposable -> showLogs("Observable doOnSubscribe", operatorName))
                .filter(response -> {
                    showLogs("Observable filter", operatorName);
                    return true;
                })
                .doOnNext(response -> showLogs("Observable doOnNext", operatorName))
                .doAfterNext(response -> showLogs("Observable doAfterNext", operatorName))
                .doOnTerminate(() -> showLogs("Observable doOnTerminate", operatorName))
                .doOnComplete(() -> showLogs("Observable doOnTerminate", operatorName))
                .doOnError(e -> showLogs("Observable doOnError", operatorName))
                .doFinally(() -> showLogs("Observable doFinally", operatorName))
                .doAfterTerminate(() -> showLogs("Observable doAfterTerminate", operatorName))
                .doOnDispose(() -> showLogs("Observable doOnDispose", operatorName));
    }


    protected <T> ObservableTransformer<T, T> provideScheduler() {
        return upstream -> upstream
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui());
    }


    public abstract class BaseObserver<T> implements Observer<T>, SingleObserver<T>, android.arch.lifecycle.Observer<T> {

        @Override
        public void onSubscribe(Disposable disposable) {
            referenceTime = new AtomicLong();
            referenceTime.set(System.currentTimeMillis());
          //  setIsLoading(true);
            getCompositeDisposable().add(disposable);

            showLogs("BaseObserver onSubscribe", "");
        }

        @Override
        public void onNext(T object) {
            showLogs("BaseObserver onNext", "");
            result(object);
        }

        @Override
        public void onSuccess(T object) {
            showLogs("BaseObserver onSuccess", "");
            result(object);
        }

        @Override
        public void onChanged(@Nullable T object) {
            showLogs("BaseObserver onChanged", "");
            result(object);
        }


        @Override
        public void onComplete() {
            showLogs("BaseObserver onComplete", "");
            setIsLoading(false);
        }

        @Override
        public void onError(Throwable throwable) {
            showLogs("BaseObserver onError", "");
            setIsLoading(false);

            if (getNavigator() != null)
                getNavigator().handleError(throwable);
        }

        protected abstract void result(T object);

    }

    protected void setIsLoading(boolean isLoading) {
        loading.set(isLoading);
    }

    public ObservableBoolean getLoading() {
        return loading;
    }


    protected void showLogs(String MethodName, String operatorName) {
        //  Log.d("TAG", "method is " + MethodName);
        if (referenceTime != null)
            Log.d("TAG", operatorName + " " + MethodName + " total time taken ==> " + (System.currentTimeMillis() - referenceTime.get()) + " ms");
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

}
