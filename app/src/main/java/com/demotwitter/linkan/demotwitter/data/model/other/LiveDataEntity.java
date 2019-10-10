package com.demotwitter.linkan.demotwitter.data.model.other;

public class LiveDataEntity<T> {

    public Method methodName;

    public T viewModel;

    public LiveDataEntity(Method methodName, T viewModel){
        this.methodName = methodName;
        this.viewModel = viewModel;
    }

}
