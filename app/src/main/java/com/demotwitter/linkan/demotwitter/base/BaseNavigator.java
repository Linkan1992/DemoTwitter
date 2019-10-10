package com.demotwitter.linkan.demotwitter.base;


import android.content.Context;

public interface BaseNavigator {

    void handleError(Throwable throwable);

    void showToast(String message);

}
