package com.android.screen_capture

import android.app.Application
import com.android.screen_capture.di.component.AppComponent
import com.android.screen_capture.di.component.DaggerAppComponent
import dagger.android.support.DaggerApplication
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
open class MovieApp : Application() {
    override fun onCreate() {
        super.onCreate()
// Instance of the AppComponent that will be used by all the Activities in the project
        val appComponent: AppComponent by lazy {
            initializeComponent()
        }

    }

    open fun initializeComponent(): AppComponent {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        return DaggerAppComponent.factory().create(applicationContext)
    }
}