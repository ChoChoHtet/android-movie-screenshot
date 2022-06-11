package com.android.screen_capture.di.module

import com.android.screen_capture.ui.detail.HomeDetailFragment
import com.android.screen_capture.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun homeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun homeDetailFragment():HomeDetailFragment
}