package com.demotwitter.linkan.demotwitter.base;

import android.databinding.ObservableBoolean;

public class EmptyFeedViewModel {

    private final ObservableBoolean isVisible = new ObservableBoolean(false);

    private RetryNavigator onRetryListener;

    public EmptyFeedViewModel(RetryNavigator onRetryListener){
        this.onRetryListener = onRetryListener;
    }

    public void onRetry(){
        /**
         * On click of button show grey background and hide the
         * layout
         */
        setIsVisible(false);
        onRetryListener.onRetryClick();
    }

    public void setIsVisible(boolean value){
        this.isVisible.set(value);
    }

    public ObservableBoolean getIsVisible() {
        return isVisible;
    }

}
