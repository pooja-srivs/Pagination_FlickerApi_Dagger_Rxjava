package com.mingle.chatapp.di.modules

import com.mingle.chatapp.data.remote.config.ApiManager
import com.mingle.chatapp.data.remote.sources.DataSource
import com.mingle.chatapp.data.repository.DataRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataSourceResolver {

    @Singleton
    @Provides
    fun providesMovieSource(apiManager: ApiManager) : DataSource = DataSource(apiManager.dataService)

    @Singleton
    @Provides
    fun providesMovieDataRepository(movieSource: DataSource) : DataRepository = DataRepository(movieSource)


}