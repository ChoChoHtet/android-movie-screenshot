package com.android.screen_capture

import com.android.screen_capture.di.component.DaggerAppComponent
import dagger.android.support.DaggerApplication

class MovieApp :DaggerApplication() {
    override fun onCreate() {
        super.onCreate()
        applicationInjector().inject(this)
    }
    override fun applicationInjector() = DaggerAppComponent.factory().create(this)
}