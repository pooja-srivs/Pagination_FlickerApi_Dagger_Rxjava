package com.mingle.chatapp.chat.multiviewadapter

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sociolla.data.remote.sources.HorizontalPhoto
import com.example.sociolla.pagination.adapter.MovieHorizontalItemAdapter
import kotlinx.android.synthetic.main.horizontal_item.view.*

class HorizontalRecyclerVH(view: View): BaseViewHolder<AdapterDataModel>(view) {

    private lateinit var adapter: MovieHorizontalItemAdapter
    private var movieDataList : ArrayList<AdapterDataModel> = ArrayList(arrayListOf())

    override fun bind(model: AdapterDataModel) {

        with(itemView){

           val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL ,false)
            rv_horizontal_list.layoutManager = linearLayoutManager

            rv_horizontal_list.adapter = MovieHorizontalItemAdapter.newInstance()
                    .also {
                        adapter = it
                    }
        }
        appendData(model.horizontalList)
    }

    fun appendData(photoListData: List<HorizontalPhoto>) {

        movieDataList.clear()
        for (i in 0 until photoListData.size) {
                movieDataList.add(AdapterDataModel(photoListData.get(i).id, photoListData.get(i).title, photoListData.get(i).farm, photoListData.get(i).server, photoListData.get(i).secret, ViewModelTypeFactoryImpl.VERTICAL))

        }
        adapter.submitList(movieDataList)
    }
}