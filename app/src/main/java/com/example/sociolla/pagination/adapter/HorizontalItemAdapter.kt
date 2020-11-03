package com.example.sociolla.pagination.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sociolla.R
import com.example.sociolla.utils.cancelImageLoading
import com.example.sociolla.utils.loadImage
import com.mingle.chatapp.chat.multiviewadapter.AdapterDataModel
import kotlinx.android.synthetic.main.horizontal_movie_item.view.*

class MovieHorizontalItemAdapter private constructor(
        private val diffUtil: DiffUtil.ItemCallback<AdapterDataModel>
) : ListAdapter<AdapterDataModel, MovieItemVH>(diffUtil){

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AdapterDataModel>() {

            override fun areItemsTheSame(oldItem: AdapterDataModel, newItem: AdapterDataModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: AdapterDataModel, newItem: AdapterDataModel): Boolean =
                oldItem == newItem

        }

        fun newInstance() = MovieHorizontalItemAdapter(DIFF_CALLBACK)
    }

    fun getItemAt(position: Int): AdapterDataModel? {
        return getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemVH {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.horizontal_movie_item, parent, false)
        return MovieItemVH(view)
    }

    override fun onBindViewHolder(holder: MovieItemVH, position: Int) {
       holder.bind(requireNotNull(getItemAt(position)))
    }

    override fun onViewAttachedToWindow(holder: MovieItemVH) {
        super.onViewAttachedToWindow(holder)
        holder.loadMoviePoster()
    }

    override fun onViewDetachedFromWindow(holder: MovieItemVH) {
        super.onViewDetachedFromWindow(holder)
        holder.cancelLoading()
    }

}

class MovieItemVH(view : View) : RecyclerView.ViewHolder(view){

    var adapterDataModel : AdapterDataModel? = null
    val image_movie = view.image_movie
    val text_movie_title = view.text_movie_title

    fun bind(
            adapterDataModel: AdapterDataModel
    ){
        val flickerImageUrl = "https://farm" + adapterDataModel.farm.toString() + ".staticflickr.com/" +
                adapterDataModel.server + "/" + adapterDataModel.id + "_" +
                adapterDataModel.secret + ".jpg"

        this.adapterDataModel = adapterDataModel
        image_movie.loadImage(flickerImageUrl)
        text_movie_title.setText(adapterDataModel.title)

    }

    fun cancelLoading(){
        image_movie.cancelImageLoading()
    }

    fun loadMoviePoster(){
        val flickerImageUrl = "https://farm" + adapterDataModel?.farm.toString() + ".staticflickr.com/" +
                adapterDataModel?.server + "/" + adapterDataModel?.id + "_" +
                adapterDataModel?.secret + ".jpg"
        image_movie.loadImage(flickerImageUrl)
    }

}