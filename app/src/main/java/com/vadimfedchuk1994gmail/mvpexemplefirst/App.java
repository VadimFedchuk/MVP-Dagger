package com.vadimfedchuk1994gmail.mvpexemplefirst;

import android.app.Application;

public class App extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .storageModule(new StorageModule())
                .build();
    }
//    component = DaggerAppComponent.builder()
//            .methodForSettingAppModule(new StorageModule(this)).letsBuildThisComponent();

    public static AppComponent getComponent() {
        return component;
    }
}
