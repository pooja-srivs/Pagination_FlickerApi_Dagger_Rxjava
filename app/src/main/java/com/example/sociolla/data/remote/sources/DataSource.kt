package com.mingle.chatapp.data.remote.sources

import com.example.sociolla.data.remote.ApiConstants
import com.example.sociolla.data.remote.sources.HorizontalDataModel
import io.reactivex.Single

//Movie Source will consume Movie Service
class DataSource(val dataService: DataService) {

    fun getVerticalList(method : String, apiKey : String, format : String, keyword : String, pageNo : Int): Single<VerticleDataModel> {
        return dataService.getVerticalData(method, apiKey, format, keyword,pageNo, ApiConstants.PER_PAGE_LIMIT,"?")
    }

    fun getHorizontalList(method : String, apiKey : String, format : String, keyword : String, pageNo : Int): Single<HorizontalDataModel> {
        return dataService.getHorizontalData(method, apiKey, format, keyword,pageNo, ApiConstants.HORIZONTAL_PER_PAGE_LIMIT,"?")
    }

}