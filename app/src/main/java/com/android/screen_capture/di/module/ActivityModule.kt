package com.android.screen_capture.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.android.screen_capture.MainActivity

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

}