package com.android.screen_capture.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.screen_capture.di.ViewModelFactory
import com.android.screen_capture.di.ViewModelKey
import com.android.screen_capture.ui.detail.HomeDetailViewModel
import com.android.screen_capture.ui.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun homeViewModel(homeViewModel: HomeViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeDetailViewModel::class)
    abstract fun homeDetailViewModel(homeDetailViewModel: HomeDetailViewModel):ViewModel


}