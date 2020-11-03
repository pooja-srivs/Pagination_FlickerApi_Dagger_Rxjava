package com.mingle.chatapp.di

import com.example.sociolla.Application
import com.mingle.chatapp.di.modules.ActivityResolver
import com.mingle.chatapp.di.modules.DataSourceResolver
import com.mingle.chatapp.di.modules.NetworkResolver
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
    AndroidSupportInjectionModule::class,
    ActivityResolver::class,
    DataSourceResolver::class,
    NetworkResolver::class))
interface AppComponent : AndroidInjector<Application>{

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}