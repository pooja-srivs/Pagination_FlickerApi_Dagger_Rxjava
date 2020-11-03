package com.mingle.chatapp.chat.multiviewadapter

import android.view.View
import com.example.sociolla.utils.loadImage
import kotlinx.android.synthetic.main.movie_item.view.*

class VerticalVH(view: View): BaseViewHolder<AdapterDataModel>(view) {
    override fun bind(model: AdapterDataModel) {
        with(itemView){
            val flickerImageUrl = "https://farm" + model.farm.toString() + ".staticflickr.com/" +
                    model.server + "/" + model.id + "_" +
                    model.secret + ".jpg"
            image_movie.loadImage(flickerImageUrl)
            text_movie_title.text = model.title
        }
    }
}