
package com.tailorfit.android.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.tailorfit.android.utils.PrefsUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDataModule {

    @Provides
    @Singleton
    fun providePrefsUtils(prefs: SharedPreferences, gson: Gson): PrefsUtils =
        PrefsUtils(prefs, gson)

    @Provides
    @Singleton
    fun provideGlobalSharedPreference(app: Application): SharedPreferences =
        app.getSharedPreferences("global_shared_prefs", Context.MODE_PRIVATE)
}
