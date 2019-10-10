package com.demotwitter.linkan.demotwitter.base;

import android.databinding.ObservableBoolean;

public class PagingProgressViewModel {

    private final ObservableBoolean isVisible = new ObservableBoolean(false);

    public PagingProgressViewModel(){

    }

    public void setIsVisible(boolean value){
        this.isVisible.set(value);
    }

    public ObservableBoolean getIsVisible() {
        return isVisible;
    }

}
