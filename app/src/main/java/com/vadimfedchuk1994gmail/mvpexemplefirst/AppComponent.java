package com.vadimfedchuk1994gmail.mvpexemplefirst;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = StorageModule.class)
public interface AppComponent {
    //DbHelper getDbHelper();
    void injectsMainActivity(MainActivity mainActivity);

//    @Component.Builder
//    interface MyBuilder {
//        AppComponent letsBuildThisComponent();
//        MyBuilder methodForSettingAppModule(StorageModule storageModule);
//    }

}
