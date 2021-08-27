package com.example.android.rtdp.implementation.kotlin.di

import android.app.Application
import com.example.android.rtdp.implementation.kotlin.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    ViewModelModule::class,
    ActivityModule::class
])
interface KotlinAppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): KotlinAppComponent
    }

    fun inject(app: App)
}