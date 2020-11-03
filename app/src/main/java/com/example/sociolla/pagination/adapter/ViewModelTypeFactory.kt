package com.mingle.chatapp.chat.multiviewadapter

import android.view.View

interface ViewModelTypeFactory {

    fun type(dataModel: AdapterDataModel) : Int
    fun create(parent: View, viewType: Int) : BaseViewHolder<AdapterDataModel>
}