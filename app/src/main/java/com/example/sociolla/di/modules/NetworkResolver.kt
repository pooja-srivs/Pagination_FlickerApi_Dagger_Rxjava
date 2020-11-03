package com.mingle.chatapp.di.modules

import com.mingle.chatapp.data.remote.config.ApiManager
import com.mingle.chatapp.data.remote.config.BaseUrl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkResolver {

    @Singleton
    @Provides
    fun providesApiKey() : String = "3e7cc266ae2b0e0d78e279ce8e361736"

    @Singleton
    @Provides
    fun providesOkHttpClient() : OkHttpClient = OkHttpClient.Builder().build()

    @Singleton
    @Provides
    fun providesRetrofitBuilder(okHttpClient: OkHttpClient) : Retrofit = Retrofit.Builder()
        .baseUrl(BaseUrl.base_url)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun providesRetrofit(retrofit: Retrofit) = ApiManager(retrofit)


}