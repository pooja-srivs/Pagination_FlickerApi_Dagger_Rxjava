package com.mingle.chatapp.chat.multiviewadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class MultiMovieViewAdapter private constructor(
    val diffUtil: DiffUtil.ItemCallback<AdapterDataModel>
): ListAdapter<AdapterDataModel, BaseViewHolder<AdapterDataModel>>(diffUtil) {

    private val typeFactory: ViewModelTypeFactory = ViewModelTypeFactoryImpl()

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AdapterDataModel>() {

            override fun areItemsTheSame(oldItem: AdapterDataModel, newItem: AdapterDataModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: AdapterDataModel, newItem: AdapterDataModel): Boolean =
                oldItem == newItem

        }

        fun newInstance() = MultiMovieViewAdapter(
                DIFF_CALLBACK
            )

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<AdapterDataModel> {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(viewType, parent, false)
        return typeFactory.create(view, viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<AdapterDataModel>, position: Int) {
 
        holder.bind(requireNotNull(getItemAt(position)))
    }

    fun getItemAt(position: Int): AdapterDataModel {
        return getItem(position)
    }

    override fun getItemViewType(position: Int): Int {
        return getItemAt(position).type(typeFactory)
    }

}
