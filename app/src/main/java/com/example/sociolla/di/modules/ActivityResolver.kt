package com.mingle.chatapp.di.modules

import com.example.sociolla.pagination.MainActivity
import com.mingle.chatapp.movie.di.DataModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityResolver {

    @ContributesAndroidInjector(modules = arrayOf(DataModule::class))
    abstract fun providesMainActivity() : MainActivity
}