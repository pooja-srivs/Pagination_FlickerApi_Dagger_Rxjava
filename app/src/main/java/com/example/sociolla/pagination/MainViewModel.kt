package com.example.sociolla.pagination

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sociolla.data.remote.ApiConstants
import com.example.sociolla.data.remote.sources.HorizontalDataModel
import com.example.sociolla.data.remote.sources.ZippeDataModel
import com.mingle.chatapp.data.remote.sources.VerticleDataModel
import com.mingle.chatapp.data.repository.DataRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val movieDataRepository: DataRepository) : ViewModel() {

    var zippedLiveData : MutableLiveData<ZippeDataModel> = MutableLiveData()
    var moreZippedLiveData : MutableLiveData<ZippeDataModel> = MutableLiveData()

    var horizontalLiveData : MutableLiveData<HorizontalDataModel> = MutableLiveData()
    var verticalLiveData : MutableLiveData<VerticleDataModel> = MutableLiveData()

    var errorLiveData : MutableLiveData<Throwable> = MutableLiveData()
    var isLoading : MutableLiveData<Boolean> = MutableLiveData()
    private val compositeDisposable : CompositeDisposable = CompositeDisposable()

    fun getVerticalData(method : String, apiKey : String, format : String, keyword : String, pageNo : Int){
        isLoading.setValue(true)
         compositeDisposable.add(movieDataRepository.getVerticalData(method, apiKey, format, keyword, pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                 .doOnEvent { imgList, throwble -> isLoading.setValue(false) }
                .subscribe(verticalLiveData::setValue, errorLiveData::setValue))
    }

    fun getHorizontalData(method : String, apiKey : String, format : String, keyword : String, pageNo : Int){
        isLoading.setValue(true)
        compositeDisposable.add(movieDataRepository.getHorizontalData(method, apiKey, format, keyword, pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEvent { imgList, throwble -> isLoading.setValue(false) }
                .subscribe(horizontalLiveData::setValue, errorLiveData::setValue))
    }

    fun getData(method : String, apiKey : String, format : String, pageNo : Int){
        isLoading.setValue(true)
       
        Single.zip(movieDataRepository.getVerticalData(method, apiKey, format, ApiConstants.TEXT_VERTICAL, pageNo).subscribeOn(Schedulers.io()),
                movieDataRepository.getHorizontalData(method, apiKey, format, ApiConstants.TEXT_HORIZONTAL, pageNo).subscribeOn(Schedulers.io()),

                object : io.reactivex.functions.BiFunction<VerticleDataModel, HorizontalDataModel, ZippeDataModel>{
                    override fun apply(verticleDataModel: VerticleDataModel, horizontalDataModel : HorizontalDataModel): ZippeDataModel {

                        return ZippeDataModel(verticleDataModel, horizontalDataModel)
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEvent { imgList, throwble -> isLoading.setValue(false) }
                .subscribe({zippedDataModel ->

                    zippedLiveData.setValue(zippedDataModel)

                },{
                    errorLiveData.setValue(it)
                })
    }

    fun getMoreData(method : String, apiKey : String, format : String, pageNo : Int){
        isLoading.setValue(true)

        Single.zip(movieDataRepository.getVerticalData(method, apiKey, format, ApiConstants.TEXT_VERTICAL, pageNo).subscribeOn(Schedulers.io()),
                movieDataRepository.getHorizontalData(method, apiKey, format, ApiConstants.TEXT_HORIZONTAL, pageNo).subscribeOn(Schedulers.io()),

                object : io.reactivex.functions.BiFunction<VerticleDataModel, HorizontalDataModel, ZippeDataModel>{
                    override fun apply(verticleDataModel: VerticleDataModel, horizontalDataModel : HorizontalDataModel): ZippeDataModel {

                        return ZippeDataModel(verticleDataModel, horizontalDataModel)
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEvent { imgList, throwble -> isLoading.setValue(false) }
                .subscribe({zippedDataModel ->

                    moreZippedLiveData.setValue(zippedDataModel)

                },{
                    errorLiveData.setValue(it)
                })
    }


    override fun onCleared() {
        super.onCleared()
        if (compositeDisposable != null){
            compositeDisposable.clear()
        }
    }
}