package com.mingle.chatapp.chat.multiviewadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<model: AdapterDataModel>(view: View): RecyclerView.ViewHolder(view) {

    abstract fun bind(model: AdapterDataModel)
}