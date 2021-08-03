package com.application.News.ui.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.News.R
import com.application.News.ui.model.BannerList
import com.application.News.ui.model.SlideRes
import com.application.News.util.blur.Blurry
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.slide_item.view.*

class SlideAdapter(var context: Context):RecyclerView.Adapter<SlideAdapter.ViewHolder>() {

    var list = emptyList<BannerList>()

    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        var slideImg = itemView.slideImg
        var slideTitle = itemView.slideTitle
        var slideCate = itemView.slideCate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.slide_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        Glide.with(context).load( model.bannerImage).into(holder.slideImg)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface ManageCardInterface {
        fun data()
    }

    fun setValues(slide:ArrayList<BannerList>){
        list = slide
        notifyDataSetChanged()
    }
}