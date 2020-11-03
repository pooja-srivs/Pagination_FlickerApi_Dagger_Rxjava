package com.mingle.chatapp.data.repository

import com.example.sociolla.data.remote.sources.HorizontalDataModel
import com.mingle.chatapp.data.remote.sources.DataSource
import com.mingle.chatapp.data.remote.sources.VerticleDataModel
import io.reactivex.Single

class DataRepository(val dataSource: DataSource) {

    fun getVerticalData(method : String, apiKey : String, format : String, keyword : String, pageNo : Int) : Single<VerticleDataModel> {
        return dataSource.getVerticalList(method, apiKey, format, keyword, pageNo)
    }

    fun getHorizontalData(method : String, apiKey : String, format : String, keyword : String, pageNo : Int) : Single<HorizontalDataModel> {
        return dataSource.getHorizontalList(method, apiKey, format, keyword, pageNo)
    }
}