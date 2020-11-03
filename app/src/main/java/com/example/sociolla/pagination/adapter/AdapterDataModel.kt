package com.mingle.chatapp.chat.multiviewadapter

import com.example.sociolla.data.remote.sources.HorizontalPhoto

data class AdapterDataModel(val id: String = "", val title: String = "", val farm: Int = 0, val server: String = "", val secret: String = "", val type: String, val horizontalList: ArrayList<HorizontalPhoto> = arrayListOf()): ItemViewModelType{
    override fun type(viewModelTypefactory: ViewModelTypeFactory): Int {
        return viewModelTypefactory.type(this)
    }
}