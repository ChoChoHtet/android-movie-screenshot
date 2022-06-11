package com.android.screen_capture.di.component

import android.content.Context
import com.android.screen_capture.MovieApp
import com.android.screen_capture.di.module.ActivityModule
import com.android.screen_capture.di.module.FragmentModule
import com.android.screen_capture.di.module.MovieModule
import com.android.screen_capture.di.module.NetworkModule
import com.android.screen_capture.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelModule::class,
        NetworkModule::class,
        ActivityModule::class,
        FragmentModule::class,
        MovieModule::class
    ]
)
interface AppComponent :AndroidInjector<MovieApp>{
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
    override fun inject(instance: MovieApp?)
}