package com.mingle.chatapp.chat.multiviewadapter

import android.view.View
import com.example.sociolla.R

class ViewModelTypeFactoryImpl : ViewModelTypeFactory {

    companion object{
        const val VERTICAL = "Vertical"
        const val HORIZONTAL = "Horizontal"

        const val VERTICAL_VIEW = R.layout.movie_item
        const val HORIZONTAL_VIEW = R.layout.horizontal_item
    }

    override fun type(dataModel: AdapterDataModel): Int {
        return when(dataModel.type){

            VERTICAL-> VERTICAL_VIEW
            HORIZONTAL -> HORIZONTAL_VIEW
            else -> VERTICAL_VIEW
        }
    }

    override fun create(parent: View, viewType: Int): BaseViewHolder<AdapterDataModel> {

        return when (viewType) {
            VERTICAL_VIEW -> VerticalVH(parent)
            HORIZONTAL_VIEW -> HorizontalRecyclerVH(parent)
            else -> VerticalVH(parent)
        }
    }
}