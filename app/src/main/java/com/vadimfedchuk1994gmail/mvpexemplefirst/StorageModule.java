package com.vadimfedchuk1994gmail.mvpexemplefirst;

import android.content.Context;

import com.vadimfedchuk1994gmail.mvpexemplefirst.database.DbHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = ContextModule.class)
public class StorageModule {

    @Singleton
    @Provides
    DbHelper provideDbHelper(Context context) {
        return new DbHelper(context);
    }
}
