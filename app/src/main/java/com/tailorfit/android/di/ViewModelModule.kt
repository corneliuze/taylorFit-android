
package com.tailorfit.android.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    // TODO bind viewmodels
}
