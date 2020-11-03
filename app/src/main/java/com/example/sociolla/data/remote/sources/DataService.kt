package com.mingle.chatapp.data.remote.sources

import com.example.sociolla.data.remote.sources.HorizontalDataModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface DataService {

    @GET("rest?")
    fun getVerticalData(@Query("method") method: String,
                        @Query("api_key") apiKey: String,
                        @Query("format") format: String,
                        @Query("text") keyword: String,
                        @Query("page") pageNo: Int,
                        @Query("per_page") pageLimit: Int,
                        @Query("nojsoncallback") callback: String?): Single<VerticleDataModel>

    @GET("rest?")
    fun getHorizontalData(@Query("method") method: String,
                  @Query("api_key") apiKey: String,
                  @Query("format") format: String,
                  @Query("text") keyword: String,
                  @Query("page") pageNo: Int,
                  @Query("per_page") pageLimit: Int,
                  @Query("nojsoncallback") callback: String?): Single<HorizontalDataModel>
}