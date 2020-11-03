package com.mingle.chatapp.movie.di

import androidx.lifecycle.ViewModelProviders
import com.example.sociolla.pagination.MainActivity
import com.example.sociolla.pagination.MainViewModel
import com.mingle.chatapp.data.repository.DataRepository
import com.mingle.chatapp.movie.vmfactory.MainViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun providesViewModelFactory(movieDataRepository: DataRepository) = MainViewModelFactory(movieDataRepository)

    @Provides
    fun providesMainViewModel(factory: MainViewModelFactory, activity: MainActivity) : MainViewModel = ViewModelProviders
        .of(activity, factory)
        .get(MainViewModel::class.java)
}